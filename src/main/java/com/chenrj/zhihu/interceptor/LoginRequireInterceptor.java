package com.chenrj.zhihu.interceptor;

import com.chenrj.zhihu.model.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginRequireInterceptor
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 22:57
 * @Version 1.0
 */
@Slf4j
@Component
public class LoginRequireInterceptor implements HandlerInterceptor {

    @Resource
    HostHolder currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("invoke LoginRequireInterceptor.preHandle()");
        if (currentUser.getUser() == null) {log.info("login is invalid, you must sign up before");
            response.sendRedirect("/signin");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("invoke LoginRequireInterceptor.postHandle()");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("invoke LoginRequireInterceptor.afterCompletion()");
    }
}
