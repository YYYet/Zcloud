package com.chengzzz.zcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Zcloud初始化配置
 *
 * @author Yet
 * @date 2022/08/25 20:07
 **/

@Component
@Configuration
public class ZcloudInitConfig {

    public static String DEFAULT_ADMIN_USERNAME;
    public static String DEFAULT_ADMIN_PASSWORD;
    public  String getDefaultAdminUsername() {
        return DEFAULT_ADMIN_USERNAME;
    }

    @Value("${default.admin.username}")
    public  void setDefaultAdminUsername(String defaultAdminUsername) {

        DEFAULT_ADMIN_USERNAME = defaultAdminUsername;
    }

    public  String getDefaultAdminPassword() {
        return DEFAULT_ADMIN_PASSWORD;
    }

    @Value("${default.admin.password}")
    public  void setDefaultAdminPassword(String defaultAdminPassword) {

        DEFAULT_ADMIN_PASSWORD = defaultAdminPassword;
    }

}
