package com.gzu.system.mapper;

import com.gzu.system.pojo.Place;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper {

   // 1.传入场所信息，将场所信息插入place_table表内
    int insert(String userName, String placeName, String placeAddress);

  //  2.传入username，查询place_table,返回场所信息
    Place selectPalaceByUserName(String username);

    //3.将大众用户名、场所用户名和当前时间戳(秒为单位)插入people_track表

    int insertTrack(String peopleUsername, String placeUsername,int passingTime);
}
