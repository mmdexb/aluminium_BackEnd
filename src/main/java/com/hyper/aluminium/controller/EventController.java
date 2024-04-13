package com.hyper.aluminium.controller;


import com.alibaba.fastjson2.*;
import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.event;
import com.hyper.aluminium.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/public/getAllEvent")
    public Result getAllEvent(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int pageSize) {

        log.info("分页查询活动,参数{}，{}", page, pageSize);
        PageBean pagebean= eventService.getAllEvent(page,pageSize);
        return Result.success(pagebean);
    }

    @PostMapping("/admin/addEvent")
    public Result addEvent(
            @RequestParam("eventname") String eventname,
            @RequestParam("eventtime") String eventtime,
            @RequestParam("dep") String dep,
            @RequestParam("arr") String arr,
            @RequestParam("eventroute") String eventroute,
            @RequestParam("eventinfo") String eventinfo
    ) {
        log.info("添加活动,参数{}", eventname);
        event event = new event();
        event.setEventname(eventname);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        event.setEventtime(LocalDateTime.parse(eventtime, formatter));
        event.setDep(dep);
        event.setArr(arr);
        event.setEventroute(eventroute);
        event.setEventinfo(eventinfo);
        eventService.addEvent(event);
        return Result.success();
    }

    @GetMapping("/public/GetEvent")
    public Result GetEvent(@RequestParam("id") int id) {
        log.info("获取活动,参数{}", id);
        event e = eventService.GetEvent(id);
        if (e==null){
            return Result.error("活动不存在");
        }
        //event转化为json

        String json = JSON.toJSONString(e);
        return Result.success(e);

    }

    @GetMapping("/admin/deleteEvent")
    public Result deleteEvent(@RequestParam("id") int id) {
        log.info("删除活动,参数{}", id);
        eventService.deleteEvent(id);
        return Result.success();
    }


}
