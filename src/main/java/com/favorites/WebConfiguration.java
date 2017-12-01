package com.favorites;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.favorites.comm.filter.SecurityFilter;

@Configuration
public class WebConfiguration  {
	
    @Bean
    public FilterRegistrationBean filterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/*");//添加过滤规则
        registration.addInitParameter("paramName", "paramValue");//添加不需要忽略的格式信息
        registration.setName("MyFilter");
        registration.setOrder(1);//表示执行过滤顺序，值越小，越先执行
        return registration;
    }
 
}



