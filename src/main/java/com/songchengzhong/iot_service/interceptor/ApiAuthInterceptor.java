package com.songchengzhong.iot_service.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songchengzhong.iot_service.common.Constants;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.UserService;
import com.songchengzhong.iot_service.view_model.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by songchengzhong on 2017/1/10.
 */
public class ApiAuthInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader(Constants.API_KEY);
        SocketUser user = userService.findByApiKey(apiKey);
        if (user == null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);//没有权限
            response.getWriter().write(new ObjectMapper().writeValueAsString("api-key认证失败"));
            response.flushBuffer();
            //System.out.println("api-key认证失败");
            return false;
        } else {
            request.setAttribute("user", user);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
