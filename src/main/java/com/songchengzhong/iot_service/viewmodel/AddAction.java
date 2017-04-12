package com.songchengzhong.iot_service.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by songchengzhong on 2017/3/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAction {
    private String actionName;    //动作名称
    private Integer actionType;   //动作类型      1.email   2.网址推送
    private String emailAddress;  //email地址
    private Integer actionSubType;//子类动作   1.推送到开关  2.推送到其他网址
    private Integer switchType;   //开关id
    private String httpURL;       //推送的网址

}
