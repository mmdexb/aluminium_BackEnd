package com.hyper.aluminium.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hyper.aluminium.pojo.atc;
import com.hyper.aluminium.pojo.pilot;
import com.hyper.aluminium.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Service
public class InfoServiceImpl implements InfoService {
    private static final Logger log = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Value("${fsd.ip}")
    private String ip;

    @Override
    public List<pilot> listOnlinePilots() {
        String jsonUrl = "http://"+ip+"/json.php";
        List<pilot> pilots = null;
        try {
            pilots = new ArrayList<>();
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonObject = JSONObject.parseObject(response.toString());
                JSONArray pilotArray = jsonObject.getJSONArray("Pilot");

                for (int i = 0; i < pilotArray.size(); i++) {
                    JSONObject pilotObject = pilotArray.getJSONObject(i);
                    pilot p = new pilot(
                            pilotObject.getString("callsign"),
                            pilotObject.getIntValue("cid"),
                            pilotObject.getDoubleValue("latitude"),
                            pilotObject.getDoubleValue("longitude"),
                            pilotObject.getDoubleValue("altitude"),
                            pilotObject.getIntValue("speed"),
                            pilotObject.getIntValue("heading"),
                            pilotObject.getString("departure_airport"),
                            pilotObject.getString("arrival_airport"),
                            pilotObject.getString("route"),
                            pilotObject.getString("squawk_code"),
                            pilotObject.getString("aircraft_type"),
                            pilotObject.getIntValue("cruise_altitude"),
                            pilotObject.getString("alternate_airport"),
                            pilotObject.getString("logintime")
                    );
                    pilots.add(p);
                }
            } else {
                System.out.println("HTTP GET请求失败：" + responseCode);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }


        return pilots;
    }

    @Override
    public List<atc> listOnlineATC() {

        String jsonUrl = "http://"+ip+"/json.php";
        List<atc> atcs = null;
        try {
            atcs = new ArrayList<>();
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonObject = JSONObject.parseObject(response.toString());
                JSONArray atcArray = jsonObject.getJSONArray("ATC");

                for (int i = 0; i < atcArray.size(); i++) {
                    JSONObject atcObject = atcArray.getJSONObject(i);
                    atc a = new atc(
                            atcObject.getString("callsign"),
                            atcObject.getIntValue("cid"),
                            atcObject.getDoubleValue("latitude"),
                            atcObject.getDoubleValue("longitude"),
                            atcObject.getString("frequency"),
                            atcObject.getString("logintime")
                    );
                    atcs.add(a);
                }
            } else {
                System.out.println("HTTP GET请求失败：" + responseCode);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return atcs;


    }
}
