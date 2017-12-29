package com.whn.personal.config;

import com.whn.personal.internal.intercptor.ContextResolveInterceptor;
import com.whn.waf.config.base.AbstractWebMvcConfigurerAdpter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */

@Configuration
@EnableScheduling
public class WebMvcConfigurerAdpter extends AbstractWebMvcConfigurerAdpter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new ContextResolveInterceptor());
    }

}