package com.adrian.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author asus
 */
@Component
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(com.fasterxml.jackson.core.filter.TokenFilter.class);

    /**
     * 定义filter的类型，有pre、route、post、error四种
     * 可以在请求被路由之前调用
     *
     * @return String
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
     *
     * @return int
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     *
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * /filter需要执行的具体操作
     *
     * @return Object
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        // 获取请求的参数
        String token = request.getParameter("token");
        logger.info("--->>> TokenFilter 请求的token是: {}", request.getParameter("token"));
        if (StringUtils.isNotBlank(token)) {
            //对请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            logger.info("--->>> TokenFilter 请求的token是: token is ok ");
            return null;
        } else {
            JSONObject json = new JSONObject();
            json.put("code", "400");
            json.put("msg", "token is empty");
            //不对其进行路由
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.set("isSuccess", false);
            ctx.setResponseBody(json.toJSONString());
            logger.info("--->>> TokenFilter 请求的token是: token is empty ");
            return null;
        }
    }

}