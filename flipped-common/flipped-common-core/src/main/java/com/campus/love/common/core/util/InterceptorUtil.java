package com.campus.love.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.campus.love.common.core.api.IErrorCode;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.api.ResultCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InterceptorUtil {

    public static void setReturn(HttpServletResponse response) throws IOException {
        setReturn(response, ResultCode.FORBIDDEN);
    }

    public static void setReturn(HttpServletResponse response, IErrorCode code) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //response.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String json = JSONObject.toJSONString(MessageModel.failed(code));
        response.getWriter().print(json);
    }
}
