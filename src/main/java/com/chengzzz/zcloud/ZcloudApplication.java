package com.chengzzz.zcloud;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.fileEntity;
import com.chengzzz.zcloud.entity.filesTree;
import com.chengzzz.zcloud.exception.pathErrorException;
import com.chengzzz.zcloud.utils.filesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ZcloudApplication {

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

}
