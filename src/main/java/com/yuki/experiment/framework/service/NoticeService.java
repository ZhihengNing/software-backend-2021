package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> getAllNotice();

    int insertNotice(Notice notice);

    int updateNotice(Notice notice);

    int deleteNotice(List<Integer>noticeId);
}
