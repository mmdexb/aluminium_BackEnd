package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.service.UserService;
import com.hyper.aluminium.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.RemoteServer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestParam("cid") String cid, @RequestParam("password") String pwd) {
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        User user = new User();
        user.setCid(cid);
        user.setPassword(pwd);
        log.info("用户登录：{}", user);
        User e = userService.login(user);
        //登陆成功下发jwt令牌
        if(e!=null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("cid",e.getCid());
            claims.put("level",e.getLevel());
            claims.put("realname",e.getRealname());
            claims.put("email",e.getEmail());

            String jwt= JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }



        return Result.error("用户名或密码错误");

    }

    @PostMapping("/Reset")
    public Result Reset(@RequestParam("cid") String cid, @RequestParam("password") String pwd) {

        String pwdMD5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        int res = userService.Reset(cid, pwd, pwdMD5);
        if (res == 0) {
            return Result.error("CID不存在");
        } else {
            return Result.success();
        }

    }

    @PostMapping("/reg")
    public Result reg(@RequestParam("cid") String cid,
                      @RequestParam("password") String pwd,
                      @RequestParam("realname") String realname,
                      @RequestParam("email") String email) {
        int res = userService.reg(cid, pwd, realname, email);

        if (res == 0) {
            return Result.error("CID已存在");
        } else if (res == 1) {
            return Result.error("邮箱已存在");
        } else if (res == 2) {
            return Result.success("注册成功");
        }

        return Result.error("未知错误");
    }



}







