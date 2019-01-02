package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.common.queryparam.agent.AgentListCondition;
import com.hema.newretail.backstage.common.queryparam.common.CompanyNameCondition;
import com.hema.newretail.backstage.entry.agent.AgentUserEntry;
import com.hema.newretail.backstage.model.agent.AgentListBo;
import com.hema.newretail.backstage.model.common.AgentCompanyBo;
import com.hema.newretail.backstage.model.common.GridCompanyBo;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface AgentUserMapper {


    int selectCountByName(String companyName);

    /**逻辑删除*/
    int deleteById(Long id);

    /**代理列表查询*/
    List<AgentListBo> selectAgentList(AgentListCondition agentListCondition);

    /**查询全部信息*/
    List<AgentUserEntry> selectAll();


    int deleteByPrimaryKey(Long id);

    int insert(AgentUserEntry record);

    int insertSelective(AgentUserEntry record);

    AgentUserEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AgentUserEntry record);

    int updateByPrimaryKey(AgentUserEntry record);


    /**
     *
     * 功能描述: 实时检索网格公司公共接口
     *
     * @param  condition
     * @return  List<GridCompanyBo>
     * @author  cwz
     * @date  2018/12/11 10:31
     */
    List<AgentCompanyBo> selectCommon(CompanyNameCondition condition);
}