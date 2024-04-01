package com.hyper.aluminium;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hyper.aluminium.pojo.pilot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SpringBootTest
class AluminiumApplicationTests {

	@Test
	public void listOnlinePilots() {
        String jsonUrl = "http://192.168.0.147/json.php";
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

                System.out.println(response);
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
            e.printStackTrace();
        }


		System.out.println(pilots);
    }

    @Test
    public void addCert() {
        String serverAddress = "192.168.0.147";
        int port = 3010; // Telnet默认端口号是23

        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 连接成功后发送指令
            out.println("pwd linus");

            // 读取服务器返回的响应
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Server: " + response);
                if (response.contains("Password correct.")) {
                    // 从用户获取参数
                    String cid = "4589";
                    String pwd = "3641";
                    String level = "STUDENT1";
                    // 发送命令
                    out.println("cert add " + cid + " " + pwd + " " + level);
                    break;
                }
            }

            // 关闭连接
            socket.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }


    }



