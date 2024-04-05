package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.PageBean;
import com.hyper.aluminium.pojo.User;

public interface UserService {

    User login(User user);

    int Reset(String cid, String pwd,String pwdMD5);

    int reg(String cid, String pwd, String realname, String email);

    PageBean getAllUser(int page, int pageSize);
}
