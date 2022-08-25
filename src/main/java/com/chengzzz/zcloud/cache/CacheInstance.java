package com.chengzzz.zcloud.cache;

import com.chengzzz.zcloud.entity.FileEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存
 *
 * @author Yet
 * @date 2022/08/25 19:38
 **/
public class CacheInstance {


    //创建guava cache
    Cache<String, Object> loadingCache = CacheBuilder.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(17, TimeUnit.SECONDS)
            .build();
    //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
    //.expireAfterAccess(17, TimeUnit.SECONDS)


    //懒汉式单例
    public Cache getInstance(){
        if (loadingCache != null){
            return loadingCache;
        }
        return CacheBuilder.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(17, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 移除bucket
     * @param bucketId
     * @return
     */
    public boolean removeBucket(String bucketId){
        loadingCache.invalidate(bucketId);
        return true;
    }

    /**
     * 添加bucket
     * @param bucketId
     * @param val
     * @return
     */
    public boolean putBucket(String bucketId, List<FileEntity> val){
        loadingCache.put(bucketId, val);
        return true;
    }


    /**
     * 更新bucket
     * @param bucketId
     * @param val
     * @return
     */
    public boolean updateBucket(String bucketId, List<FileEntity> val){
        this.putBucket(bucketId, val);
        return true;
    }






}
