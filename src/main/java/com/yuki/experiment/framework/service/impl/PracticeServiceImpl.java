package com.yuki.experiment.framework.service.impl;

import com.yuki.experiment.framework.entity.Option;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.entity.Problem;
import com.yuki.experiment.framework.service.PracticeService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PracticeServiceImpl implements PracticeService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<Practice> getAll(Integer courseId, Integer teacherId, Integer practiceId) {
        if (courseId != null) {
            Criteria criteria = Criteria.where("courseId").is(courseId);
            Query query = new Query(criteria);
            return mongoTemplate.find(query, Practice.class, "practice");
        }
        return mongoTemplate.findAll(Practice.class, "practice");
    }

    public Practice insert(Practice jsonObject) {
        return  mongoTemplate.insert(jsonObject, "practice");
    }

    public void insert() {
        Practice practice = new Practice();

        List<Problem> problems = new ArrayList<>();
        Problem problem = new Problem();

        List<Option> options = new ArrayList<>();
        options.add(new Option("A", "1456", "qeedewr"));
        options.add(new Option("C", "我爱你", "qeedeedwwr"));
        problem.setStem("今天吃什么，今天吃什么");
        problem.setOptions(options);


        Problem problem1 = new Problem();

        List<Option> optionMongos1 = new ArrayList<>();
        optionMongos1.add(new Option("B", "1456B", "qeedewr"));
        optionMongos1.add(new Option("D", "我爱你D", "qeedeedwwr"));
        problem1.setStem("今天吃什么1，今天吃什么1");
        problem1.setOptions(optionMongos1);

        problems.add(problem);
        problems.add(problem1);

        practice.setCourseId(60020);
        practice.setTeacherId(25500);
        practice.setProblems(problems);
        System.out.println(practice);
        mongoTemplate.insert(practice, "practice");

    }
}
