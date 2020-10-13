package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.async.EventProducer;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.result.ResultStatus;
import com.chenrj.zhihu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginController
 * @Description 登录注册
 * @Author rjchen
 * @Date 2020-05-04 16:14
 * @Version 1.0
 */
@Slf4j
@Controller
public class LoginController {

    private static int COOKIE_DURATION = 3 * 24 * 3600;

    @Autowired
    UserService userService;

    @Resource
    HostHolder currentUser;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String registerAndLogin() {
        return "login";
    }

    @RequestMapping(path = "/register/", method = RequestMethod.POST)
    public ModelAndView register(ModelAndView modelAndView, HttpServletResponse response,
                           @RequestParam("username") String name,
                           @RequestParam("password") String password,
                           @RequestParam(value = "rememberme", defaultValue = "false") boolean remenberMe) {
        ResultStatus status = userService.register(name, password);
        switch (status) {
            case REGISTER_SUCCESS:
                Cookie cookie = new Cookie("ticket", userService.getCookie(name));
                cookie.setPath("/");
                if (remenberMe) {
                    cookie.setMaxAge(COOKIE_DURATION);
                }
                response.addCookie(cookie);
                modelAndView.setViewName("redirect:/index");
                return modelAndView;
            case REGISTER_FAIL:
                modelAndView.addObject("msg", status.getMessage());
                modelAndView.setViewName("redirect:/signin");
                return modelAndView;
        }
        return null;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ModelAndView login(ModelAndView modelAndView,
                              HttpServletResponse response,
                              @RequestParam("username") String name,
                              @RequestParam("password") String password,
                              @RequestParam(value = "rememberme", defaultValue = "false") boolean remenberMe) {
        ResultStatus status = userService.login(name, password);
        switch (status) {
            case LOGIN_SUCCESS:
                Cookie cookie = new Cookie("ticket", userService.getCookie(name));
                cookie.setPath("/");
                if (remenberMe) {
                    cookie.setMaxAge(COOKIE_DURATION);
                }
                response.addCookie(cookie);
                modelAndView.addObject("currentUser", currentUser.getUser());
                modelAndView.setViewName("redirect:/index");

                // 取消登录邮件发送
                /*EventModel eventModel = new EventModel();
                eventModel.setActionId(currentUser.getUser().getId());
                eventModel.setEventType(EventType.LOGIN);
                eventProducer.fireEvent(eventModel);*/
                return modelAndView;
            case LOGIN_FAIL:
                modelAndView.addObject("msg", status.getMessage());
                modelAndView.setViewName("redirect:/signin");
                return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/signin";
    }
}
