package com.adrian.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 访问被拒绝处理程序
 *
 * @author sang
 * @date 2017/12/29
 */
@Log4j
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {


        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        json.put("code", "401");
        json.put("msg", "权限不足，请联系管理员!");
        PrintWriter out = resp.getWriter();
        log.info(httpServletRequest.getParameter("username") + "用户沒有权限访问:" + httpServletRequest.getRequestURI());
        out.write(new ObjectMapper().writeValueAsString(json));
        out.flush();
        out.close();
    }
}
