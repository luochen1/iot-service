package com.songchengzhong.iot_service.common;

import java.util.UUID;

/**
 * Created by songchengzhong on 2017/1/3.
 */
public class UUIDs {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
