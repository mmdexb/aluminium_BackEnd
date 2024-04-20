package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user2{
    String cid;
    String realname;
    String level;
    String email;
    String LoveAirport;
    String LoveType;
    String time;

}