package com.hema.newretail.backstage.dao;

import com.hema.newretail.backstage.entry.BaseCompanyGeoHashData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public interface BaseCompanyGeoHashMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseCompanyGeoHashData record);

    int insertSelective(BaseCompanyGeoHashData record);

    BaseCompanyGeoHashData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCompanyGeoHashData record);

    int updateByPrimaryKey(BaseCompanyGeoHashData record);

    int countByBaseCompanyId(Long id);

    List<BaseCompanyGeoHashData> findServiceAreaByBaseCompanyId(Long baseCompanyId);

    List<String> freeGeoHash(@Param("mapGeoHash") List<String> parameter);

    List<String> findBaseCompanyGeoHashByCompanyId(Long baseCompanyId);

    int insertBatchBaseCompanyGeoHash(@Param("list") List<BaseCompanyGeoHashData> list);

    int deleteByCompanyId(Long companyId);

    List<BaseCompanyGeoHashData> findGeoHashs(@Param("mapGeoHash") List<String> parameter);

}