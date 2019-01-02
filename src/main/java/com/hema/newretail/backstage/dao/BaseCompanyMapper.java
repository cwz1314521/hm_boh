package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.common.queryparam.BaseCompanyNameParameter;
import com.hema.newretail.backstage.common.queryparam.BaseCompanyNameQueryParameter;
import com.hema.newretail.backstage.common.queryparam.BaseCompanyQueryParameter;
import com.hema.newretail.backstage.common.queryparam.common.CompanyNameCondition;
import com.hema.newretail.backstage.common.queryparam.erp.ListManufacturerCondition;
import com.hema.newretail.backstage.entry.BaseCompanyData;
import com.hema.newretail.backstage.model.common.GridCompanyBo;
import com.hema.newretail.backstage.model.erp.ListManufacturerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseCompanyMapper {


    List<ListManufacturerBo> selectBySelect(ListManufacturerCondition listManufacturerCondition);

    int deleteByPrimaryKey(Long id);

    int insert(BaseCompanyData record);

    int insertSelective(BaseCompanyData record);

    BaseCompanyData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCompanyData record);

    int updateByPrimaryKey(BaseCompanyData record);

    List<BaseCompanyData> selectAll();

    int updateStatusById(BaseCompanyData data);

    List<BaseCompanyData> findAll(BaseCompanyQueryParameter parameter);

    List<BaseCompanyData> findBaseCompanyByArea(BaseCompanyNameQueryParameter parameter);

    List<BaseCompanyData> findCompanysByIds(@Param("ids") List<Long> ids);

    int countByCompanyName(BaseCompanyNameParameter parameter);



    /**
     *
     * 功能描述: 实时检索网格公司公共接口
     *
     * @param  condition
     * @return  List<GridCompanyBo>
     * @author  cwz
     * @date  2018/12/11 10:31
     */
    List<GridCompanyBo> selectCommon(CompanyNameCondition condition);
}