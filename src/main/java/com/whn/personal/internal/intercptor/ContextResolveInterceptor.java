package com.whn.personal.internal.intercptor;

import com.whn.waf.common.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class ContextResolveInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ContextResolveInterceptor.class);

    private Context context = Context.getBean(Context.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
//            return true;
//        }
//        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
//        context.setUserInfo(userInfo);
        return super.preHandle(request, response, handler);
    }
}
