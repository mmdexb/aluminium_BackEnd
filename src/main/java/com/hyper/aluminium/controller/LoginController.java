package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestParam("cid") String cid,@RequestParam("password") String pwd){
        pwd= DigestUtils.md5DigestAsHex(pwd.getBytes());
        User user=new User();
        user.setCid(cid);
        user.setPassword(pwd);
        log.info("用户登录：{}",user);
        User e= userService.login(user);
        return e !=null?Result.success():Result.error("用户名或密码错误");

    }

    @PostMapping("/Reset")
    public Result Reset(@RequestParam("cid") String cid,@RequestParam("password") String pwd){

        String pwdMD5= DigestUtils.md5DigestAsHex(pwd.getBytes());
        int res = userService.Reset(cid,pwd,pwdMD5);
        if (res==0){
            return Result.error("CID不存在");
        }else{
            return Result.success();
        }

    }

}
