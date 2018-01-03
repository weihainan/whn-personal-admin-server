package com.whn.personal.internal.support;

import com.whn.personal.modules.admin.domain.Admin;
import com.whn.waf.base.context.Context;
import org.springframework.stereotype.Component;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Component
public class AppContext extends Context {

    public AppContext() {
    }

    private final static String USER_INFO_KEY = "CURRENT_USER_INFO"; //当前用户信息

    public void setUserInfo(Admin userInfo) {
        getRequest().setAttribute(USER_INFO_KEY, userInfo);
    }

    public Admin getUserInfo() {
        return (Admin) getRequest().getAttribute(USER_INFO_KEY);
    }

    public String getUserId() {
        return getUserInfo() == null ? null : getUserInfo().getId();
    }

    public String getUserName() {
        return getUserInfo() == null ? null : getUserInfo().getName();
    }
}
