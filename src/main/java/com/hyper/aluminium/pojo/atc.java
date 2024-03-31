package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class atc {
    private String callsign;
    private int cid;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //频率
    private String frequency;
    //登入时间戳
    private String loginTime;



}
