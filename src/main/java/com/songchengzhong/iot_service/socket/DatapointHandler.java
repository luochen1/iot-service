package com.songchengzhong.iot_service.socket;

import com.songchengzhong.iot_service.common.Strings;
import com.songchengzhong.iot_service.service.DataPointService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by songchengzhong on 2017/2/7.
 */
@Component("datapointHandler")
public class DatapointHandler extends TextWebSocketHandler {

    public static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>(1024);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("socket开启了");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("socket收到:" + payload);

        //关闭连接
        if ("close".equals(payload)) {
            try {
                session.close();
                removeSession(session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将payload放入map
        if (!Strings.isNullOrEmpty(payload) && payload.contains("in-time")) {
            sessionMap.put(payload, session);
            System.out.println("现在的map容量:" + sessionMap.size());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("socket连接关闭了");
        removeSession(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("socket出错了");
        if (session.isOpen()) {
            session.close();
        }
        removeSession(session);
    }

    private synchronized static void removeSession(WebSocketSession session) {
        List<Map.Entry<String, WebSocketSession>> collect = sessionMap.entrySet().stream().filter(p -> p.getValue() == session).collect(Collectors.toList());
        if (collect != null && collect.size() > 0) {
            Map.Entry<String, WebSocketSession> entry = collect.get(0);
            sessionMap.remove(entry.getKey());
        }
        System.out.println("现在的map容量:" + sessionMap.size());
    }
}
