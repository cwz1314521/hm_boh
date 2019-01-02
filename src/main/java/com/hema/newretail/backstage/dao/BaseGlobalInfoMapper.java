package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseGlobalInfoEntry;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseGlobalInfoMapper {


    BaseGlobalInfoEntry selectByKey(String key);

    int deleteByPrimaryKey(Long id);

    int insert(BaseGlobalInfoEntry record);

    int insertSelective(BaseGlobalInfoEntry record);

    BaseGlobalInfoEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseGlobalInfoEntry record);

    int updateByPrimaryKey(BaseGlobalInfoEntry record);
}