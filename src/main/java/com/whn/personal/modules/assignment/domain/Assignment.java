package com.whn.personal.modules.assignment.domain;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class Assignment {

    private String id;
    @NotBlank(message = "事务内容不能为空.")
    private String content;
    private boolean stare;
    private boolean completed;
    private Date createTime;
    private String userId;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStare() {
        return stare;
    }

    public void setStare(boolean stare) {
        this.stare = stare;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

