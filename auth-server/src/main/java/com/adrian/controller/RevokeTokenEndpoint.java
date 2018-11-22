package com.adrian.controller;


import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fengdan 访问此资源需要完全身份验证
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    private final ConsumerTokenServices consumerTokenServices;

    @Autowired
    public RevokeTokenEndpoint(@Qualifier("consumerTokenServices") ConsumerTokenServices consumerTokenServices) {
        this.consumerTokenServices = consumerTokenServices;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public ResponseEntity<HttpStatusContent> revokeToken(@RequestParam(name = "accessToken") String access_token) {
        HttpStatusContent httpStatusContent;
        if (consumerTokenServices.revokeToken(access_token)) {
            httpStatusContent = new HttpStatusContent(OutputState.SUCCESS, "注销成功");
            return new ResponseEntity<>(httpStatusContent, HttpStatus.OK);
        } else {
            httpStatusContent = new HttpStatusContent(OutputState.FAIL, "注销失败");
            return new ResponseEntity<>(httpStatusContent, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
