package com.chengzzz.zcloud.utils;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Yet
 * @date 2022/08/27 23:21
 **/
@Slf4j
public class IpUtil {

    @Resource
    static RedisCacheUtil redisCacheUtil;

    public static List<String> appendWhiteIp(BucketDTO bucket, FileRequestDTO fileRequest){
        List<String> whiteIpList = bucket.getWhiteIpList();
        whiteIpList.add(fileRequest.getUserIp());
        bucket.setWhiteIpList(whiteIpList);
        redisCacheUtil.setCacheObject(Constant.BUCKET+bucket.getBucketId(), bucket);
        return whiteIpList;
    }

    public static boolean isWhiteIp(BucketDTO bucket, FileRequestDTO fileRequest){
        return bucket.getWhiteIpList().contains(fileRequest.getUserIp().trim());
    }
    public static boolean isWhiteIp(String ip, List<String> whiteIpList){
        return whiteIpList.contains(ip.trim());
    }

    public static boolean removeWhiteIp(BucketDTO bucket, FileRequestDTO fileRequest){
        List<String> whiteIpList =  bucket.getWhiteIpList();
        whiteIpList.remove(fileRequest.getUserIp());
        bucket.setWhiteIpList(whiteIpList);
        redisCacheUtil.setCacheObject(Constant.BUCKET+bucket.getBucketId(), bucket);
        return true;
    }
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("未知主机", e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
