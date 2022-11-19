package com.gzu.system.service;

public interface IUserLoginService {
    /* 需要将密码用sha256取哈希后存入数据库
     * 返回值:
     *     0 - 成功
     *     1 - 用户名被占用
     *     2 - 其他错误
     */
    int register(String username, String password, String type);

    /* 需要将密码用sha256取哈希后存入数据库
     * 返回值:
     *     0 - 成功，用户类型为PEOPLE
     *     1 - 成功，用户类型为PLACE
     *     2 - 成功，用户类型为AGENCY
     *     3 - 密码错误
     *     4 - 用户不存在
     *     5 - 其他错误
     */
    int login(String username, String password);
}
