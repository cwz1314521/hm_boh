package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.agent.AgentCooperationMode;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface AgentCooperationModeMapper {


    /**查询代理公司有几条分成记录*/
    List<AgentCooperationMode> selectByAgentUserId(Long agentUserId);

    int deleteByPrimaryKey(Long id);

    int insert(AgentCooperationMode record);

    int insertSelective(AgentCooperationMode record);

    AgentCooperationMode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AgentCooperationMode record);

    int updateByPrimaryKey(AgentCooperationMode record);
}