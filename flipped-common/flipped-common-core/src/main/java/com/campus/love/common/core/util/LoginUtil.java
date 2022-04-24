package com.campus.love.common.core.util;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.campus.love.common.core.entity.AccessToken;
import com.campus.love.common.core.util.dto.SessionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class LoginUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * auth.code2Session
     * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     * @param code 通过 wx.login 获得的插件用户标志凭证 code，有效时间为5分钟，一个 code 只能获取一次 openpid。
     * @return openpid 插件用户的唯一标识。
     *
     */
    public static SessionDto code2Session(String code) {

        String response = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=" +
                AccessToken.getAppID()+"&secret="+AccessToken.getAppSecret()+"&js_code="+code+
                "&grant_type=authorization_code",String.class);

        SessionDto session = JSONObject.parseObject(response,SessionDto.class);

        //SessionDto session = JSON.toJavaObject(json, SessionDto.class);

        log.info("session:"+session.toString());

        return session;
    }

    public static void main(String[] args) {
        LoginUtil.code2Session("031QMbll2JtE394Zuoll2gb1TT3QMbl8");
    }
}
