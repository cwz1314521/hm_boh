package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseDiyAddIngredientStatusEntry;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseDiyAddIngredientStatusMapper {


    List<BaseDiyAddIngredientStatusEntry> selectAll();

    int deleteByPrimaryKey(Long id);

    int insert(BaseDiyAddIngredientStatusEntry record);

    int insertSelective(BaseDiyAddIngredientStatusEntry record);

    BaseDiyAddIngredientStatusEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseDiyAddIngredientStatusEntry record);

    int updateByPrimaryKey(BaseDiyAddIngredientStatusEntry record);
}