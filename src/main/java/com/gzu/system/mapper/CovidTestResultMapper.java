package com.gzu.system.mapper;

import com.gzu.system.pojo.CovidTestResult;
import com.gzu.system.pojo.SimpleResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
@Mapper
public interface CovidTestResultMapper {
    //3.在covid_test_result表里查询peopleUsername的所有核酸检测记录

    ArrayList<CovidTestResult> selectResultsByPeopleUsername(String peopleUsername);

    int insert(@Param("peopleUsername") String peopleUsername,@Param("agencyUsername")  String agencyUsername,
               @Param("resultTime") int resultTime, @Param("result") CovidTestResult.type result);

    ArrayList<SimpleResult> selectMapToSimpleResult(String peopleUsername);

}
