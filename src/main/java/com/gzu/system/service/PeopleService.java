package com.gzu.system.service;


import com.gzu.system.mapper.CovidTestAuthorizationMapper;
import com.gzu.system.mapper.CovidTestResultMapper;
import com.gzu.system.mapper.PeopleMapper;
import com.gzu.system.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 需求:
 * 1. 传入用户信息，将用户信息插入people_table表内
 *    返回值:
 *    0 - 成功
 *    1 - 身份证已存在
 *    2 - 其他错误
 * int completeInformation(String userName, String fullName, String phoneNumber, String idCardNumber, String userGender);
 *
 *
 * 2. 传入username，返回用户信息
 *    返回值:
 *    com.gzu.system.pojo.People - 成功
 *    null - 失败
 * People getInformation(String userName)；
 *
 */

@Service
public class PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private CovidTestResultMapper covidTestResultMapper;


    @Autowired
    private CovidTestAuthorizationMapper covidTestAuthorizationMapper;

    /**
     * 传入用户信息，将用户信息插入people_table表内
     * @param userName
     * @param fullName
     * @param phoneNumber
     * @param idCardNumber
     * @param userGender
     * @return 0 or 1 or 2
     */
    @Transactional
    public int completeInformation(String userName, String fullName, String phoneNumber, String idCardNumber, String userGender){
        //1.根据userName,查找people_table是否存在这个人
        People peopleBySelect = peopleMapper.selectByIdCardNumber(idCardNumber);
        //存在，则表示身份证已存在，无法插入
        if(peopleBySelect!=null){
            return 1;
        }
        //不存在，则可以插入

        //假设没有异常
        boolean isError=false;

        //2.插入该用户，存在异常则isError=true表示存在异常，反之
        try{
            People people = new People();
            people.setUsername(userName);
            people.setFullName(fullName);
            people.setPhoneNumber(phoneNumber);
            people.setIdCardNumber(idCardNumber);
            People.gender gender=Enum.valueOf(People.gender.class,userGender);
            people.setUserGender(gender);
            people.setGreenCodeAfter(0);
            peopleMapper.insert(people);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }

        //3.检查是否有异常，如果有异常，触发回滚并返回2
        if (isError){
            return 2;
        }
        //4.插入成功，没有异常，返回0
        return 0;
    }

    /**
     * 传入username，返回用户信息
     * @param username
     * @return people or null
     */
    public People getInformation(String username){
        return peopleMapper.selectByUsername(username);
    }



    /** 在covid_test_result表里查询peopleUsername的所有核酸检测记录
     *  然后和agency_table按照agency_username连接查询
     *  返回值:
     *  ArrayList<com.gzu.system.pojo.SimpleResult> - 成功
     *  null - 无记录
     */
    public ArrayList<SimpleResult> getSimpleResult(String peopleUsername){
        ArrayList<SimpleResult> simpleResults = covidTestResultMapper.selectMapToSimpleResult(peopleUsername);
        if (simpleResults.size()==0){
            return null;
        }
        return simpleResults;
    }

    /** 在covid_test_authorization表里查询peopleUsername的所有核酸检测记录
     *  然后和agency_table按照agency_username连接查询
     *  返回值:
     *  ArrayList<com.gzu.system.pojo.SimpleAuthorization> - 成功
     *  null - 无记录
     */
    public ArrayList<SimpleAuthorization> getSimpleAuthorization(String peopleUsername){
        ArrayList<SimpleAuthorization> simpleAuthorizations = covidTestAuthorizationMapper.selectMapToSimpleAuthorization(peopleUsername);
        if(simpleAuthorizations.size()==0){
            return null;
        }
        return simpleAuthorizations;
    }

}
