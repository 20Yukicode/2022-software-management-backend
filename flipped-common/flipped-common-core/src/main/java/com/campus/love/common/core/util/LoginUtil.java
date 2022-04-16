package com.campus.love.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.campus.love.common.core.api.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginUtil {
    static final private String appID = "wx0efdf8c94fecea45";
    static final private String appSecret = "261b135d5e36f7cac172a06f837d8f74";
    /**
     * 小程序全局唯一后台接口调用凭据
     */
    static private String access_token;
    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        JSONObject response = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin" +
                "/token?grant_type=client_credential&appid=" +appID+
                "&secret="+appSecret,JSONObject.class);

        log.info("response={}",response);
        access_token = response.getString("access_token");
        //获取不到token，则判定为出错
        if(access_token == null) {
            String errCode = response.getString("errcode");
            //String errMsg = response.getString("errmsg");
        }

        //  to be continued
    }

    /**
     *
     * @param code 通过 wx.pluginLogin 获得的插件用户标志凭证 code，有效时间为5分钟，一个 code 只能获取一次 openpid。
     * @return openpid 插件用户的唯一标识。
     */
    public static MessageModel getPluginOpenPId(String code) {
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
            return MessageModel.success(errMsg,response.getString("openpid"));
        }
        //获取失败
        else {
            log.info("openPid Failed:"+errMsg);
            return MessageModel.failed("errMsg");
        }
    }

    public static void main(String[] args) {
        LoginUtil.getPluginOpenPId("ww");
    }
}
