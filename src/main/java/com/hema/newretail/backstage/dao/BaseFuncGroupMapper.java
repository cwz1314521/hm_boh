package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseFuncGroup;
import com.hema.newretail.backstage.entry.BaseFuncGroupIsSelectEntry;
import com.hema.newretail.backstage.model.authority.BaseFuncGroupMoudleBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseFuncGroupMapper {


    List<BaseFuncGroupIsSelectEntry> isSelectedAll(Long postId);

    List<BaseFuncGroupMoudleBo> selectBaseFuncGroupMoudleBo(@Param("postId") Long postId);

    List<BaseFuncGroup> selectAll();

    int deleteByPrimaryKey(Long id);

    int insert(BaseFuncGroup record);

    int insertSelective(BaseFuncGroup record);

    BaseFuncGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseFuncGroup record);

    int updateByPrimaryKey(BaseFuncGroup record);
}