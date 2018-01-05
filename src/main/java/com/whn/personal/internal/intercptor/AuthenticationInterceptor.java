package com.whn.personal.internal.intercptor;

import com.whn.personal.internal.constant.GuestApi;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.service.AdminService;
import com.whn.waf.base.exception.WafBizException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.whn.personal.internal.constant.ErrorCode.MISSING_USER_INFO;
import static com.whn.personal.internal.constant.ErrorCode.TOKEN_EXPIRED;
import static com.whn.personal.internal.constant.ErrorCode.WRONG_USER_INFO;

/**
 * 验证用户授权信息
 *
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private AppContext context = AppContext.getBean(AppContext.class);

    private AdminService adminService = AppContext.getBean(AdminService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            return super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 接口不需要验证用户信息
        if (handlerMethod.getBeanType().getAnnotation(GuestApi.class) != null
                || handlerMethod.getMethodAnnotation(GuestApi.class) != null) {
            logger.warn("ignore authentication for {}.{}",
                    handlerMethod.getBeanType().getName(), handlerMethod.getMethod().getName());
            return super.preHandle(request, response, handler);
        }

        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            // 没有用户信息
            logger.error("authenticate error {}", MISSING_USER_INFO.getCode());
            throw WafBizException.of(MISSING_USER_INFO);
        }

        if (!token.contains("-")) {
            // 用户信息格式错误
            logger.error("authenticate error {}, token: {}", WRONG_USER_INFO.getCode(), token);
            throw WafBizException.of(WRONG_USER_INFO);
        }

        String[] tokens = token.split("\\-", 2);
        Admin admin = adminService.getById(tokens[0]);
        if (admin == null || !admin.getToken().equals(tokens[1])
                || DateTime.now().getMillis() >= admin.getExpireTime()) {
            // token过期
            logger.error("authenticate error {},  token: {}", TOKEN_EXPIRED.getCode(), token);
            throw WafBizException.of(TOKEN_EXPIRED);
        }

        context.setUserInfo(admin);
        logger.info("{}:{} authenticate successfully", admin.getId(), admin.getName());
        return super.preHandle(request, response, handler);
    }
}
