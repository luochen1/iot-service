package com.songchengzhong.iot_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    private int id;

    private String name;

    private String description;

    private String unit;

    private String symbol;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    private int deviceId;

    @JsonIgnore
    private int sensorTypeId;

    @JsonIgnore
    private Device device;

    @JsonIgnore
    private SensorType sensorType;

    @JsonIgnore
    private List<DataPoint> dataPoints;

    @JsonIgnore
    private List<SensorAction> sensorActions;
}
