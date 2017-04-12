package com.songchengzhong.iot_service.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Configuration
@ComponentScan(basePackages = {"com.songchengzhong.iot_service"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = EnableWebMvc.class)})
@ImportResource(locations = "classpath:spring-config.xml")
@Import(WebConfig.class)
@PropertySource("classpath:Messages_zh.properties")
public class RootConfig {

    /*
     *   Mybatis的session工厂,用来创建session
     */
    @Bean
    SqlSessionFactory sqlSessionFactory() {
        String resource = "mybatis-config.xml";//配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
        return factory;
    }

    /*
     *  Java Email
     */
    @Bean
    public JavaMailSender mailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = Resources.getResourceAsProperties("config.properties");
        mailSender.setHost(properties.getProperty("mailserver.host"));
        mailSender.setUsername(properties.getProperty("mailserver.username"));
        mailSender.setPassword(properties.getProperty("mailserver.password"));
        return mailSender;
    }
}
