package com.campus.love.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginUtil {

    static private String appID;


    static private String appSecret;

    /**
     * 小程序全局唯一后台接口调用凭据
     */
    static private String access_token;

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

        log.info("ww:"+appID);
        JSONObject response = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin" +
                "/token?grant_type=client_credential&appid=" +appID+
                "&secret="+appSecret,JSONObject.class);

        log.info("response={}",response);
        access_token = response.getString("access_token");
        //获取不到token，则判定为出错
        if(access_token == null) {
            String errCode = response.getString("errcode");
            String errMsg = response.getString("errmsg");
        }

        //  to be continued
    }

    /**
     *
     * @param code 通过 wx.pluginLogin 获得的插件用户标志凭证 code，有效时间为5分钟，一个 code 只能获取一次 openpid。
     * @return openpid 插件用户的唯一标识。
     */
    public static String getPluginOpenPId(String code) {
        //构建请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> formEntity = new HttpEntity<String>("code:"+code, headers);
        JSONObject response = restTemplate.postForObject("https://api.weixin.qq.com/wxa/getpluginopenpid?access_token=" +
                access_token, formEntity, JSONObject.class);

        String errMsg = response.getString("errmsg");
        //成功获取openPid
        if ("ok".equals(errMsg)) {
            return response.getString("openpid");
        }
        //获取失败
        else {
            log.info("openPid Failed:"+errMsg);
            throw new ApiException(errMsg);
        }
    }

    public static void main(String[] args) {
        LoginUtil.getPluginOpenPId("ww");
    }
}
