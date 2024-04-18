package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String FlightId;
    private String Cid;
    private String dep;
    private String arr;
    private String LoginTime;
    private String route ;
}
