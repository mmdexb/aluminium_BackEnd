package com.hyper.aluminium.service.impl;

import com.hyper.aluminium.mapper.UserMapper;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.service.CertService;
import com.hyper.aluminium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicelmpl implements UserService {

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
}
