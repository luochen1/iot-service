package com.songchengzhong.iot_service.socket;

import com.songchengzhong.iot_service.common.Strings;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("收到:" + payload);

        //关闭连接
        if ("close".equals(payload)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //即时获取数据
        if (!Strings.isNullOrEmpty(payload) && payload.contains("in-time")) {
            Date beginTime = new Date();
            Integer count = 0;
            try {
                while (true) {
                    Map<String, Object> inTimeData = dataPointService.getInTimeData(payload, beginTime, count);
                    if (inTimeData != null) {
                        Integer returnCount = (Integer) inTimeData.get("returnCount");
                        if (returnCount > count) {
                            count = returnCount;
                            session.sendMessage(new TextMessage((String) inTimeData.get("returnMsg")));
                        }
                    }
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    session.close();
                    System.out.println("session关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接关闭了");
    }
}
