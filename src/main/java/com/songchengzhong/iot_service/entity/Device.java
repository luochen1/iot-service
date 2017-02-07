package com.songchengzhong.iot_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Device implements Serializable {
    private int id;

    private String name;

    private String description;

    private String city;

    private double longitude;

    private double latitude;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @JsonIgnore
    private String imageURL;

    private int userId;

    @JsonIgnore
    private User user;

    @JsonIgnore
    private List<Sensor> sensors;
}
