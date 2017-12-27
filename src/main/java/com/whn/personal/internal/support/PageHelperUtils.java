package com.whn.personal.internal.support;

import com.github.pagehelper.PageHelper;

/**
 * mybatis分页帮助
 *
 * @author weihainan
 * @since 0.1 created on 2017/9/11
 */
public class PageHelperUtils {

    /**
     * 按页码分页
     *
     * @param page 页数 大于等于1 否则默认1
     * @param size 每页个数 大于0 否则默认10
     */
    public static void startPage(long page, long size) {
        if (page < 1) {
            page = 1;
        }
        if (size < 0) {
            size = 10;
        }
        PageHelper.startPage((int) page, (int) size);
    }

    /**
     * 按照偏移量分页
     *
     * @param offset 偏移量 大于等于0 否则默认0
     * @param limit  每页个数 大于0 否则默认10
     */
    public static void offsetPage(long offset, long limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        PageHelper.offsetPage((int) offset, (int) limit);
    }
}
