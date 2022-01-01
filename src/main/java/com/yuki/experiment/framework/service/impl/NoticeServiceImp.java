package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Notice;
import com.yuki.experiment.framework.mapper.mysql.NoticeMapper;
import com.yuki.experiment.framework.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImp implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice> getAllNotice(Integer noticeId) {
        QueryWrapper<Notice>noticeQueryWrapper=new QueryWrapper<>();
        noticeQueryWrapper.eq(noticeId!=null,"id",noticeId);
        return noticeMapper.selectList(noticeQueryWrapper);
    }


    @Override
    public List<Notice> fuzzyQuery(String keyword) {
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("noticeTitle",keyword).or().like("noticeText",keyword);
        return noticeMapper.selectList(queryWrapper);
    }

    @Override
    public List<Notice> queryByTime(Date beginDate, Date endDate) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("create_time", beginDate, endDate).or()
                .between("update_time", beginDate, endDate);
        return noticeMapper.selectList(queryWrapper);
    }

    @Override
    public int insertNotice(Notice notice) {
        return noticeMapper.insert(notice);
    }

    @Override
    public Notice updateNotice(Notice notice) {
        noticeMapper.updateById(notice);
        return noticeMapper.selectById(notice.getId());
    }

    @Override
    public int deleteNotice(List<Integer> noticeId) {
        return noticeMapper.deleteBatchIds(noticeId);
    }
}
