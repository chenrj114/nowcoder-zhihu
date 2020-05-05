package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.model.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * @ClassName IndexController
 * @Description 首页请求访问控制
 * @Author rjchen
 * @Date 2020-05-04 16:17
 * @Version 1.0
 */

@Controller
public class IndexController {

    @Resource
    HostHolder currnetUser;

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
