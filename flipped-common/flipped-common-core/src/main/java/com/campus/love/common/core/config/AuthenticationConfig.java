package com.campus.love.common.core.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 此属性也可以在nacos动态配置
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix ="auth")
@Data
public class AuthenticationConfig {

    private boolean mustGateway;

    private boolean mustFeign;

}
