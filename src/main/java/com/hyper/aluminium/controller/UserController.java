package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.service.UserService;
import com.hyper.aluminium.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

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

    @GetMapping("/public/GetPilotNum")
    public Result GetPilotNum() {
        int number=0;
        number= userService.getPilotNum();

        return Result.success(number);
    }
    @GetMapping("/public/GetAtcNum")
    public Result GetAtctNum() {
        int number=0;
        number= userService.getAtcNum();
        return Result.success(number);
    }

    @GetMapping("/public/GetOnlineTimeByid")
    public Result GetOnlineTimeByid(@RequestParam("cid") int cid) {
        int time=userService.GetOnlineTimeByid(cid);

        return Result.success(time);
    }

    @GetMapping("/public/GetAllOnlineSort")
    public Result GetAllOnlineSort() {
        List<User> users=new ArrayList<User>();
        users=userService.getAllUserWithList();
        //进行公开化处理 去掉隐私信息
        for(User user:users){
            user.setEmail("");
            user.setPassword("");
        }

        return Result.success(users);
    }





}






