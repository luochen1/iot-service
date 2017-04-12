package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.User;

/**
 * Created by songchengzhong on 2017/1/4.
 */
public interface EmailService {

    //发送账户验证email
    void sendRegisterCheckEmail(User user);

    //发送NormalEmail
    void sendEmail(String sendTo, String text);
}
