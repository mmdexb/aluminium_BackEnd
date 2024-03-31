package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class pilot {
    private String callsign;
    private int cid;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //海拔高度
    private double altitude;
    //空速
    private int speed;
    //航向
    private int heading;
    //起飞机场
    private String DepartureAirport;
    //到达机场
    private String ArrivalAirport;
    //航路
    private String route;
    //应答机
    private String squawk;
    //机型
    private String AircraftType;
    //巡航高度
    private int cruiseAltitude;
    //备降机场
    private String alternateAirport;
    //登录时间
    private String loginTime;

    

}
