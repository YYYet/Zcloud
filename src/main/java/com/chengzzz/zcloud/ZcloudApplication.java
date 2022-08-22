package com.chengzzz.zcloud;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.fileEntity;
import com.chengzzz.zcloud.entity.filesTree;
import com.chengzzz.zcloud.exception.pathErrorException;
import com.chengzzz.zcloud.utils.filesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class ZcloudApplication {

    public static void main(String[] args) {

        try {
            ArrayList<fileEntity> dirFromPath = filesUtil.getDirFromCurrentPath("D:\\videos");
            dirFromPath.forEach(item-> System.out.println(item.toString()+"  "+item.getName() ));

        }catch (pathErrorException e){
            System.out.println(e);
        }


        SpringApplication.run(ZcloudApplication.class, args);
    }

}
