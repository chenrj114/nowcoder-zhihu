package com.chenrj.zhihu.interceptor;

import com.chenrj.zhihu.dao.LoginTicketDao;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.model.User;
import com.chenrj.zhihu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CookiesInterceptor
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 13:13
 * @Version 1.0
 */
@Slf4j
@Component
public class CookiesInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDao ticketDao;

    @Autowired
    UserService userService;

    @Resource
    HostHolder currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("invoke CookiesInterceptor.preHandle()");
        String ticket = null;
        // 查找当前请求的用户是否有Cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                }
            }

            if (!StringUtils.isEmpty(ticket)) {
                User user = userService.getUserByTicket(ticket);
                if (user != null) {log.info("Cookies is valid");
                    currentUser.setUsers(user);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("invoke CookiesInterceptor.postHandle()");
        if (modelAndView != null && currentUser.getUser() != null) {log.info("currentUser is assigned through intercept");
            modelAndView.addObject("currentUser", currentUser.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("invoke CookiesInterceptor.afterCompletion()");
        currentUser.clear();
    }
}
