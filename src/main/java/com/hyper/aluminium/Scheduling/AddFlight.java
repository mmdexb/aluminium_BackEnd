package com.hyper.aluminium.Scheduling;

import com.hyper.aluminium.pojo.pilot;
import com.hyper.aluminium.service.InfoService;
import com.hyper.aluminium.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddFlight {
    private static final Logger log = LoggerFactory.getLogger(AddFlight.class);
    @Autowired
    private InfoService infoService;
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 * * * * ?")
    public void Add(){
        List<pilot> pilots=new ArrayList<>();
        pilots=infoService.listOnlinePilots();
        for(pilot pilot:pilots){
            log.info("航行记录增加"+pilot.toString());
            userService.addFlight(pilot);
        }

    }
}
