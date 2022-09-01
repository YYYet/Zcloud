package com.chengzzz.zcloud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chengzzz.zcloud.config.CommonConfig;
import com.chengzzz.zcloud.config.GlobalConfig;
import com.chengzzz.zcloud.config.ZcloudInitConfig;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.FileHandlerResult;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.model.ConfigDo;
import com.chengzzz.zcloud.service.cacheservice.impl.CacheServiceImpl;
import com.chengzzz.zcloud.service.commonconfig.Impl.CommonConfigImpl;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class ZcloudApplication {
    @Resource
    ZcloudInitConfig zcloudInitConfig;
    @Resource
    GlobalConfig globalConfig;
    @Resource
    RedisCacheUtil redisCacheUtil;

    @Resource
    CacheServiceImpl cacheService;


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

        try {
            initConfigFromCache();
        }catch (Exception e){
            System.out.println(e);
        }

//        initFiles2Cache();

    }

    public void clearBaseCache(){
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_NAME);
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_PASSWORD);
        redisCacheUtil.deleteObject(Constant.GLOBAL_OTHER_ADMIN);
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
    public  void initConfigFromCache() throws IllegalAccessException {
        Field[] fields = null;
        try {
            fields = CommonConfig.class.getFields();
        }catch (Exception e){
            System.out.println(e);
        }

        if (fields == null){
            return;
        }
        CommonConfig commonConfigEntity = new CommonConfig();
        for (Field field : fields) {
            try {
                initConfigDataByCache(field.getName(), field.get(commonConfigEntity).toString(), commonConfigEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public  void initConfigDataByCache(String key, String value, CommonConfig entity) throws NoSuchFieldException, IllegalAccessException {
        if (!redisCacheUtil.hasKey(Constant.CONFIG + key)){
            redisCacheUtil.setCacheObject(Constant.CONFIG + key, value);
        }else {
            Field f = CommonConfig.class.getDeclaredField(key);
            f.set(entity,redisCacheUtil.getCacheObject(Constant.CONFIG + key).toString());
        }
    }

    /**
     * 配置类反推
     * @param key
     * @param value
     * @param entity
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
//    public static void initConfigData(String key, String value, CommonConfig entity) throws NoSuchFieldException, IllegalAccessException {
//        LambdaQueryWrapper<ConfigDo> lambdaQueryWrapper = Wrappers.<ConfigDo>lambdaQuery().eq(ConfigDo::getConfigName, key);
//        long count = commonConfigServer.count(lambdaQueryWrapper);
//        if (count == 0){
//            ConfigDo configDo = new ConfigDo();
//            configDo.setConfigName(key);
//            configDo.setConfigValue(value);
//            commonConfigServer.save(configDo);
//        }else {
//            Field f = CommonConfig.class.getDeclaredField(key);
//            ConfigDo c = commonConfigServer.getOne(lambdaQueryWrapper);
//            f.set(entity, c.getConfigValue());
//        }
//    }
   public void initFiles2Cache(){
       log.info("正在缓存文件");
      try {
          cacheService.initFile2Cache().stream().forEach(item->{
              log.info(item.getBucket().getBucketId()+" 完成"+item.getCompleteTime() +" "+item.getSpendTime());
          });
      }catch (Exception e){
          System.out.println(e);
      }
   }
}
