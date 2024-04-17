package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class event {
    private int eventid;
    private String eventname;
    private String eventtime;
    private String dep;
    private String arr;
    private String eventroute;
    private String eventinfo;

}
