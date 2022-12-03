package com.gzu.system.mapper;

import com.gzu.system.pojo.PeopleTrack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PeopleTrackMapper {
    List<PeopleTrack> selectByNameAndDays(@Param("peopleUsername") String peopleUsername,
                                          @Param("nowDays") int nowdays,
                                          @Param("beforeDays")int beforeDays);

    List<PeopleTrack> selectByPlaceNameAndDays(@Param("placeUsername") String placeUsername,
                                               @Param("nowDays") int nowdays,
                                               @Param("beforeDays")int beforeDays);
}
