package com.campus.love.common.core.domain;

import lombok.Getter;

/**
 * 默认关闭网关鉴权，默认关闭feign鉴权，默认关闭模块鉴权
 */
@Getter
public class EnableProperties {

    private Boolean gateway;

    private Boolean feign ;

    private Boolean module;

    private EnableProperties(Boolean gateway, Boolean feign, Boolean module) {
        this.gateway = gateway;
        this.feign = feign;
        this.module = module;
    }

    public static EnableProperties of(){
        return new EnableProperties(false,false,false);
    }

    public EnableProperties enableGateway(){
        this.gateway=true;
        return this;
    }

    public EnableProperties enableFeign(){
        this.feign=true;
        return this;
    }

    public EnableProperties enableModule(){
        this.module=true;
        return this;
    }
}
