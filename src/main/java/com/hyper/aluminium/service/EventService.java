package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.event;


public interface EventService {




    PageBean getAllEvent(int page, int pageSize);

    void addEvent(event event);

    event GetEvent(int id);

    void deleteEvent(int id);

    void updateEvent(event event);
}
