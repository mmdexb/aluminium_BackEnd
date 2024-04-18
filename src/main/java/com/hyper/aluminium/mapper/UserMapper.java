package com.hyper.aluminium.mapper;

import com.hyper.aluminium.pojo.Flight;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.pojo.pilot;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {

    @Delete("DELETE FROM users where cid=#{cid}")
    void delUser(String cid);

    @Select("select * from users where cid=#{cid} and password=#{password}")
    User getcidAndPassword(User user);

    @Select("select * from users where cid=#{cid}")
    User IsCidExist(String cid);

    @Select("select * from users where cid=#{cid}")
    User FindByCid(String cid);

    @Select("select * from users where email=#{email}")
    User IsEmailExist(String email);

    @Update("update users set password=#{pwdMD5} where cid=#{cid}")
    void ResetPwd(String cid, String pwdMD5);

    @Insert("insert into users(cid,password,realname,email,level) values(#{cid},#{pwdMD5},#{realname},#{email},#{level})")
    void reg(String cid, String pwdMD5, String realname, String email,String level);

    @Select("select * from users")
    public List<User> getAllUser();

    @Select("SELECT COUNT(*) FROM aluminium.users")
    int getPilotNum();

    @Select("SELECT onlinetime FROM users where cid=#{cid}")
    int getOnlineTimeByid(int cid);

    @Update("UPDATE users SET onlinetime = onlinetime + 1 WHERE cid = #{cid}")
    void addTime(int cid);

    @Insert("INSERT INTO Flight (FlightTime, CID, DepartureAirport, ArrivalAirport, Altitude, Latitude, Longitude, AircraftType) " +
            "VALUES (#{flightTime}, #{cid}, #{departureAirport}, #{arrivalAirport}, #{altitude}, #{latitude}, #{longitude}, #{aircraftType})")
    void addFlight(@Param("cid") int cid,
                   @Param("flightTime") String flightTime,
                   @Param("departureAirport") String departureAirport,
                   @Param("arrivalAirport") String arrivalAirport,
                   @Param("altitude") double altitude,
                   @Param("latitude") double latitude,
                   @Param("longitude") double longitude,
                   @Param("aircraftType") String aircraftType);

    @Insert("INSERT into History (FlightId, cid, Dep, Arr, Route, Type, StartTime, EndTime) " +
            "VALUES (#{flightID},#{flight.cid},#{flight.dep},#{flight.arr},#{flight.route},#{flight.type},#{flight.loginTime},#{time}) ")
    void addHistoryList(Flight flight, String flightID, String time);
}
