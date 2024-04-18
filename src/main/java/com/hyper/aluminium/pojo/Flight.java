package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class Flight {
    private String FlightId;
    private String Callsign;
    private int Cid;
    private String dep;
    private String arr;
    private String LoginTime;
    private String route ;
    private String type;

    public Flight(String callsign, int cid, String dep,  String arr, String loginTime,String route,String type) {
        this.Callsign = callsign;
        this.Cid = cid;
        this.dep = dep;
        this.LoginTime = loginTime;
        this.arr = arr;
        this.route = route;
        this.type=type;
    }
}

