package com.chengzzz.zcloud.controller;

import com.chengzzz.zcloud.exception.pathErrorException;
import com.chengzzz.zcloud.utils.filesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 文件控制器
 *
 * @author Yet
 * @date 2022/08/22 22:22
 **/
@RestController
public class filesController {


    @GetMapping("/getFiles")
    public ModelAndView getFiles() throws pathErrorException {

        HashMap map = new HashMap();
        map.put("files", filesUtil.getDirFromCurrentPath("D:\\videos"));
        ModelAndView modelAndView = new ModelAndView("hello", map);
//        model.addAttribute("files",filesUtil.getDirFromCurrentPath("D:\\videos"));
        return modelAndView;
    }
}
