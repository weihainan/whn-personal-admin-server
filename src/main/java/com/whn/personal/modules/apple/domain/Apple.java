package com.whn.personal.modules.apple.domain;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/8/1.
 */
public class Apple {

    private Integer id;
    private Integer weight;
    private Date createTime;
    private String userId;
    private boolean isEaten;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsEaten() {
        return isEaten;
    }

    public void setIsEaten(boolean eaten) {
        isEaten = eaten;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}