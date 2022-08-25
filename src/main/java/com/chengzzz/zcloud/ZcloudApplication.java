package com.chengzzz.zcloud;

import com.chengzzz.zcloud.config.GlobalConfig;
import com.chengzzz.zcloud.config.ZcloudInitConfig;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
public class ZcloudApplication {
    @Resource
    ZcloudInitConfig zcloudInitConfig;
    @Resource
    GlobalConfig globalConfig;
    @Resource
    RedisCacheUtil redisCacheUtil;
    public static void main(String[] args) {
        SpringApplication.run(ZcloudApplication.class, args);
    }

    /**
     * Tomcat配置 路径传参
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

    @PostConstruct
    public void init(){
        clearBaseCache();

        redisCacheUtil.setCacheObject(Constant.DEFAULT_USER_NAME, zcloudInitConfig.getDefaultAdminUsername());
        redisCacheUtil.setCacheObject(Constant.DEFAULT_USER_PASSWORD, zcloudInitConfig.getDefaultAdminPassword());
        redisCacheUtil.setCacheList(Constant.GLOBAL_OTHER_ADMIN, globalConfig.getOtherAdmin());

    }

    public void clearBaseCache(){
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_NAME);
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_PASSWORD);
        redisCacheUtil.deleteObject(Constant.GLOBAL_OTHER_ADMIN);
    }


}
