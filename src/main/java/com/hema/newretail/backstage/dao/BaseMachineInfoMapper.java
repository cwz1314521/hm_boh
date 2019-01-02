package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.common.queryparam.common.CompanyNameCondition;
import com.hema.newretail.backstage.entry.BaseMachineInfoEntry;
import com.hema.newretail.backstage.model.common.GridCompanyBo;

import java.util.List;

/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseMachineInfoMapper {

    String selectByUUId(String uuid);

    BaseMachineInfoEntry selectByUId(String machineUuid);

    int deleteByPrimaryKey(Long id);

    int insert(BaseMachineInfoEntry record);

    int insertSelective(BaseMachineInfoEntry record);

    BaseMachineInfoEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseMachineInfoEntry record);

    int updateByPrimaryKeyWithBLOBs(BaseMachineInfoEntry record);

    int updateByPrimaryKey(BaseMachineInfoEntry record);


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