package com.relive.security.handler;

import com.relive.api.Result;
import com.relive.utils.JsonUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessDeniedException 异常处理器(目前未生效)
 * 未生效原因被全局异常捕获了，通过配置在异常处理器中配置AccessDeniedException捕获处理器
 *
 * @Author ReLive
 * @Date 2021/5/8-15:59
 */
@Deprecated
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(JsonUtils.objectToJson(Result.error(403, "未授权")));
    }
}