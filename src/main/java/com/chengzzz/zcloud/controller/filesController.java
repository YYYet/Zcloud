package com.chengzzz.zcloud.controller;

import com.chengzzz.zcloud.exception.pathErrorException;
import com.chengzzz.zcloud.responce.BaseResponce;
import com.chengzzz.zcloud.utils.filesUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * 文件控制器
 *
 * @author Yet
 * @date 2022/08/22 22:22
 **/
@RestController
public class filesController {


    /**
     * 获取路径下第一层文件及文件夹
     * @param path
     * @return
     * @throws pathErrorException
     */
    @GetMapping("/getFiles")
    public BaseResponce getFiles(@RequestParam String path) throws pathErrorException {
        return new BaseResponce(2000,"成功", filesUtil.getDirFromCurrentPath(path));
    }


    /**
     * 创建文件/文件夹
     * @param path
     * @return
     * @throws pathErrorException
     */
    @GetMapping("/mkdir")
    public BaseResponce mkDir(@RequestParam String path) throws pathErrorException {
        return new BaseResponce(2000,"成功", filesUtil.getDirFromCurrentPath(path));
    }

    /**
     * 删除文件/文件夹
     * @param path
     * @return
     * @throws pathErrorException
     */
    @GetMapping("/delete")
    public BaseResponce delete(@RequestParam String path) {
        return new BaseResponce(2000,"成功", filesUtil.delDir(path));
    }

    /**
     * 模糊匹配文件
     * @param name
     * @param path
     * @return
     * @throws pathErrorException
     */
    @GetMapping("/searchFiles")
    public BaseResponce searchFiles(@RequestParam String name, @RequestParam String path) {
        return new BaseResponce(2000,"成功", filesUtil.searchFiles(name, path));
    }

    /**
     * 模糊匹配文件夹
     * @param name
     * @param path
     * @return
     * @throws pathErrorException
     */
    @GetMapping("/searchDirs")
    public BaseResponce searchDirs(@RequestParam String name, @RequestParam String path) {
        return new BaseResponce(2000,"成功", filesUtil.searchDirs(name, path));
    }
}
