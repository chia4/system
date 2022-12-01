package com.gzu.system.mapper;


import com.gzu.system.pojo.Agency;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgencyMapper {


    int insert (String userName,String agencyName,String agencyAddress,boolean risk);
}
