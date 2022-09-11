package com.chengzzz.zcloud.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.entity.FileEntityItem;
import com.chengzzz.zcloud.service.cacheservice.impl.CacheServiceImpl;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author Yet
 * @date 2022/09/06 22:27
 **/
@RestController
@RequestMapping("zcloud/download")
@Slf4j
public class DownLoadController {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Resource
    private CacheServiceImpl cacheService;

    @GetMapping
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String filePath)
            throws IOException {

        Assert.isTrue(StringUtils.isNotEmpty(filePath), "路径为空");

        if (redisCacheUtil.hasKey(Constant.FILE_URL+filePath)){
            log.info("分享链接下载");
            FileEntityItem fileEntityItem = redisCacheUtil.getCacheObject(Constant.FILE_URL+filePath);
            filePath = fileEntityItem.getPath();
            fileEntityItem.setDownLoadCount(fileEntityItem.getDownLoadCount()+1);
            fileEntityItem.setUrl(filePath);
            cacheService.saveOrUpdateFileByFile(fileEntityItem);
        }

        log.info("进入下载方法 路径{}", filePath);
        FileSystemResource file = new FileSystemResource(filePath);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(file.getFilename().getBytes("UTF-8"),"ISO8859-1")));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
