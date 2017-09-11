package com.whn.personal.internal.support;

import com.github.pagehelper.PageHelper;

/**
 * mybatis分页帮助
 *
 * @author weihainan
 * @since 0.1 created on 2017/9/11
 */
public class PageHelperUtils {

    public static void paging(long page, long size) {
        if (page < 1) {
            page = 1;
        }
        if (size < 0) {
            size = 10;
        }
        PageHelper.startPage((int) page, (int) size);
    }

    public static void pagination(long offset, long limit) {
        if (offset < 0) {
            offset = 1;
        }
        if (limit < 0) {
            limit = 10;
        }
        long page = offset / limit + 1;
        long size = limit;
        PageHelper.startPage((int) page, (int) size);
    }

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
