package com.campus.love.common.core.domain;

import lombok.Getter;

/**
 * version 1.0
 * 通过代码来配置拦截权限，现已废弃
 * 默认关闭网关鉴权，默认关闭feign鉴权，默认关闭模块鉴权
 */
@Deprecated
@Getter
public class Authentication {

    private Boolean gateway;

    private Boolean feign ;

    private Boolean module;

    private Authentication(Boolean gateway, Boolean feign, Boolean module) {
        this.gateway = gateway;
        this.feign = feign;
        this.module = module;
    }

    public static Authentication of(){
        return new Authentication(false,false,false);
    }

    /**
     * 必须从网关调用服务（网关鉴权）
     * 除了/feign/
     * @return
     */
    public Authentication mustAuthGateway(){
        this.gateway=true;
        return this;
    }

    /**
     * 其他服务/人远程调用你这个服务必须鉴权
     * @return
     */
    public Authentication mustAuthFeign(){
        this.feign=true;
        return this;
    }

    /**
     * 启用服务内部特定拦截器
     * @return
     */
    public Authentication enableAuthModule(){
        this.module=true;
        return this;
    }
}
