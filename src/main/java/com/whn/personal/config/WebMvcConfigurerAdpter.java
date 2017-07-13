package com.whn.personal.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.whn.waf.common.config.AbstractWebMvcConfigurerAdpter;
import com.whn.waf.common.support.WafJsonMapper;
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
//@PropertySource(value = {})
public class WebMvcConfigurerAdpter extends AbstractWebMvcConfigurerAdpter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        WafJsonMapper.getMapper().enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

}