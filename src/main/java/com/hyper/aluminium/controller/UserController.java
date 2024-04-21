package com.hyper.aluminium.controller;

import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.pojo.user2;
import com.hyper.aluminium.service.UserService;
import com.hyper.aluminium.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        //循环参数看看是否有空着的 cid是否为纯数字
        if (cid.equals("") || pwd.equals("") || realname.equals("") || email.equals("")) {
            return Result.error("注册信息不完整");
        }else if( !cid.matches("^[0-9]*$")){
            return Result.error("CID只能为纯数字");
        }
        int res = userService.reg(cid, pwd, realname, email);
        log.info(String.valueOf(res));
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

    @GetMapping("/public/GetFlightByid")
    public Result GetFlightByid(@RequestParam("cid") String cid) {
        return Result.success(userService.GetFlightByid(cid));
    }

    @GetMapping("/public/GetFlightByid2")
    public Result GetFlightByid2(@RequestParam("cid") String cid) {
        return Result.success(userService.GetFlightByid2(cid));
    }

    @GetMapping("/user/GetSelfInfo")
    public Result GetSelf(@RequestHeader("Authorization") String jwt) {
        Claims claims= JwtUtils.parseJWT(jwt);
        String cid=claims.get("cid").toString();
        String realname=claims.get("realname").toString();
        String level=claims.get("level").toString();
        String eamil=claims.get("email").toString();

        String LoveAirport=userService.getLoveAirport(cid);
        String LoveType=userService.getLoveType(cid);

        String time=userService.GetOnlineTimeByid(Integer.parseInt(cid))+"分钟";

        log.info("用户信息：{}{}{}{}",cid,realname,level,eamil);
        //创建临时匿名类

        user2 user=new user2(cid,realname,level,eamil,LoveAirport,LoveType,time);
        //使用正则表达式解析{Field=ZBAA, Count=2}
        if(user.getLoveAirport().equals("暂无数据")){
            user.setLoveAirport("暂无数据");
        }else {
            String input = user.getLoveAirport();
            // 正则表达式模式，用于匹配ZBAA和2
            String pattern = "Field=(\\w+), Count=(\\d+)";
            // 创建Pattern对象
            Pattern regexPattern = Pattern.compile(pattern);
            // 创建Matcher对象
            Matcher matcher = regexPattern.matcher(input);
            // 查找匹配项
            if (matcher.find()) {
                String airportCode = matcher.group(1); // 提取ZBAA
                int flightCount = Integer.parseInt(matcher.group(2)); // 提取2

                // 拼接结果
                String result = "您在" + airportCode + "飞行了" + flightCount + "次";
                user.setLoveAirport(result);
            } else {
                System.out.println("未找到匹配项");
            }
        }

        if(user.getLoveType().equals("暂无数据")){
            user.setLoveType("暂无数据");
        }else {
            String input = user.getLoveType();

            // 正则表达式模式，用于匹配Type和1
            String pattern = "Type=(\\w+), Count=(\\d+)";

            // 创建Pattern对象
            Pattern regexPattern = Pattern.compile(pattern);

            // 创建Matcher对象
            Matcher matcher = regexPattern.matcher(input);

            // 查找匹配项
            if (matcher.find()) {
                String aircraftType = matcher.group(1); // 提取A320
                int flightCount = Integer.parseInt(matcher.group(2)); // 提取1

                // 拼接结果
                String result = "您使用" + aircraftType + "飞行了" + (flightCount * 2) + "次";
                user.setLoveType(result);
            } else {
                System.out.println("未找到匹配项");
            }
        }





        return Result.success(user);
    }





}







