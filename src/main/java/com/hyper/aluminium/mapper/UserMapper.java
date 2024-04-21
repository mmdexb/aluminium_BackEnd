package com.hyper.aluminium.mapper;

import com.hyper.aluminium.pojo.AirportList;
import com.hyper.aluminium.pojo.Flight;
import com.hyper.aluminium.pojo.User;
import com.hyper.aluminium.pojo.pilot;
import org.apache.ibatis.annotations.*;


import java.util.List;
import java.util.Map;

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

    @Insert("insert into users(cid,password,realname,email,level,onlinetime) values(#{cid},#{pwdMD5},#{realname},#{email},#{level},0)")
    void reg(String cid, String pwdMD5, String realname, String email,String level);

    @Select("select * from users")
    List<User> getAllUser();

    @Select("SELECT COUNT(*) FROM aluminium.users")
    Integer getPilotNum();

    @Select("SELECT onlinetime FROM users where cid=#{cid}")
    Integer getOnlineTimeByid(int cid);

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

    @Insert("INSERT into History (FlightId, cid, Dep, Arr, Route, Type, StartTime, EndTime,CallSign) " +
            "VALUES (#{flightID},#{flight.cid},#{flight.dep},#{flight.arr},#{flight.route},#{flight.type},#{flight.loginTime},#{time},#{flight.Callsign}) ")
    void addHistoryList(Flight flight, String flightID, String time);

    //统计Dep和Arr中每个字符串的出现次数
    @MapKey("Field")
    List<Map<String, Integer>> GetAirportList();

    @Select("SELECT * FROM History WHERE cid=#{cid} ORDER BY StartTime DESC LIMIT 3")
    List<Flight> GetFlightByid(String cid);

    @MapKey("Field")
    List<Map<String, Integer>> GetAirportListByid(int cid);

    @MapKey("Field")
    List<Map<String, Integer>> GetTypeListByid(int cid);

    @Select("SELECT output.name FROM output WHERE icao=#{icao}")
    String GetChineseNameByicao(String icao);

    @Update("UPDATE users SET level=#{level} WHere cid=#{cid}")
    void ModifyLevel(String cid, String level);
}
