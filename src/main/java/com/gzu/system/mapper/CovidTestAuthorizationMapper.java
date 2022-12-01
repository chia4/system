package com.gzu.system.mapper;

import com.gzu.system.pojo.CovidTestAuthorization;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface CovidTestAuthorizationMapper {
   // 1.将大众用户名、机构用户名和当前时间戳(秒为单位)插入covid_test_authorization表

    int insert(String peopleUsername, String agencyUsername,int authorizationTime);

   // 2.在covid_test_authorization表里查询agencyUsername的所有授权记录

    ArrayList<CovidTestAuthorization> selectAuthorizationsByAgencyUsername(String agencyUsername);

}
