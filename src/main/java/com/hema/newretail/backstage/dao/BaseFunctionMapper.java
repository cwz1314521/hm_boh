package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseFunction;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseFunctionMapper {
    int deleteByPrimaryKey(String funcCode);

    int insert(BaseFunction record);

    int insertSelective(BaseFunction record);

    BaseFunction selectByPrimaryKey(String funcCode);

    int updateByPrimaryKeySelective(BaseFunction record);

    int updateByPrimaryKey(BaseFunction record);
}