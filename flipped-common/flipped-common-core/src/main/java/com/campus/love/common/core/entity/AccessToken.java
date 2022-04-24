package com.campus.love.common.core.entity;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Component
public class AccessToken {

    private static String accessToken;

    /**
     * 过期时间（默认为7200s)
     */
    private static Long expiresTime;

    static private String appID;


    static private String appSecret;

    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        //读取yml配置文件，获得appId和appSecret
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("flipped-common/flipped-common-core/src/main/resources/application-key.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,Object> map;
        Yaml yaml = new Yaml();
        map = yaml.load(inputStream);
        Map<String,String> wechatMap = (Map)map.get("wechat");
        appID = wechatMap.get("appid");
        appSecret = wechatMap.get("appsecret");

       log.info("初始化");
        refreshAccessToken();

    }

    public static String getAppID() {
        return appID;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getAccessToken() {
        //时间戳过期
        if (isExpired()) {
            log.info("刷新AccessToken");
            refreshAccessToken();
        }
        else {
            log.info("未刷新AccessToken");
        }
        return accessToken;
    }

    public static Long getExpiresTime() {
        return expiresTime;
    }

    /**
     * 刷新token
     */
    public static void refreshAccessToken(){
        try {
            JSONObject response = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin" +
                    "/token?grant_type=client_credential&appid=" + appID +
                    "&secret=" + appSecret, JSONObject.class);
            //获取不到token，则判定为出错
            if (StringUtils.isNullOrEmpty(response.getString("access_token"))) {
                String errCode = response.getString("errcode");
                String errMsg = response.getString("errmsg");
                return;
            }
            accessToken = response.getString("access_token");
            Long expiresIn = response.getLong("expires_in");
            expiresTime = System.currentTimeMillis()+ expiresIn * 1000;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前token是否过期
     * @return
     */
    public static Boolean isExpired(){
        log.info("system time:"+System.currentTimeMillis());
        log.info("expiresTime:"+expiresTime);
        return System.currentTimeMillis() >= expiresTime;
    }
}

