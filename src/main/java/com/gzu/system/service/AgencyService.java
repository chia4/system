package com.gzu.system.service;

import com.gzu.system.mapper.*;
import com.gzu.system.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class AgencyService {

    @Autowired
    private CovidTestAuthorizationMapper covidTestAuthorizationMapper;

    @Autowired
    private CovidTestResultMapper covidTestResultMapper;

    @Autowired
    private PeopleTrackMapper peopleTrackMapper;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private PlaceMapper placeMapper;

    @Autowired
    private AgencyMapper agencyMapper;

    /** 将大众用户名、机构用户名和当前时间戳(秒为单位)插入covid_test_authorization表
     *  不用验证用户类型，我会在响应层验证
     *  返回值:
     *  CovidTestAuthorization - 成功
     *  null - 失败
     */
    @Transactional
    public CovidTestAuthorization registrationSampling(String peopleUsername, String agencyUsername){
        //插入指标
        int count=0;
        //检错指标
        boolean isError=false;
        int passingTime=0;
        try{
            passingTime= (int)(new Date().getTime() / 1000);
            count=covidTestAuthorizationMapper.insert(peopleUsername,agencyUsername,passingTime);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }
        //如果没插入或者检出错，返回1
        if (isError||count==0){
            return null;
        }
        //否则返回0
        return new CovidTestAuthorization(peopleUsername, agencyUsername, passingTime);
    }

    /** 在covid_test_authorization表里查询agencyUsername的所有授权记录
     *  返回值:
     *  ArrayList<CovidTestAuthorization> - 成功
     *  null - 无记录
     */
    public ArrayList<CovidTestAuthorization> getAuthorization(String agencyUsername){
        ArrayList<CovidTestAuthorization> covidTestAuthorizations = covidTestAuthorizationMapper.selectAuthorizationsByAgencyUsername(agencyUsername);
        if(covidTestAuthorizations.size()>0){
            return covidTestAuthorizations;
        }
        return null;
    }

    /** 在covid_test_result表里查询peopleUsername的所有核酸检测记录
     *  返回值:
     *  ArrayList<CovidTestAuthorization> - 成功
     *  null - 无记录
     */
    public ArrayList<CovidTestResult> getResult(String peopleUsername){
        ArrayList<CovidTestResult> covidTestResults = covidTestResultMapper.selectResultsByPeopleUsername(peopleUsername);
        if(covidTestResults.size()>0){
            return covidTestResults;
        }
        return null;
    }

    /** 首先验证(peopleUsername, agencyUsername, authorizationTime)是否存在
     *  于covid_test_authorization表里
     *  如果存在，在covid_test_authorization表里删除这条记录，在covid_test_result
     *  表里录入结果
     *  如果结果为阳性，另外开一个线程将时空交集者(在阳性用户经过场所的正负1小时内经过同
     *  样场所在people_track留下记录)的green_code_after设置为当前时间戳+7天，将场
     *  所设置为风险地区，将赋码的用户和设为风险地区的地区打印在控制台
     *  返回值:*
     *  0 - 成功录入
     *  1 - 没有找到记录，无法录入
     *  2 - 其他错误
     *  根据covid_test_result的阳性用户（people_username），查询在people_track的passing_time加减一小时
     *  的用户
     *  然后把这些用户的green_code_after设置为当前时间戳+7天（people_table）
     *  然后把根据place_username设置风险地区
     *  最后把赋码的用户和设置为风险地区打印到控制台
     *
     *
     *  1.根据people_username（阳性）,查询people_track(用户场所表)，查询条件是people_username的小时加减一，并记录这些用户
     *  2.根据上面得到的people_username，查询（people_table）设置时间错+7天，
     *  3.根据上面得到的place_username,设置风险地区
     *  4.查询风险地区
     *
     */
    @Transactional
    public int uploadResult(String peopleUsername, String agencyUsername, int authorizationTime, String result){
        CovidTestAuthorization covidTestAuthorization = covidTestAuthorizationMapper.selectOne
                (peopleUsername, agencyUsername, authorizationTime);
        if (covidTestAuthorization==null){
            return 1;
        }

        boolean isError=false;
        int count=0;
        try {
            count=covidTestAuthorizationMapper.deleteOne(peopleUsername,agencyUsername,authorizationTime);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }
        if(isError||count==0){
            return 2;
        }

        isError=false;
        count=0;
        try{
            CovidTestResult.type myresult=Enum.valueOf(CovidTestResult.type.class,result);
            count=covidTestResultMapper.insert(peopleUsername,agencyUsername,authorizationTime,myresult);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }
        if(isError||count==0){
            return 2;
        }

        isError=false;
        count=0;
        try{
            if(result.equals("POSITIVE")){
                new Thread(){
                    @Override
                    public void run() {
                        /** 首先验证(peopleUsername, agencyUsername, authorizationTime)是否存在
                         *  于covid_test_authorization表里
                         *  如果存在，在covid_test_authorization表里删除这条记录，在covid_test_result
                         *  表里录入结果
                         *  如果结果为阳性，另外开一个线程将时空交集者(在阳性用户经过场所的正负1小时内经过同
                         *  样场所在people_track留下记录)的green_code_after设置为当前时间戳+7天，将场
                         *  所设置为风险地区，将赋码的用户和设为风险地区的地区打印在控制台
                         *  返回值:*
                         *  0 - 成功录入
                         *  1 - 没有找到记录，无法录入
                         *  2 - 其他错误
                         *  根据covid_test_result的阳性用户（people_username），查询在people_track的passing_time加减一小时
                         *  的用户
                         *  然后把这些用户的green_code_after设置为当前时间戳+7天（people_table）
                         *  然后把根据place_username设置风险地区
                         *  最后把赋码的用户和设置为风险地区打印到控制台
                         *
                         *
                         *  1.根据people_username（阳性）,查询people_track(用户场所表)，查询条件是people_username的小时加减一，并记录这些用户
                         *  2.根据上面得到的people_username，查询（people_table）设置时间错+7天，
                         *  3.根据上面得到的place_username,设置风险地区
                         *  4.查询风险地区
                         *
                         */
                        //根据阳性用户查找当前时间七天内的所有记录
                        int nowTime = (int) new Date().getTime() / 1000;//现在的时间
                        int beforeTime=nowTime-60*60*24*7;//七天前
                        List<PeopleTrack> peopleTracks = peopleTrackMapper.selectByNameAndDays(peopleUsername, nowTime, beforeTime);//七天内所有记录
                        HashMap<String, List<PeopleTrack>> placeNameTrackMap = new HashMap<>();

                        //针对7天内的所有记录的场所名字段，查询所有记录，以<场所名，记录集>存储
                        for (PeopleTrack peopleTrack : peopleTracks) {
                            placeNameTrackMap.put(peopleTrack.getPlaceUsername(),
                                    peopleTrackMapper.selectByPlaceNameAndDays(peopleTrack.getPlaceUsername(),
                                            peopleTrack.getPassingTime()+3600,
                                            peopleTrack.getPassingTime()-3600));
                        }
                        //获取所有风险场所名的名字
                        Set<String> placeNameSet = placeNameTrackMap.keySet();
                        for (String placeName : placeNameSet) {
                            //输出风险场所信息
                            System.out.println(placeName+":");
                            //获得风险场所的对应记录集
                            List<PeopleTrack> peopleTrackList = placeNameTrackMap.get(placeName);
                            for (PeopleTrack peopleTrack : peopleTrackList) {
                                //新时间
                                Integer newTime = peopleTrack.getPassingTime();
                                String pUserName = peopleTrack.getPeopleUsername();
                                //查询密接人信息
                                People selectByUsername = peopleMapper.selectByUsername(pUserName);
                                //旧值和新值比较，如果大就修改，如果小就不处理
                                if(selectByUsername.getGreenCodeAfter()<newTime){
                                    peopleMapper.updateByUsername(peopleUsername,newTime);
                                }
                                //输出密接人名字
                                System.out.print(pUserName+" ");
                                //查询场所信息
                                Place place = placeMapper.selectPlaceByPlaceName(placeName);
                                //旧值和新值比较，如果大就修改，如果小就不处理
                                if(place.getLowRiskAfter()<newTime){
                                    placeMapper.updateRisk(placeName,newTime);
                                }
                            }
                            System.out.println();//换行
                        }


                    }
                }.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            isError=true;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        if (isError){
            return 2;
        }


        return 0;

    }
    /** 传入机构信息，将场所信息插入agency_table表内
     *  返回值:
     *  0 - 成功
     *  1 - agencyName已存在
     *  2 - 其他错误
     */
    @Transactional
    public int completeInformation(String username, String agencyName, String agencyAddress){
        Agency agencyBySelect = agencyMapper.selectByAgencyName(agencyName);
        if(agencyBySelect!=null){
            return 1;
        }
        boolean isError=false;
        int count=0;
        try{
            count=agencyMapper.insert(username, agencyName, agencyAddress);
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }

        if (isError||count==0){
            return 2;
        }
        return 0;
    }

    /** 传入username，返回机构信息
     *  返回值:
     *  Agency - 成功
     *  null - 失败
     */
    public Agency getInformation(String username){
        return agencyMapper.selectByUserName(username);
    }

}

