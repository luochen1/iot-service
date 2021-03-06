package com.songchengzhong.iot_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionType {
    private int id;
    private String name;

    private List<Action> actions;
}
