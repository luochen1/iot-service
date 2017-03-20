package com.songchengzhong.iot_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by songchengzhong on 2017/3/10.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorAction {
    private Integer id;
    private String operator;
    private String value;
    private String content;
    private Integer actionId;
    private Integer sensorId;
    private Action action;
    private Sensor sensor;
}
