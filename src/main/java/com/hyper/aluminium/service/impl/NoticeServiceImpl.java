package com.hyper.aluminium.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyper.aluminium.mapper.NoticeMapper;
import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.event;
import com.hyper.aluminium.pojo.notice;
import com.hyper.aluminium.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public PageBean getAllnotice(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<notice> notices = noticeMapper.getAllnotice();
        Page<notice> noticePage = (Page<notice>) notices;

        PageBean pageBean = new PageBean(noticePage.getTotal(), noticePage.getResult());
        return pageBean;

    }

    @Override
    public void addNotice(notice notice) {
        noticeMapper.addNotice(notice);

    }

    @Override
    public void deleteNoticeByid(int id) {
        noticeMapper.delNotice(id);
    }

    @Override
    public void updateNotice(notice notice, int id) {
        noticeMapper.UpdateNotice(notice,id);
    }


}
