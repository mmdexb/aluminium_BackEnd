package com.hyper.aluminium.mapper;

import com.hyper.aluminium.pojo.notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from board order by id desc")
    List<notice> getAllnotice();

    @Insert("INSERT INTO board (title, author, time, content) VALUES (#{title},#{author},#{time},#{content})")
    void addNotice(notice notice);

    @Delete("DELETE from board where id=#{id}")
    void delNotice(int id);

    @Update("UPDATE board set title=#{notice.title},author=#{notice.author},time=#{notice.time},content=#{notice.content} Where id=#{id}")
    void UpdateNotice(notice notice, int id);
}
