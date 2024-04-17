package com.hyper.aluminium.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyper.aluminium.mapper.UserMapper;
import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.pojo.pilot;
import com.hyper.aluminium.service.CertService;
import com.hyper.aluminium.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserServicelmpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServicelmpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CertService certService;
    @Override
    public User login(User user) {
        User e= userMapper.getcidAndPassword(user);
        return e;
    }

    @Override
    public int Reset(String cid, String pwd,String pwdMD5) {
        User e= userMapper.FindByCid(cid);
        if(e!=null){
            //调用certService
            e.setPassword(pwd);
            certService.delCert(cid);
            certService.addCert(cid,pwd,e.getLevel());
            //操作数据库
            userMapper.ResetPwd(cid,pwdMD5);
            return 1;

        }else {
            //cid不存在
            return 0;
        }


    }
    
    @Override
    public int reg(String cid, String pwd, String realname, String email) {
        String pwdMD5= DigestUtils.md5DigestAsHex(pwd.getBytes());
        User e=new User();
        if(userMapper.IsCidExist(cid)!=null){
            //cid已存在
            return 0;
        }else if(userMapper.IsEmailExist(email)!=null){
            //email 已存在
            return 1;
        }else {
            String s=certService.addCertToTXT(cid,"OBSPILOT",pwd);
            //插入数据库
            userMapper.reg(cid,pwdMD5,realname,email,"OBSPILOT");
            //调用certService
            certService.addCert(cid,pwd,"OBSPILOT");

            Logger log1 = log;
            log1.info(s);
            return 2;

        }
    }

    @Override
    public void delUser(String cid) {
        userMapper.delUser(cid);
    }

    @Override
    public PageBean getAllUser(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<User> users = userMapper.getAllUser();
        Page<User> userPage = (Page<User>) users;

        PageBean pageBean = new PageBean(userPage.getTotal(), userPage.getResult());
        return pageBean;
    }

    @Override
    public User GetCertByid(String cid) {
        User user=userMapper.FindByCid(cid);
        return user;
    }

    @Override
    public int getPilotNum() {
        int num=userMapper.getPilotNum();
        return num;
    }

    @Override
    public int getAtcNum() {
        int number=0;
        List<User> users =userMapper.getAllUser();
        for(User u:users){
            if (!u.getLevel().equals("OBSPILOT") && !u.getLevel().equals("SUSPENDED")) {
                number++;
            }
        }
        return number;
    }

    @Override
    public int GetOnlineTimeByid(int cid) {
        int time=userMapper.getOnlineTimeByid(cid);
        return time;
    }

    @Override
    public List<User> getAllUserWithList() {
        List<User> users=userMapper.getAllUser();
        users.sort(Comparator.comparingInt(User::getOnlineTime).reversed());
        return users.subList(0, Math.min(users.size(), 8));
        
    }

    @Override
    public void addTime(int cid) {
        userMapper.addTime(cid);
    }

    @Override
    public void addFlight(pilot pilot) {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取飞行数据
        int cid = pilot.getCid();
        String flightTime= sdf.format(new Date(currentTimeMillis));
        String departureAirport = pilot.getDepartureAirport();
        String arrivalAirport = pilot.getArrivalAirport();
        double altitude = pilot.getAltitude();
        double latitude = pilot.getLatitude();
        double longitude = pilot.getLongitude();
        String aircraftType = pilot.getAircraftType();

        // 向数据库插入飞行数据
        userMapper.addFlight(cid, flightTime, departureAirport, arrivalAirport, altitude, latitude, longitude, aircraftType);
    }



}
