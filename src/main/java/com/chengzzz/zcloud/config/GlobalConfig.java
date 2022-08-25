package com.chengzzz.zcloud.config;

import com.chengzzz.zcloud.model.UserDo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Data
@Configuration
@PropertySource(value = {"classpath:/application.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "global")
public class GlobalConfig {
    private List<UserDo> otherAdmin;
}
