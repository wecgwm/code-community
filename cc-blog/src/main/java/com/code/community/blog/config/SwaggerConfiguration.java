package com.code.community.blog.config;

import com.code.community.common.config.BaseSwaggerConfiguration;
import com.code.community.common.model.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration extends BaseSwaggerConfiguration {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.code.community.blog")
                .title("博客服务")
                .description("博客相关接口文档")
                .version("1.0")
                .build();
    }

}
