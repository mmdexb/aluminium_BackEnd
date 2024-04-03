package com.hyper.aluminium.mapper;

import com.hyper.aluminium.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Select("select * from users where cid=#{cid} and password=#{password}")
    User getcidAndPassword(User user);

    @Select("select * from users where cid=#{cid}")
    User IsCidExist(String cid);

    @Select("select * from users where cid=#{cid}")
    User FindByCid(String cid);

    @Select("select * from users where email=#{email}")
    User IsEmailExist(String email);

    @Update("update users set password=#{pwdMD5} where cid=#{cid}")
    void ResetPwd(String cid, String pwdMD5);

    @Insert("insert into users(cid,password,realname,email,level) values(#{cid},#{pwdMD5},#{realname},#{email},#{level})")
    void reg(String cid, String pwdMD5, String realname, String email,String level);

}
