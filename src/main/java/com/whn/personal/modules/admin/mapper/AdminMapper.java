package com.whn.personal.modules.admin.mapper;

import com.whn.personal.modules.admin.domain.Admin;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface AdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}
