package com.code.community.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
// 此注解会生成equals(Object other) 和 hashCode()方法。
// 1.它默认使用非静态，非瞬态的属性。且不使用父类的属性，即默认为false
// 2.指定为true则在1的基础上调用父类的方法
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;

}
