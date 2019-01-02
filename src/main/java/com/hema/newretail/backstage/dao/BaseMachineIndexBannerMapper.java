package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseMachineIndexBannerEntry;
import com.hema.newretail.backstage.model.index.banner.BannerListBo;

import java.util.List;

public interface BaseMachineIndexBannerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseMachineIndexBannerEntry record);

    int insertSelective(BaseMachineIndexBannerEntry record);

    BaseMachineIndexBannerEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseMachineIndexBannerEntry record);

    int updateByPrimaryKey(BaseMachineIndexBannerEntry record);

    List<BannerListBo> selectListMap();

    int delete(Long id);
}