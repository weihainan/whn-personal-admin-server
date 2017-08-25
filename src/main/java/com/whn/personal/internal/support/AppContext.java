package com.whn.personal.internal.support;

import com.whn.personal.modules.admin.domain.Admin;
import com.whn.waf.common.context.Context;
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
public class AppContext extends Context {

    private static ApplicationContext applicationContext;

    public AppContext() {
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
