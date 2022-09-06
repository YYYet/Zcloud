package com.chengzzz.zcloud.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chengzzz.zcloud.config.CommonConfig;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.model.ConfigDo;
import com.chengzzz.zcloud.service.commonconfig.Impl.CommonConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
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
//        log.info("初始化配置库");
//        initConfig();
//        log.info("配置库初始化完成");

    }
    //    public static void initConfig() throws IllegalAccessException {
//        Field[] fields = null;
//        try {
//            fields = CommonConfig.class.getFields();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//        if (fields == null){
//            return;
//        }
//        CommonConfig commonConfigEntity = new CommonConfig();
//        for (Field field : fields) {
//            System.out.println("这是当前类的属性 "+field.getName());
//            System.out.println("这是当前类的值 "+field.get(commonConfigEntity));
//            try {
//                initConfigData(field.getName(), field.get(commonConfigEntity).toString(), commonConfigEntity);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }
    public  void initConfig() throws IllegalAccessException {
        Field[] fields = null;
        try {
            fields = CommonConfig.class.getFields();
        }catch (Exception e){
            log.error("反射异常 {}", e);
        }

        if (fields == null){
            return;
        }
        CommonConfig commonConfigEntity = new CommonConfig();
        for (Field field : fields) {
            try {
                initConfigData(field.getName(), field.get(commonConfigEntity).toString(), commonConfigEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//    public  void initConfigData(String key, String value, CommonConfig entity) throws NoSuchFieldException, IllegalAccessException {
//        commonConfigService
//        if (!redisCacheUtil.hasKey(Constant.CONFIG + key)){
//            redisCacheUtil.setCacheObject(Constant.CONFIG + key, value);
//        }else {
//            Field f = CommonConfig.class.getDeclaredField(key);
//            f.set(entity,redisCacheUtil.getCacheObject(Constant.CONFIG + key).toString());
//        }
//    }

    /**
     * 配置类反推
     * @param key
     * @param value
     * @param entity
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void initConfigData(String key, String value, CommonConfig entity) throws NoSuchFieldException, IllegalAccessException {
        LambdaQueryWrapper<ConfigDo> lambdaQueryWrapper = Wrappers.<ConfigDo>lambdaQuery().eq(ConfigDo::getConfigName, key);
        long count = commonConfigService.count(lambdaQueryWrapper);
        if (count == 0){
            ConfigDo configDo = new ConfigDo();
            configDo.setConfigName(key);
            configDo.setConfigValue(value);
            commonConfigService.save(configDo);
        }else {
            Field f = CommonConfig.class.getDeclaredField(key);
            ConfigDo c = commonConfigService.getOne(lambdaQueryWrapper);
            f.set(entity, c.getConfigValue());
        }
    }
}
