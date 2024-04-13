package com.hyper.aluminium.controller;


import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.atc;
import com.hyper.aluminium.pojo.pilot;
import com.hyper.aluminium.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController()
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/public/onlinePilot")
    public Result listOnlinePilots() {
        List<pilot> list = infoService.listOnlinePilots();

        return Result.success(list);
    }

    @GetMapping("/public/onlineATC")
    public Result listOnlineATC() {
        List<atc> list = infoService.listOnlineATC();
        return Result.success(list);
    }



    


}
