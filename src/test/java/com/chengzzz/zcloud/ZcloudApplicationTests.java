package com.chengzzz.zcloud;

import com.chengzzz.zcloud.dto.FileConfigDTO;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ZcloudApplicationTests {
    @Resource
    RedisCacheUtil redisCacheUtil;
    @Test
    void contextLoads() {
        FileConfigDTO fileConfigDTO = new FileConfigDTO();
        List<String> password = new ArrayList<>();
        password.add("12345");
        password.add("123456");

        List<String> ip = new ArrayList<>();
        ip.add("10.3.0.191");
        ip.add("10.3.0.192");

        fileConfigDTO.setWhiteIpList(ip);
        fileConfigDTO.setPassword(password);


        redisCacheUtil.setCacheObject("fileConfig:C:Users\\\\Yet\\\\Nutstore\\\\1\\\\23种设计模式\\\\mongoDb.md", fileConfigDTO);
    }

}
