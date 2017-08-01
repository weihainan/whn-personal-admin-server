package com.whn.personal.modules.apple.mapper;

import com.whn.personal.modules.apple.domain.Apple;

import java.util.List;

public interface ApplesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Apple record);

    List<Apple> selectList(String userId);

    Apple selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Apple record);
}