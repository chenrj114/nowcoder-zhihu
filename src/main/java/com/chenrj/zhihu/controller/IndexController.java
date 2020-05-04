package com.chenrj.zhihu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @ClassName IndexController
 * @Description 首页请求访问控制
 * @Author rjchen
 * @Date 2020-05-04 16:17
 * @Version 1.0
 */

@Controller
public class IndexController {

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
