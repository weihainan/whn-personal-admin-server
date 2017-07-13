package com.whn.personal.internal.support;

import com.whn.personal.modules.admin.domain.Admin;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Component
public class Context implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private Context() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initContextHolder(applicationContext);
    }

    private static void initContextHolder(ApplicationContext context) {
        Context.applicationContext = context;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }


    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    private final static String KEY_USER_INFO = "user_info"; //当前用户信息

    public void setUserInfo(Admin userInfo) {
        getRequest().setAttribute(KEY_USER_INFO, userInfo);
    }

    public Admin getUserInfo() {
        return (Admin) getRequest().getAttribute(KEY_USER_INFO);
    }

    public String getUserId() {
        return getUserInfo() == null ? null : getUserInfo().getId();
    }

    public String getUserName() {
        if (getUserInfo() == null) return null;
        Object realName = getUserInfo().getName();
        return (String) realName;
    }
}
