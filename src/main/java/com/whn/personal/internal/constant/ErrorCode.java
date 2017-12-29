package com.whn.personal.internal.constant;

import com.whn.waf.base.constant.IErrorCode;
import com.whn.waf.base.context.WafProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

/**
 * Created by Administrator on 2017/7/13.
 */
public enum ErrorCode implements IErrorCode {

    // 整个系统
    DATA_NOT_EXIST(HttpStatus.NOT_FOUND, "DATA_NOT_EXIST", "数据不存在，可能已被删除"),
    DATA_CONFLICT(HttpStatus.CONFLICT, "DATA_CONFLICT", "数据已存在"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "服务器内部错误"),

    MISSING_USER_INFO(HttpStatus.UNAUTHORIZED, "MISSING_USER_INFO", "缺失用户信息."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "用户认证过期,请重新登陆."),

    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "LOGIN_ERROR", "用户名或密码错误."),
    ;

    private static final Logger logger = LoggerFactory.getLogger(ErrorCode.class);

    private static ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    static {
        messageSource.setBasename("messages/message");
    }

    private HttpStatus httpStatus;
    private String code;
    private String message;
    private static final String PREFIX = WafProperties.getErrorCodePrefix();

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        setHttpStatus(httpStatus);
        setCode(code);
        setMessage(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return PREFIX + this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
