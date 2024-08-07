package com.hyper.aluminium.service.impl;


import com.hyper.aluminium.controller.CertController;
import com.hyper.aluminium.mapper.UserMapper;
import com.hyper.aluminium.service.CertService;
import com.hyper.aluminium.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class CertServiceImpl implements CertService {
    private static final Logger log = LoggerFactory.getLogger(CertServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Value("${fsd.ip}")
    private String ip;

    @Value("${fsd.port}")
    private int port;

    @Value("${fsd.pwd}")
    private String Spwd;


    @Override
    public String addCert(String cid, String pwd, String level) {
        String serverAddress = ip;

        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 连接成功后发送指令
            out.println("pwd "+Spwd);

            // 读取服务器返回的响应
            String response;
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Password correct.")) {
                    out.println("cert add " + cid + " " + pwd + " " + level);
                    break;
                }
            }

            // 再次读取服务器返回的响应，判断添加结果
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Certificate already exists.")) {
                    socket.close();
                    return "cert添加失败，CID已存在";
                } else if (response.contains("Your change has been executed")) {
                    socket.close();
                    return "cert添加成功";
                }
            }

            socket.close();
            return "出现服务器内部错误，添加失败";
        } catch (IOException e) {

            return "出现服务器内部错误，添加失败";
        }
    }

    @Override
    public String delCert(String cid) {
        try {
            Socket socket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 连接成功后发送指令
            out.println("pwd "+Spwd);

            // 读取服务器返回的响应
            String response;
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Password correct.")) {
                    out.println("cert delete " + cid);
                    break;
                }
            }
            // 再次读取服务器返回的响应，判断删除结果
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Certificate does not exist.")) {
                    socket.close();
                    return "CID不存在，删除失败";
                } else if (response.contains("Your change has been executed")) {
                    socket.close();
                    return "cert删除成功";
                }
            }

            socket.close();
            return "出现服务器内部错误，删除失败";
        } catch (IOException e) {

            return "出现服务器内部错误，删除失败";
        }
    }

    @Override
    public String modCertLevel(String cid, String level) {
        try {
            Socket socket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 连接成功后发送指令
            out.println("pwd "+Spwd);

            // 读取服务器返回的响应
            String response;
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Password correct.")) {
                    out.println("cert modify " + cid + " " + level);
                    break;
                }
            }

            // 再次读取服务器返回的响应，判断修改结果
            while ((response = in.readLine()) != null) {
                log.info("Server: " + response);
                if (response.contains("Certificate does not exist.")) {
                    socket.close();
                    return "CID不存在，修改失败";
                } else if (response.contains("Your change has been executed")) {
                    socket.close();
                    userMapper.ModifyLevel(cid,level);
                    return "等级修改成功";
                }
            }


            socket.close();


            return "出现服务器内部错误，修改失败";
        } catch (IOException e) {

            return "出现服务器内部错误，修改失败";
        }
    }

    @Override
    public String addCertToTXT(String cid, String level,String password) {

        // 计算token
        String ps="linus";
        String token = DigestUtils.md5DigestAsHex((ps).getBytes());

        // 构建请求参数
        String urlParameters = "cid=" + cid + "&password=" + password + "&level=" + level + "&token=" + token;

        // 发送HTTP请求
        return HttpUtil.sendPostRequest("http://demo.linuschen.ink/fsd/DataBase2Cert.php", urlParameters);
    }


}


