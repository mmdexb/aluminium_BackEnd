package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.notice;
import org.springframework.stereotype.Service;


@Service
public interface NoticeService {
    PageBean getAllnotice(int page, int pageSize);

    void addNotice(notice notice);

    void deleteNoticeByid(int id);

    void updateNotice(notice notice, int id);

    notice GetNoticeByid(int id);
}
