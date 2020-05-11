package com.chenrj.zhihu.config;

import com.chenrj.zhihu.interceptor.CookiesInterceptor;
import com.chenrj.zhihu.interceptor.LoginRequireInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description
 * @Author rjchen
 * @Date 2020-05-05 14:38
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    CookiesInterceptor cookiesInterceptor;

    @Autowired
    LoginRequireInterceptor loginRequireInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cookiesInterceptor);
        registry.addInterceptor(loginRequireInterceptor).addPathPatterns("/*/add");
    }
}
