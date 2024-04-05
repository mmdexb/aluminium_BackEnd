package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.service.CertService;
import com.hyper.aluminium.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController()
public class CertController {
    private static final Set<String> ALLOWED_LEVELS = new HashSet<>(Arrays.asList(
            "SUSPENDED", "OBSPILOT", "STUDENT1", "STUDENT2", "STUDENT3",
            "CONTROLLER1", "CONTROLLER2", "CONTROLLER3", "INSTRUCTOR1",
            "INSTRUCTOR2", "INSTRUCTOR3", "SUPERVISOR", "ADMINISTRATOR"));
    @Autowired
    private CertService certService;


    @PostMapping("/admin/addCert")
    public Result addCert(
            @RequestParam("cid") String cid,
            @RequestParam("pwd") String pwd,
            @RequestParam("level") String level
    ) {
        //判断level是否合法
        log.info("add cert cid:{},pwd:{},level:{},", cid, pwd, level);
        if (ALLOWED_LEVELS.contains(level)) {
            return Result.success(certService.addCert(cid, pwd, level));
        } else {
            return Result.error("level不合法");
        }
    }

    @PostMapping("/admin/delCert")
    public Result delCert(
            @RequestParam("cid") String cid
    ) {

        return Result.success(certService.delCert(cid));
    }

    @PostMapping("/admin/modCertLevel")
    public Result modCertLevel(@RequestParam("cid") String cid,@RequestParam("level") String level){
        log.info("add cert cid:{},level:{},", cid,  level);
        if (ALLOWED_LEVELS.contains(level)) {
            return Result.success(certService.modCertLevel(cid,level));
        } else {
            return Result.error("level不合法");
        }
    }


    @Autowired
    private UserService userService;
    @GetMapping("/admin/getAllCert")
    public Result getAllCert(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int pageSize) {

        log.info("分页查询,参数{}，{}", page, pageSize);
        PageBean pagebean= userService.getAllUser(page,pageSize);
        return Result.success(pagebean);
    }

}
