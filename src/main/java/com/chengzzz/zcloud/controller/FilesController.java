package com.chengzzz.zcloud.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.chengzzz.zcloud.exception.PathErrorException;
import com.chengzzz.zcloud.handler.NonStaticResourceHttpRequestHandler;
import com.chengzzz.zcloud.responce.RestResponce;
import com.chengzzz.zcloud.service.fileservice.impl.FileServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * 文件控制器
 *
 * @author Yet
 * @date 2022/08/22 22:22
 **/
@RestController
public class FilesController {

    /**
     * TODO
     * 获取文件树 需做切面 优先入缓存查询
     * 文件操作 需做切面 更新缓存
     * 需做定时任务刷新缓存
     */

    @Resource
    NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @Resource
    FileServiceImpl fileService;

    /**
     * 获取路径下第一层文件及文件夹
     * @param path
     * @return
     * @throws PathErrorException
     */
    @GetMapping("/getFiles")
    public RestResponce getFiles(@RequestParam String path) throws PathErrorException {
        return RestResponce.sucess(fileService.getDirFromCurrentPath(path));
    }


    /**
     * 创建文件/文件夹
     * @param path
     * @return
     * @throws PathErrorException
     */
    @GetMapping("/mkdir")
    public RestResponce mkDir(@RequestParam String path) throws PathErrorException {
        return RestResponce.sucess(fileService.getDirFromCurrentPath(path));
    }

    /**
     * 删除文件/文件夹
     * @param path
     * @return
     * @throws PathErrorException
     */
    @GetMapping("/delete")
    public RestResponce delete(@RequestParam String path) {
        return RestResponce.sucess( fileService.delDir(path));
    }

    /**
     * 模糊匹配文件
     * @param name
     * @param path
     * @return
     * @throws PathErrorException
     */
    @GetMapping("/searchFiles")
    public RestResponce searchFiles(@RequestParam String name, @RequestParam String path) {
        return RestResponce.sucess(fileService.searchFiles(name, path));
    }

    /**
     * 模糊匹配文件夹
     * @param name
     * @param path
     * @return
     * @throws PathErrorException
     */
    @GetMapping("/searchDirs")
    public RestResponce searchDirs(@RequestParam String name, @RequestParam String path) {
        return RestResponce.sucess(fileService.searchDirs(name, path));
    }

    @GetMapping("/fp/play")
    public void play(HttpServletRequest request, @RequestParam("path") String path, HttpServletResponse response) throws IOException{
        response.reset();
        File file = FileUtil.file(path);
        long fileLength = file.length();
        // 随机读文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        long range=0;
        if (StrUtil.isNotBlank(rangeString)) {
            range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        }
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型
        response.setHeader("Content-Type", "video/mp4");
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 移动访问指针到指定位置
        randomAccessFile.seek(range);
        // 每次请求只返回1MB的视频流
        byte[] bytes = new byte[1024 * 1024 / 4];
        int len = randomAccessFile.read(bytes);
        //设置此次相应返回的数据长度
        response.setContentLength(len);
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes "+range+"-"+(fileLength-1)+"/"+fileLength);
        // 将这1MB的视频流响应给客户端
        outputStream.write(bytes, 0, len);
        outputStream.close();
        randomAccessFile.close();

        System.out.println("返回数据区间:【"+range+"-"+(range+len)+"】");
    }


    @RequestMapping(value = "/videoPlayer", method = RequestMethod.GET)
    public void player(HttpServletRequest request, @RequestParam("path") String path, HttpServletResponse response) {

        BufferedInputStream bis = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0;
                long fileLength;
                String rangBytes = "";
                fileLength = file.length();
                InputStream ins = new FileInputStream(file);
                bis = new BufferedInputStream(ins);
                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");
                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) { // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }
                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", new Long(contentLength).toString());
                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-").append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/").append(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }
                String fileName = file.getName();
                //response.setContentType("application/octet-stream");
                response.setContentType("video/mp4");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                OutputStream out = response.getOutputStream();
                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
            }

        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @GetMapping(value = "/mp4/play")
    public void aloneVideoPlay2(HttpServletRequest request, @RequestParam("path") String path, HttpServletResponse response) {
        try {
            File file = FileUtil.file(path);
            if (file.exists()){
                String mimeType = Files.probeContentType(file.toPath());
                System.out.println("mimeType "+mimeType);
                System.out.println("mimeType hutool "+FileUtil.getMimeType(path));
                if (!StringUtils.isEmpty(mimeType)) {
                    response.setContentType("video/mp4");
                }
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, path);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            }else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }


}
