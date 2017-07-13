package com.whn.personal.modules.assignment.mapper;


import com.whn.personal.modules.assignment.domain.Assignment;

public interface AssignmentMapper {

    int deleteByPrimaryKey(String id);

    int insert(Assignment record);

    int insertSelective(Assignment record);

    Assignment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Assignment record);

    int updateByPrimaryKey(Assignment record);
}