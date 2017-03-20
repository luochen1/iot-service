package com.songchengzhong.iot_service.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.songchengzhong.iot_service.viewmodel.WeiXinRec;
import com.songchengzhong.iot_service.viewmodel.WeiXinResp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by songchengzhong on 2017/2/6.
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {
    //微信验证
    @GetMapping
    public void weixin(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        String signature = request.getParameter("signature");//微信加密签名

        String token = "sczero";//自己设置的token
        String timestamp = request.getParameter("timestamp");//时间戳
        String nonce = request.getParameter("nonce");//随机数

        String echostr = request.getParameter("echostr");//随机字符串

        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] bytes = messageDigest.digest((arr[0] + arr[1] + arr[2]).getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;//将有符号的bytes转换为int,向上转型
            if (value < 16) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(value));
        }

        if (signature.equals(stringBuilder.toString())) {
            PrintWriter writer = response.getWriter();
            writer.write(echostr);
            writer.flush();
            writer.close();
        }
    }

    //接受传递过来的消息
    @PostMapping
    public void weixinAccept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Stream<String> lines = reader.lines();
        StringBuilder sb = new StringBuilder();
        lines.forEach(p -> sb.append(p));
        System.out.println(sb.toString());

        XmlMapper mapper = new XmlMapper();
        WeiXinRec weiXinRec = mapper.readValue(sb.toString(), WeiXinRec.class);
        System.out.println("接受的消息:" + weiXinRec);

        WeiXinResp weiXinResp = new WeiXinResp(weiXinRec.getFromUserName(), weiXinRec.getToUserName(), new Date().getTime(), "text", "白痴~");
        String value = mapper.writeValueAsString(weiXinResp);
        System.out.println("响应的消息:" + value);
        response.getWriter().write(value);
    }

}
