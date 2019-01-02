package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseDiyIngredientEventEntry;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseDiyIngredientEventMapper {

    int deleteBySettingId(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(BaseDiyIngredientEventEntry record);

    int insertSelective(BaseDiyIngredientEventEntry record);

    BaseDiyIngredientEventEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseDiyIngredientEventEntry record);

    int updateByPrimaryKey(BaseDiyIngredientEventEntry record);
}