package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.notice;
import com.hyper.aluminium.service.InfoService;
import com.hyper.aluminium.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/public/getAllNotice")
    public Result getAllEvent(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int pageSize) {

        log.info("分页查询活动,参数{}，{}", page, pageSize);
        PageBean pagebean= noticeService.getAllnotice(page,pageSize);
        return Result.success(pagebean);
    }

    @PostMapping("/admin/addNotice")
    public Result addNotice(@RequestBody notice notice){
        noticeService.addNotice(notice);
        return Result.success();
    }

    @GetMapping("/admin/deleteNotice")
    public Result deleteNotice(@RequestParam("id") int id){
        noticeService.deleteNoticeByid(id);
        return Result.success();
    }

    @PostMapping("/admin/UpdateNotice")
    public Result UpdateNotice(@RequestBody notice notice){
        int id=notice.getId();
        noticeService.updateNotice(notice,id);
        return Result.success();
    }



}
