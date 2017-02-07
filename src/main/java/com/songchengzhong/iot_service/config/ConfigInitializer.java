package com.songchengzhong.iot_service.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * Created by songchengzhong on 2017/1/1.
 * 继承  抽象注解配置调度Servlet初始化器
 */
public class ConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharacterEncodingFilter("utf-8", true), new HiddenHttpMethodFilter()};
    }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        //上传文件的最低配置
        registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }
}
