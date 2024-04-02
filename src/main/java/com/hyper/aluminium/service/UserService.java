package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.User;

public interface UserService {

    User login(User user);

    int Reset(String cid, String pwd,String pwdMD5);
}
