package com.songchengzhong.iot_service.viewmodel;

import com.fasterxml.jackson.dataformat.xml.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by songchengzhong on 2017/2/6.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class WeiXinResp {
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "Content")
    private String content;
}
