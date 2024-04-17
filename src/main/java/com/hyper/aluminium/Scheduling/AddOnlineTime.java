package com.hyper.aluminium.Scheduling;

import com.hyper.aluminium.pojo.atc;
import com.hyper.aluminium.service.InfoService;
import com.hyper.aluminium.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.hyper.aluminium.pojo.pilot;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddOnlineTime {
    private static final Logger log = LoggerFactory.getLogger(AddOnlineTime.class);
    @Autowired
    private InfoService infoService;
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 * * * * ?")
    public void AddTime() {
        List<pilot> pilots = new ArrayList<>();
        pilots=infoService.listOnlinePilots();
        for (pilot pilot : pilots) {
            log.info(pilot.getCid()+"的时间增加了");
            userService.addTime(pilot.getCid());
        }

        List<atc> atcs=new ArrayList<>();
        atcs=infoService.listOnlineATC();
        for (atc atc : atcs) {
            log.info(atc.getCid()+"的时间增加了");
            userService.addTime(atc.getCid());
        }

    }


}
