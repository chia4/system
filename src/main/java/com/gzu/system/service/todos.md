根据下面接口方法实现类方法，不用将接口写进代码

```java
interface UserLoginService {
    /** 根据输入的username在user_login表查询
     *  返回值:
     *  UserLogin - 成功
     *  null - 失败
     */
    UserLogin getUserByUsername(String username);
}

interface PlaceService {
    /** 传入场所信息，将场所信息插入place_table表内
     *  返回值:
     *  0 - 成功
     *  1 - placeName已存在
     *  2 - 其他错误
     */
    int completeInformation(String username, String placeName, String placeAddress);

    /** 传入username，返回场所信息
     *  返回值:
     *  Place - 成功
     *  null - 失败
     */
    Place getInformation(String username);

    /** 将大众用户名、场所用户名和当前时间戳(秒为单位)插入people_track表
     *  不用验证用户类型，我会在响应层验证
     *  返回值:
     *  0 - 成功
     *  1 - 失败
     */
    int recordTrack(String peopleUsername, String placeUsername);
}

interface AgencyService {
    /** 将大众用户名、机构用户名和当前时间戳(秒为单位)插入covid_test_authorization表
     *  不用验证用户类型，我会在响应层验证
     *  返回值:
     *  0 - 成功
     *  1 - 失败
     */
    int registrationSampling(String peopleUsername, String agencyUsername);

    /** 首先验证(peopleUsername, agencyUsername, authorizationTime)是否存在
     *  于covid_test_authorization表里
     *  如果存在，在covid_test_authorization表里删除这条记录，在covid_test_result
     *  表里录入结果
     *  如果结果为阳性，另外开一个线程将时空交集者(在阳性用户经过场所的正负1小时内经过同
     *  样场所在people_track留下记录)的green_code_after设置为当前时间戳+7天，将场
     *  所设置为风险地区，将赋码的用户和设为风险地区的地区打印在控制台
     *  返回值:
     *  0 - 成功录入
     *  1 - 没有找到记录，无法录入
     *  2 - 其他错误
     */
    int uploadResult(String peopleUsername, String agencyUsername, int authorizationTime, String result);

    /** 在covid_test_authorization表里查询agencyUsername的所有授权记录
     *  返回值:
     *  ArrayList<CovidTestAuthorization> - 成功
     *  null - 无记录
     */
    ArrayList<CovidTestAuthorization> getAuthorization(String agencyUsername);

    /** 在covid_test_result表里查询peopleUsername的所有核酸检测记录
     *  返回值:
     *  ArrayList<CovidTestAuthorization> - 成功
     *  null - 无记录
     */
    ArrayList<CovidTestResult> getResult(String peopleUsername);
}

```