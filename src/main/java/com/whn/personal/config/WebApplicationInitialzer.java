package com.whn.personal.config;


import com.whn.waf.config.base.AbstractWebApplicationInitialzer;
import com.whn.waf.config.base.ServiceConfigurerAdapter;
import com.whn.waf.config.mybatis.MyBatisConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebApplicationInitialzer extends AbstractWebApplicationInitialzer{

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                WebMvcConfigurerAdpter.class,
                ServiceConfigurerAdapter.class,
                MyBatisConfig.class
        };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }
}
