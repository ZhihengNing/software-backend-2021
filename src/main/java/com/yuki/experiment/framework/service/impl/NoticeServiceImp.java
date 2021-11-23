package com.yuki.experiment.framework.service.impl;

import com.yuki.experiment.framework.entity.Notice;
import com.yuki.experiment.framework.mapper.NoticeMapper;
import com.yuki.experiment.framework.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImp implements NoticeService {
    @Autowired
    private NoticeMapper mapper;
    @Override
    public List<Notice> getAllNotice() {
        return mapper.selectList(null);
    }

    @Override
    public int insertNotice(Notice notice) {
        return mapper.insert(notice);
    }

    @Override
    public int updateNotice(Notice notice) {
        return mapper.updateById(notice);
    }

    @Override
    public int deleteNotice(List<Integer> noticeId) {
        return mapper.deleteBatchIds(noticeId);
    }
}
