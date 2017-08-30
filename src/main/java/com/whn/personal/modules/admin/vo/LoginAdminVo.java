package com.whn.personal.modules.admin.vo;

import org.hibernate.validator.constraints.Length;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/14.
 */
public class LoginAdminVo {

    @Length(min = 6, max = 6, message = "管理员编号为6位.")
    private String id;

    @Length(min = 6, max = 20, message = "管理员密码为6~20位.")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
