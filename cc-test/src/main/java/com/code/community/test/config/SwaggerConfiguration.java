package com.code.community.test.config;

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
                .apiBasePackage("com.code.community.test")
                .title("答题服务")
                .description("答题相关接口文档")
                .version("1.0")
                .build();
    }

}
