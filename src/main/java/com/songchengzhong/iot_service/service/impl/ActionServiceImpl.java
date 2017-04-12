package com.songchengzhong.iot_service.service.impl;

import com.songchengzhong.iot_service.entity.*;
import com.songchengzhong.iot_service.repository.ActionRepository;
import com.songchengzhong.iot_service.repository.SensorActionRepository;
import com.songchengzhong.iot_service.repository.SensorRepository;
import com.songchengzhong.iot_service.service.ActionService;
import com.songchengzhong.iot_service.service.EmailService;
import com.songchengzhong.iot_service.viewmodel.AddAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by songchengzhong on 2017/3/20.
 */
@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    ActionRepository actionRepository;

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    SensorActionRepository sensorActionRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    Environment env;

    @Override
    public List<Action> findAllActionByUser(User user) {
        return actionRepository.findByUserId(user.getId());
    }

    @Override
    public void deleteById(int id) {
        actionRepository.delete(id);
    }

    @Override
    public void add(User user, AddAction addAction, String contextPath) {
        Action action = new Action();
        action.setName(addAction.getActionName());
        action.setUserId(user.getId());
        action.setActionTypeId(addAction.getActionType());
        if (addAction.getActionType() == 1) {//email,直接复制
            action.setValue(addAction.getEmailAddress());
        }
        if (addAction.getActionType() == 2) {//网址推送
            if (addAction.getActionSubType() == 2) {//推送到其他网址
                action.setValue(addAction.getHttpURL());
            } else if (addAction.getActionSubType() == 1) {//推送到本地
                Sensor sensor = sensorRepository.findById(addAction.getSwitchType());
                action.setValue(env.getProperty("webSite")
                        + contextPath + "/api/devices/" + sensor.getDeviceId() + "/sensors/" + sensor.getId() + "/datapoints");
            }
        }
        actionRepository.insert(action);
    }

    @Override
    public void TriggerAction(User user, DataPoint dataPoint) {
        Sensor sensor = sensorRepository.findById(dataPoint.getSensorId());
        if (sensor.getSensorTypeId() == 1) {//表明是数值传感器
            List<SensorAction> sensorActions = sensorActionRepository.findBySensorId(dataPoint.getSensorId());
            for (SensorAction sensorAction : sensorActions) {
                //进行条件的比较
                if (compare(dataPoint.getValue(), sensorAction.getOperator(), sensorAction.getValue())) {
                    int actionTypeId = sensorAction.getAction().getActionTypeId();
                    String sendInfo = sensorAction.getContent().replaceAll("\\$value", dataPoint.getValue());
                    String actionValue = sensorAction.getAction().getValue();
                    if (actionTypeId == 1) {//发送email
                        emailService.sendEmail(actionValue, sendInfo);
                    } else if (actionTypeId == 2) {//进行http请求
                        try {
                            URL url = new URL(actionValue);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setRequestProperty("Content-Type", "application/json");
                            connection.setRequestProperty("api-key", user.getApikey());
                            connection.setRequestProperty("Accept", "*/*");
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36 QQBrowser/4.1.4132.400");
                            connection.setRequestProperty("Connection", "keep-alive");
                            connection.setRequestProperty("Cache-Control", "no-cache");
                            OutputStream outputStream = connection.getOutputStream();
                            outputStream.write(sendInfo.getBytes("UTF-8"));
                            outputStream.flush();
                            outputStream.close();
                            connection.connect();
                            int code = connection.getResponseCode();
                            System.out.println(code);
                            connection.getInputStream();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    /**
     * 进行 值条件的比较
     *
     * @param acceptValue
     * @param operator
     * @param compareValue
     * @return
     */
    private boolean compare(String acceptValue, String operator, String compareValue) {
        double aDouble = Double.parseDouble(acceptValue);
        double bDouble = Double.parseDouble(compareValue);
        boolean result = false;
        switch (operator) {
            case ">":
                if (aDouble > bDouble) {
                    result = true;
                }
                break;
            case "=":
                if (aDouble == bDouble) {
                    result = true;
                }
                break;
            case "<":
                if (aDouble < bDouble) {
                    result = true;
                }
                break;
        }
        return result;
    }
}
