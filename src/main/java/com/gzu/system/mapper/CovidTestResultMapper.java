package com.gzu.system.mapper;

import com.gzu.system.pojo.CovidTestResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
@Mapper
public interface CovidTestResultMapper {
    //3.在covid_test_result表里查询peopleUsername的所有核酸检测记录

    ArrayList<CovidTestResult> selectResultsByPeopleUsername(String peopleUsername);

}
