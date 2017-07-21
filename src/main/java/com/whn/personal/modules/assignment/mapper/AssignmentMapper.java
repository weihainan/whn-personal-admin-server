package com.whn.personal.modules.assignment.mapper;


import com.github.pagehelper.Page;
import com.whn.personal.modules.assignment.domain.Assignment;

import java.util.Map;

public interface AssignmentMapper {

    int deleteByPrimaryKey(String id);

    int insert(Assignment record);

    int insertSelective(Assignment record);

    Assignment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Assignment record);

    int updateByPrimaryKey(Assignment record);

    Page<Assignment> select(Map<String, Object> map);
}