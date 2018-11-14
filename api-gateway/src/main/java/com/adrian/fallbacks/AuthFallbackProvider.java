package com.adrian.fallbacks;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 向用户管理 * 路由发起请求失败时的回滚处理
 * hystrix的回滚能力
 * Edgware.SR5
 *
 * @author asus
 * @ClassName MyFallbackProvider
 * @description TODO
 * @Date 2018/10/18 18:07
 * @Version 1.0v
 **/
@Slf4j
@Component
public class AuthFallbackProvider implements FallbackProvider {

    private static final String AUTH_SERVICE_DISABLE = "授权模块不可用";

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 根据执行失败的原因提供回退响应。
     * Provides a fallback response based on the cause of the failed execution.
     * <p>
     * 主要方法失败的原因
     *
     * @param cause cause of the main method failure
     *              后备反应
     * @return the fallback response
     */
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是黑盒子
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                JSONObject json = new JSONObject();
                json.put("code", "9999");
                json.put("msg", "服务故障，请稍后重试");
                if (cause != null && cause.getMessage() != null) {
                    log.error("调用:{} 异常：{}", getRoute(), AUTH_SERVICE_DISABLE);
                    log.error("调用:{} 异常：{}", getRoute(), cause.getMessage());
                    return new ByteArrayInputStream(json.toJSONString().getBytes(CHARSET_NAME));
                } else {
                    return new ByteArrayInputStream(json.toJSONString().getBytes(CHARSET_NAME));
                }
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和body中的内容编码一致，否则容易乱码
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }

    /**
     * 这个回退的路线将用于。
     * The route this fallback will be used for.
     * 回退的路线将用于。
     *
     * @return The route the fallback will be used for.
     */
    @Override
    public String getRoute() {
        // *: api服务id，如果需要所有调用都支持回退，则return "*"或return null
        return "*";
    }

    /**
     * Provides a fallback response.
     * 如果请求用户服务失败，返回什么信息给消费者客户端
     *
     * @return The fallback response.
     */
    @Override
    public ClientHttpResponse fallbackResponse() {
        return null;
    }
}
