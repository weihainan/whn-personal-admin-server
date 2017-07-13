package com.whn.personal.modules.charge.domain;

import com.whn.personal.modules.charge.enums.ChargeType;

import java.math.BigDecimal;
import java.util.Date;

import static com.whn.personal.modules.charge.enums.ChargeType.disbursements;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
public class Charge {

    private String id;
    private BigDecimal amount;
    private String label;
    private String mark;
    private ChargeType type = disbursements;
    private String userId;
    private Date createTime;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ChargeType getType() {
        return type;
    }

    public void setType(ChargeType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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
}

