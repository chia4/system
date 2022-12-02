package com.gzu.system.mapper;

import com.gzu.system.pojo.CovidTestResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
@Mapper
public interface CovidTestResultMapper {
    //3.在covid_test_result表里查询peopleUsername的所有核酸检测记录

    ArrayList<CovidTestResult> selectResultsByPeopleUsername(String peopleUsername);

    int insert(@Param("peopleUsername") String peopleUsername,@Param("agencyUsername")  String agencyUsername,
               @Param("authorizationTime") int authorizationTime, @Param("result") CovidTestResult.type result);

}
