package com.gzu.system.service;


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

public class PeopleService {
}
