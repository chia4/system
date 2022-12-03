根据下面接口方法实现类方法，不用将接口写进代码

```java
interface PeopleService {
    /** 在covid_test_result表里查询peopleUsername的所有核酸检测记录
     *  然后和agency_table按照agency_username连接查询
     *  返回值:
     *  ArrayList<com.gzu.system.pojo.SimpleResult> - 成功
     *  null - 无记录
     */
    ArrayList<com.gzu.system.pojo.SimpleResult> getSimpleResult(String peopleUsername);

    /** 在covid_test_authorization表里查询peopleUsername的所有核酸检测记录
     *  然后和agency_table按照agency_username连接查询
     *  返回值:
     *  ArrayList<com.gzu.system.pojo.SimpleAuthorization> - 成功
     *  null - 无记录
     */
    ArrayList<com.gzu.system.pojo.SimpleAuthorization> getSimpleAuthorization(String peopleUsername);
}

```
