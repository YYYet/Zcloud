package com.chengzzz.zcloud.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chengzzz.zcloud.config.CommonConfig;
import com.chengzzz.zcloud.model.ConfigDo;
import com.chengzzz.zcloud.service.commonconfig.Impl.CommonConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * 通用数据库初始化
 *
 * @author Yet
 * @date 2022/09/06 22:50
 **/
@Component
@Slf4j
public class CommonConfigRunner implements ApplicationRunner {

    @Resource
    private CommonConfigImpl commonConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化配置库");
        initConfig();
        log.info("配置库初始化完成");
    }


    /**
     * 初始化配置
     *
     * @throws IllegalAccessException 非法访问异常
     */
    public void initConfig() throws IllegalAccessException {
        Field[] fields = null;
        try {
            fields = CommonConfig.class.getFields();
        } catch (Exception e) {
            log.error("反射异常 {}", e);
        }

        if (fields == null) {
            return;
        }
        CommonConfig commonConfigEntity = new CommonConfig();
        for (Field field : fields) {
            try {
                initConfigData(field.getName(), field.get(commonConfigEntity).toString(), commonConfigEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 初始化配置数据
     *
     * @param key    关键
     * @param value  价值
     * @param entity 实体
     * @throws NoSuchFieldException   文件不存在异常
     * @throws IllegalAccessException 非法访问异常
     */
    public void initConfigData(String key, String value, CommonConfig entity) throws NoSuchFieldException, IllegalAccessException {
        LambdaQueryWrapper<ConfigDo> lambdaQueryWrapper = Wrappers.<ConfigDo>lambdaQuery().eq(ConfigDo::getConfigName, key);
        long count = commonConfigService.count(lambdaQueryWrapper);
        if (count == 0) {
            ConfigDo configDo = new ConfigDo();
            configDo.setConfigName(key);
            configDo.setConfigValue(value);
            commonConfigService.save(configDo);
        } else {
            Field f = CommonConfig.class.getDeclaredField(key);
            ConfigDo c = commonConfigService.getOne(lambdaQueryWrapper);
            f.set(entity, c.getConfigValue());
        }
    }
}
