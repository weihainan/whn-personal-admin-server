package com.whn.personal.modules.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class Admin {

    @Length(min = 6, max = 6, message = "管理员编号为6位.")
    private String id;

    @Length(min = 3, max = 20, message = "管理员名称为6~20位.")
    private String name;

    @JsonIgnore // 登陆后返回的实体不包含密码
    @Length(min = 6, max = 20, message = "管理员密码为6~20位.")
    private String password;
    private Date createTime;
    private Date lastLoginTime;
    private String avatar;

    private String token;     // 每次登陆都设置新的token
    // token过期时间 每次登陆时当前时间 + 7天   后台定时任务 每天凌晨检查是否过期 设置新值 当2个值不一样的时候 重新登陆
    // 请求头包含 Auth-Token:userId-Token 验证token
    private long expireTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
