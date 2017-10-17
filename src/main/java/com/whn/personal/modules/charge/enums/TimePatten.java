package com.whn.personal.modules.charge.enums;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/20.
 */
public enum TimePatten {
    /**
     * Mysql 选年份
     */
    year(4),

    /**
     * Mysql 选年月
     */
    yearmonth(7);

    private int value;

    TimePatten(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
