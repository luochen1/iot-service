package com.songchengzhong.iot_service.view_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketUser extends User implements Serializable {

    private int id;

    private String username;

    private String email;

    private String password;

    private String apikey;

    private String introduction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    private Date loginTime;

    private boolean isActive;

    private String activeCode;

    public SocketUser(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        apikey = user.getApikey();
        introduction = user.getIntroduction();
        createdAt = user.getCreatedAt();
        loginTime = user.getLoginTime();
        isActive = user.isActive();
        activeCode = user.getActiveCode();
    }
}
