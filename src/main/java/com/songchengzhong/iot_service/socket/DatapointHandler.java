package com.songchengzhong.iot_service.socket;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by songchengzhong on 2017/2/7.
 */
public class DatapointHandler extends TextWebSocketHandler {

    DataPointService dataPointService;

    public DatapointHandler(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("连接开启了");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("收到:" + payload);

        //关闭连接
        if("close".equals(payload)){
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接关闭了");
    }
}
