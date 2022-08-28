package com.chengzzz.zcloud;

import com.chengzzz.zcloud.config.GlobalConfig;
import com.chengzzz.zcloud.config.ZcloudInitConfig;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.FileHandlerResult;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.service.cacheservice.impl.CacheServiceImpl;
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

//        initFiles2Cache();

    }

    public void clearBaseCache(){
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_NAME);
        redisCacheUtil.deleteObject(Constant.DEFAULT_USER_PASSWORD);
        redisCacheUtil.deleteObject(Constant.GLOBAL_OTHER_ADMIN);
    }


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
