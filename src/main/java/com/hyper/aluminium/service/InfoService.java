package com.hyper.aluminium.service;

import com.hyper.aluminium.pojo.atc;
import com.hyper.aluminium.pojo.pilot;

import java.util.List;

public interface InfoService {
    List<pilot> listOnlinePilots();
    List<atc> listOnlineATC();

}
