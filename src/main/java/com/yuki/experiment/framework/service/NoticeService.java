package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Notice;

import java.util.Date;
import java.util.List;

public interface NoticeService {

    List<Notice> getAllNotice(Integer noticeId);

    List<Notice> fuzzyQuery(String keyword);

    List<Notice> queryByTime(Date beginDate,Date endDate);

    int insertNotice(Notice notice);

    Notice updateNotice(Notice notice);

    int deleteNotice(List<Integer>noticeId);
}
