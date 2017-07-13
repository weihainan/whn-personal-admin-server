package com.whn.personal.modules.charge.mapper;


import com.whn.personal.modules.charge.domain.ChargeLabel;

public interface ChargeLabelMapper {

    int deleteByPrimaryKey(String id);

    int insert(ChargeLabel record);

    int insertSelective(ChargeLabel record);

    ChargeLabel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChargeLabel record);

    int updateByPrimaryKey(ChargeLabel record);
}