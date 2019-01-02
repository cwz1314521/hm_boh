package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.common.queryparam.erp.ListManufacturerCondition;
import com.hema.newretail.backstage.entry.BaseIngredientInfoEntry;
import com.hema.newretail.backstage.model.erp.ListManufacturerBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
@Component
public interface BaseIngredientInfoEntryMapper {



    int selectCountByCode(String ingredientCode);

    List<ListManufacturerBo> selectBySelect(ListManufacturerCondition listManufacturerCondition);

    List<BaseIngredientInfoEntry> selectAll();

    int deleteByPrimaryKey(Long id);

    int insert(BaseIngredientInfoEntry record);

    int insertSelective(BaseIngredientInfoEntry record);

    BaseIngredientInfoEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseIngredientInfoEntry record);

    int updateByPrimaryKey(BaseIngredientInfoEntry record);

    List<BaseIngredientInfoEntry> findAll(Map<String,Object> params);

    int countByRefMenuIngredient(long id);

    int countByBasePropertiesType(long id);

    int countByBaseIngredientBox(@Param("id") Long id);

    int verifyIngredientCode(@Param("code") String code);
}