package com.gzu.system.mapper;

import com.gzu.system.pojo.People;
import org.apache.ibatis.annotations.Mapper;

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


}
