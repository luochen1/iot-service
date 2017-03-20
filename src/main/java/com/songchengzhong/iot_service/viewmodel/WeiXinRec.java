package com.songchengzhong.iot_service.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by songchengzhong on 2017/2/6.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinRec {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}
