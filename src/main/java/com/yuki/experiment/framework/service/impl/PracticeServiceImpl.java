package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.EmptyUtil;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.Option;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.entity.Problem;
import com.yuki.experiment.framework.mapper.mysql.CourseScoreMapper;
import com.yuki.experiment.framework.service.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class PracticeServiceImpl implements PracticeService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private CourseScoreMapper courseScoreMapper;

    private final static String tableName="practice";

    @Override
    public List<Practice> getAll(Integer courseId, Integer teacherId) {
        Criteria criteria = new Criteria();
        if (courseId != null && teacherId != null) {
            criteria.andOperator(
                    Criteria.where("courseId").is(courseId),
                    Criteria.where("teacherId").is(teacherId)
            );
        } else if (courseId != null) {
            criteria.andOperator(
                    Criteria.where("courseId").is(courseId)
                    );
        } else if (teacherId != null) {
            criteria.andOperator(
                    Criteria.where("teacherId").is(teacherId)
            );
        } else {
            return mongoTemplate.findAll(Practice.class, tableName);
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Practice.class, tableName);
    }

    @Override
    public Practice getOneById(String practiceId) {
        Criteria criteria = Criteria.where("id").is(practiceId);
        List<Practice> practices = mongoTemplate.find(new Query(criteria),
                Practice.class, tableName);
        if(!EmptyUtil.isEmpty(practices)){
            return practices.get(0);
        }
        return null;
    }
    //用来新增题目的接口
    @Override
    public Practice addSomeProblems(Practice practice){
        Integer courseId = practice.getCourseId();
        Integer teacherId = practice.getTeacherId();
        Criteria criteria=new Criteria();
        criteria.andOperator(
                Criteria.where("courseId").is(courseId),
                Criteria.where("teacherId").is(teacherId));
        Query query=new Query(criteria);
        Practice one = mongoTemplate.findOne(query, Practice.class, tableName);
        if(one==null){
            Date date=new Date();
            practice.setCreateTime(date);
            practice.setUpdateTime(date);
            return  mongoTemplate.insert(practice, tableName);
        }
        List<Problem> problems = one.getProblems();
        //把新的题加进去
        problems.addAll(practice.getProblems());
        Update update=new Update();
        update.set("problems",one.getProblems());
        update.set("updateTime",new Date());
        mongoTemplate.updateFirst(query,update,Practice.class,tableName);
        return one;
    }


    @Override
    public Practice update(Practice practice) {
        Query query=new Query(Criteria.where("id").is(practice.getId()));
        practice.setUpdateTime(new Date());
        Update update=new Update();
        update.set("problems",practice.getProblems());
        update.set("updateTime",new Date());
        mongoTemplate.updateFirst(query, update, Practice.class, tableName);
        return practice;
    }

    @Override
    public Practice random(Integer courseId) {
        Criteria criteria = Criteria.where("courseId").is(courseId);
        Query query = new Query(criteria);
        Practice practice = mongoTemplate.findOne(query, Practice.class, "practice");
        return practice;
    }

    public void insert() {
//        Practice practice = new Practice();
//
//        List<Problem> problems = new ArrayList<>();
//        Problem problem = new Problem();
//
//        List<Option> options = new ArrayList<>();
//        options.add(new Option("A", "1456", "qeedewr"));
//        options.add(new Option("C", "我爱你", "qeedeedwwr"));
//        problem.setStem("今天吃什么，今天吃什么");
//        problem.setOptions(options);
//
//
//        Problem problem1 = new Problem();
//
//        List<Option> optionMongos1 = new ArrayList<>();
//        optionMongos1.add(new Option("B", "1456B", "qeedewr"));
//        optionMongos1.add(new Option("D", "我爱你D", "qeedeedwwr"));
//        problem1.setStem("今天吃什么1，今天吃什么1");
//        problem1.setOptions(optionMongos1);
//
//        problems.add(problem);
//        problems.add(problem1);
//
//        practice.setCourseId(60020);
//        practice.setTeacherId(25500);
//        practice.setProblems(problems);
//        System.out.println(practice);
//        mongoTemplate.insert(practice, tableName);

    }

    private boolean judgeTime(Date thisTime,Date lastTime){
        Calendar instance = Calendar.getInstance();
        instance.setTime(lastTime);
        Calendar instance1=Calendar.getInstance();
        instance1.setTime(thisTime);
        return instance1.get(Calendar.DAY_OF_YEAR)-instance.get(Calendar.DAY_OF_YEAR)>=1;
    }

    @Override
    public Boolean judgePractice(Integer courseId, Integer studentId) {
        Date now=new Date();
        QueryWrapper<CourseScore>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId).eq("course_id",courseId);
        CourseScore courseScore = courseScoreMapper.selectOne(queryWrapper);

        return judgeTime(now,courseScore.getLastPracticeTime());
    }
}
