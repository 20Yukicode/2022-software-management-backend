package com.campus.love.user.config;

import com.campus.love.common.core.config.BaseSwaggerConfig;
import com.campus.love.common.core.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.campus.love.user.controller")
                .title("flipped-user系统")
                .description("demo")
                .contactName("nzh and ww")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }

}
