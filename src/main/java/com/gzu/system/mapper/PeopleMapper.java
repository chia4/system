package com.gzu.system.mapper;

import com.gzu.system.pojo.People;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PeopleMapper {
    /**
     * 根据username，查询是否存在这个人
     * @param username
     * @return People
     */
    People selectByUsername(String username);

    /**
     * 插入一条people记录
     * @param people
     * @return
     */
    int insert(People people);

    /**
     * 根据idCardNumber,查询是否存在这个人
     * @param idCardNumber
     * @return
     */
    People selectByIdCardNumber(String idCardNumber);


    /**
     * 根据用户名，更新红绿码时间戳
     * @return
     */
    int updateByUsername(@Param("username") String username, @Param("greenCodeAfter") int greenCodeAfter);

}
