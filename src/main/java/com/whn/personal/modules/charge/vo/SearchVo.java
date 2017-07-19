package com.whn.personal.modules.charge.vo;

import com.whn.waf.common.support.PageParams;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/19.
 */
public class SearchVo extends PageParams {

    private String yearAndMonth;

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }
}
