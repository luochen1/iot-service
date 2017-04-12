package com.songchengzhong.iot_service.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.accept.ContentNegotiationManager;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private int id;
    private String name;
    private String value;
    private int actionTypeId;
    private int userId;

    private ActionType actionType;
    private User user;

}
