package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.pojo.pilot;

import java.util.List;

public interface UserService {

    User login(User user);

    int Reset(String cid, String pwd,String pwdMD5);

    int reg(String cid, String pwd, String realname, String email);

    void delUser(String cid);

    PageBean getAllUser(int page, int pageSize);

    User GetCertByid(String cid);

    int getPilotNum();

    int getAtcNum();

    int GetOnlineTimeByid(int cid);

    List<User> getAllUserWithList();

    void addTime(int cid);

    void addFlight(pilot pilot);

    void addHistoryList(pilot pilot);
}
