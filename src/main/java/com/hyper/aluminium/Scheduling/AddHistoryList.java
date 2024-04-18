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
public class AddHistoryList {
    private static final Logger log = LoggerFactory.getLogger(AddHistoryList.class);
    //创建三个list 一个用于存放当前在线的机组，一个用于存放历史在线机组，一个用于存放归档机组
    //算法流程: 每分钟获取当前在线的机组，并且复制一份到历史在线机组（如果有新增 循环一下历史机组中有无在线的机组） 每次执行时，则查看
    //存在历史机组在但是在线机组不在的机组，如果发现，则放置到归档机组，并且保存到数据库。

    @Autowired
    private InfoService infoService;
    @Autowired
    private UserService userService;

    List<pilot> HistoryList=new ArrayList<>();
    List<pilot> ResultList=new ArrayList<>();
    @Scheduled(cron = "0 * * * * ?")
    public void FlightTrack(){
        //在线机组
        List<pilot> OnlineList=infoService.listOnlinePilots();
        log.info("开始执行函数了 在线机组 :"+OnlineList.toString());
        log.info("历史在线机组 :"+HistoryList.toString());


        //判断HistoryList是否为空 为空则复制Online List
        if(HistoryList.isEmpty()){
            HistoryList.addAll(OnlineList);
            log.info("历史在线机组为空 复制Online List :"+HistoryList.toString());
        }

        //循环有无在线机组中存在 但是历史机组中不存在的机组 并将其添加到历史机组（新上线机组）
        for(int i=0;i<OnlineList.size();i++){
            if(!HistoryList.contains(OnlineList.get(i))){
                HistoryList.add(OnlineList.get(i));
                log.info("在线机组中存在 但是历史在线机组中不存在的机组 :"+OnlineList.get(i).toString());
            }
        }
        //循环有无在线机组中不存在 但是历史机组中存在 并将其添加到归档机组（原来在线 现在下线了）
        for(int i=0;i<HistoryList.size();i++){
            if(!OnlineList.contains(HistoryList.get(i))){
                ResultList.add(HistoryList.get(i));
                log.info("在线机组中不存在 但是历史在线机组中存在的机组 添加到归档:"+HistoryList.get(i).toString());
                //在历史机组中删除他
                HistoryList.remove(HistoryList.get(i));
            }
        }

        //如果归档机组不为空则将归档机组中的机组添加到数据库中
        if(!ResultList.isEmpty()){
            for(int i=0;i<ResultList.size();i++){
                userService.addHistoryList(ResultList.get(i));
                log.info("将归档机组中的机组添加到数据库中 :"+ResultList.get(i).toString());
                //删除归档机组中的机组
                ResultList.remove(i);
            }
        }










    }


}
