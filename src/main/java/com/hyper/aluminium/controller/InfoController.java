package com.hyper.aluminium.controller;


import com.hyper.aluminium.pojo.AirportList;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.atc;
import com.hyper.aluminium.pojo.pilot;
import com.hyper.aluminium.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.RemoteServer;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/public/GetAirportList")
    public Result GetAirportList() {
        List<Map<String, Integer>> list= infoService.GetAirportList();
        return Result.success(list);
    }

    @GetMapping("/public/GetRoute")
    public Result GetRoute(@RequestParam("dep") String dep,
                           @RequestParam("arr") String arr) throws Exception {
        return Result.success(infoService.GetRoute(dep,arr));
    }





}
