package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.*;

import java.util.List;
import java.util.Map;

public interface InfoService {
    List<pilot> listOnlinePilots();
    List<atc> listOnlineATC();
    List<Flight> listOnlineFlight();

    List<Map<String, Integer>> GetAirportList();

    RouteInfo GetRoute(String dep, String arr) throws Exception;
}
