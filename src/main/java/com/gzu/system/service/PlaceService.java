package com.gzu.system.service;

import com.gzu.system.mapper.PlaceMapper;
import com.gzu.system.pojo.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

@Service
public class PlaceService {

    @Autowired
    private PlaceMapper mapper;

    /** 传入场所信息，将场所信息插入place_table表内
     *  返回值:
     *  0 - 成功
     *  1 - placeName已存在
     *  2 - 其他错误
     */
    @Transactional
    public int completeInformation(String username, String placeName, String placeAddress){
        Place placeBySelect = mapper.selectPlaceByPlaceName(placeName);
        if(placeBySelect!=null){
            return 1;
        }

        boolean isError=false;
        int count=0;
        try{
            count=mapper.insert(username, placeName, placeAddress);
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


    /** 传入username，返回场所信息
     *  返回值:
     *  Place - 成功
     *  null - 失败
     */
    public Place getInformation(String username){
        return mapper.selectPalaceByUserName(username);
    }

    /** 将大众用户名、场所用户名和当前时间戳(秒为单位)插入people_track表
     *  不用验证用户类型，我会在响应层验证
     *  返回值:
     *  0 - 成功
     *  1 - 失败
     */
    @Transactional
    public int recordTrack(String peopleUsername, String placeUsername){
        //插入指标
        int count=0;
        //检错指标
        boolean isError=false;
        try{
            int passingTime= (int)(new Date().getTime() / 1000);
            count=mapper.insertTrack(peopleUsername,placeUsername,passingTime);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isError=true;
        }
        //如果没插入或者检出错，返回1
        if (isError||count==0){
            return 1;
        }
        //否则返回0
        return 0;
    }
}
