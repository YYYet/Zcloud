package com.chengzzz.zcloud.aspect;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;
import com.chengzzz.zcloud.responce.RestResponce;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yet
 * @date 2022/08/28 13:11
 **/
@Aspect
@Component
@Slf4j
public class FileAspect {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    /**
     * 切面控制文件读写自动缓存
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(public * com.chengzzz.zcloud.service.fileservice.IfileService.getAllFiles(..))")
    public Object handler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info(String.valueOf(proceedingJoinPoint));
        List<FileEntityItem> result = new ArrayList<>();
        // 获取请求路径
        Object[] args = proceedingJoinPoint.getArgs();
        log.info("args {}",Arrays.toString(args));
        BucketDTO bucket = (BucketDTO)args[0];
        log.info("切面bucket ", bucket);

        if (true && redisCacheUtil.hasKey(Constant.FILES + bucket.getBucketId())){
            log.info("桶缓存读取");
//            List<FileEntityItem> finalResult = result;
//            redisCacheUtil.getCacheMap(Constant.FILES + bucket.getBucketId()).forEach((key, value) ->{
//                System.out.println("key:"+key+"----"+"value:"+value);
//                finalResult.add((FileEntityItem)value);
//            });

//            result = redisCacheUtil.getCacheList(Constant.FILES + bucket.getBucketId());
//            log.info("缓存读取");
//            result = finalResult;
            result = redisCacheUtil.getCacheMap(Constant.FILES + bucket.getBucketId()).values()
                    .stream()
                    .map(item -> (FileEntityItem)item)
                    .collect(Collectors.toList());
            log.info("缓存读取完成");
        }else {
            log.info("本地读取");
            result = (List<FileEntityItem>)proceedingJoinPoint.proceed();
            //重写缓存
            log.info("重写缓存");
            Map<String, FileEntityItem> collect = result.stream()
                    .collect(Collectors.toMap(FileEntityItem::getPath, item -> item));
            redisCacheUtil.deleteObject(Constant.FILES + bucket.getBucketId());
            redisCacheUtil.setCacheMap(Constant.FILES + bucket.getBucketId(), collect);
//            redisCacheUtil.deleteObject(Constant.FILES + bucket.getBucketId());
//            redisCacheUtil.setCacheList(Constant.FILES + bucket.getBucketId(), result);
            log.info("重写成功");
        }
        return result;
    }




    @Around(value = "execution(public * com.chengzzz.zcloud.service.core.AbstractBaseFileServerCore.getFileList(..))")
    public Object filePathHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info(String.valueOf(proceedingJoinPoint));
        List<FileEntityItem> result;
        // 获取请求路径
        Object[] args = proceedingJoinPoint.getArgs();
        log.info("args {}",Arrays.toString(args));
        String path = args[0].toString();
        log.info("切面path ", path);

        if (true && redisCacheUtil.hasKey(Constant.PATH_FILES + path)){
            log.info("文件缓存读取");
            result = redisCacheUtil.getCacheMap(Constant.PATH_FILES + path).values()
                    .stream()
                    .map(item -> (FileEntityItem)item)
                    .collect(Collectors.toList());
        }else {
            log.info("本地读取");
            result = (List<FileEntityItem>)proceedingJoinPoint.proceed();
            //重写缓存
            Map<String, FileEntityItem> collect = result.stream()
                    .collect(Collectors.toMap(FileEntityItem::getPath, item -> item));
            redisCacheUtil.deleteObject(Constant.PATH_FILES + path);
            redisCacheUtil.setCacheMap(Constant.PATH_FILES + path, collect);
            log.info("重写缓存");
        }
        return result;
    }
}
