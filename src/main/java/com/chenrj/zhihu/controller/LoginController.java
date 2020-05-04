package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

/**
 * @ClassName LoginController
 * @Description 登录注册请求访问控制
 * @Author rjchen
 * @Date 2020-05-04 16:14
 * @Version 1.0
 */

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/registerAndLoginPage")
    public String registerAndLogin(@RequestParam("name") String name, @RequestParam("password") String password) {
        return "login";
    }

    @RequestMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("password") String password) {
        userService.register(name, password);
        return "index";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password) {
        String ticket = userService.login(name, password);
        if (!StringUtils.isEmpty(ticket)) {
            return "index";
        } else {
            return null;
        }
    }
}
