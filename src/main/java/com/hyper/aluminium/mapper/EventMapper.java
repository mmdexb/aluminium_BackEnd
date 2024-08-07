package com.hyper.aluminium.mapper;

import com.hyper.aluminium.pojo.event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EventMapper {
    @Select("select * from events order by eventid desc")
    List<event> getAllEvent();

    @Select("insert into events(eventname,eventtime,dep,arr,eventroute,eventinfo) values(#{eventname},#{eventtime},#{dep},#{arr},#{eventroute},#{eventinfo})")
    void addEvent(event event);

    @Select("select * from events where eventid=#{id}")
    event GetEvent(int id);

    @Delete("delete from events where eventid=#{id}")
    void deleteEventByid(int id);

    @Update("update events set eventname=#{event.eventname},eventtime=#{event.eventtime},dep=#{event.dep},arr=#{event.arr},eventroute=#{event.eventroute},eventinfo=#{event.eventinfo} where eventid=#{event.eventid}")
    void updateEvent(event event,int id);
}
