package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {
    private String route;
    private String distance;
    private String nodeInfomation;
    private String SID;
    private String STAR;
}
