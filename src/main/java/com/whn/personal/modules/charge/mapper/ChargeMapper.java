package com.whn.personal.modules.charge.mapper;


import com.whn.personal.modules.charge.domain.Charge;

public interface ChargeMapper {

    int deleteByPrimaryKey(String id);

    int insert(Charge record);

    int insertSelective(Charge record);

    Charge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Charge record);

    int updateByPrimaryKey(Charge record);
}