package com.whn.personal.modules.charge.domain;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class ChargeLabel {

    private String id;
    private String name;
    private String mark;
    private Date createTime;

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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
