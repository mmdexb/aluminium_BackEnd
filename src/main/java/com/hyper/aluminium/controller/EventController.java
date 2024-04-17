package com.hyper.aluminium.controller;


import com.alibaba.fastjson2.*;
import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.event;
import com.hyper.aluminium.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Result addEvent(@RequestBody event event) {
        log.info("添加活动,参数{}", event);
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
