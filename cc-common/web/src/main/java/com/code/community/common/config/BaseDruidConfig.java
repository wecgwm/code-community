package com.code.community.common.config;

import javax.annotation.PostConstruct;

public class BaseDruidConfig {
    /**
     * 解决druid报错discard long time none received connection
     */
    @PostConstruct
    public  void druidConfig(){
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
