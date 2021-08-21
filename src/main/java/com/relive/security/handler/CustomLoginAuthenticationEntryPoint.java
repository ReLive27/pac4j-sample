package com.relive.security.handler;

import com.relive.api.Result;
import com.relive.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 方案一：自定义未认证请求跳转URL
 * 方案二：定义返回类型401,前端判断401跳转到登录页面
 *
 * @Author ReLive
 * @Date 2021/5/11-10:23
 */
public class CustomLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JsonUtils.objectToJson(Result.error(401, "未认证")));
    }
}
