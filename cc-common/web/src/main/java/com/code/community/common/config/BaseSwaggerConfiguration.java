package com.code.community.common.config;

import com.code.community.common.model.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public abstract class BaseSwaggerConfiguration {
    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties){
        return new Docket(DocumentationType.SWAGGER_2)
                //初始化ApiInfo
                .apiInfo(apiInfo(swaggerProperties))
                //初始化并返回一个API选择构造器
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    @Bean
    public abstract SwaggerProperties swaggerProperties();
}
