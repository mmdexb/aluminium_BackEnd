package com.hyper.aluminium.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyper.aluminium.mapper.EventMapper;
import com.hyper.aluminium.pojo.PageBean;

import com.hyper.aluminium.pojo.event;
import com.hyper.aluminium.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;
    @Override
    public PageBean getAllEvent(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<event> events = eventMapper.getAllEvent();
        Page<event> eventPage = (Page<event>) events;

        PageBean pageBean = new PageBean(eventPage.getTotal(), eventPage.getResult());
        return pageBean;

    }

    @Override
    public  void addEvent(event event) {
        eventMapper.addEvent(event);

    }

    @Override
    public event GetEvent(int id) {
        event event = eventMapper.GetEvent(id);
        return event;
    }

    @Override
    public void deleteEvent(int id) {
        eventMapper.deleteEventByid(id);
    }
}
