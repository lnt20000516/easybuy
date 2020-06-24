package cn.easybuy.dao.user;

import cn.easybuy.pojo.User;
import cn.easybuy.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户类
 * 实现用户增删改查的类
 * @author 刘楠婷
 * @time 2020年6月23
 */
@Mapper
@Component
public interface UserMapper {
//    @Insert("insert into easybuy_user(loginName, userName, password," +
//            "sex) values (#{user.loginName}, #{user.userName}, #{user.password}, #{user.sex})")

    /**
     * 添加用户信息
     * @param user 用户信息
     * @return
     */
    @InsertProvider(type = UserProvider.class, method = "insertUserByParam")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    int add(@Param("user") User user);

    /**
     * 检测用户名是否已经被使用
     */
    @Select("select count('loginName') from easybuy_user where loginName=#{loginName}")
    int checkLoginName(@Param("loginName") String loginName);

    /**
     * 检测用户登录是否成功
     * @param user
     * @return
     */
    @Select("select * from easybuy_user where loginName=#{user.loginName} and password=#{user.password}")
    List<User> loginByLoginNameAndPassword(@Param("user") User user);

    /**
     * 根据用户的id更新用户信息
     * @param user 用户的id和用户的详情
     * @return
     */
    @UpdateProvider(value = UserProvider.class, method = "updateUserById")
    int updateUserById(@Param("user") User user) ;//更新用户信息

    /**
     * 根据id删除用户
     * @param id id
     * @return
     */
    @Delete("delete from easybuy_user where id=#{id}")
    int deleteUserById(@Param("id") Integer id);

    /**
     * 分页查询
     * @param from 开始的位置
     * @param pageSize 个数
     * @return
     */
    @Select("select * from easybuy_user limit #{from}, #{pageSize}")
    List<User> getUserList(@Param("from") Integer from, @Param("pageSize") Integer pageSize);

    /**
     * 获得用户表中的条目数
     * @return
     */
    @Select("select count(1) from easybuy_user")
    Integer count();

    /**
     * 获得所有用户的信息
     * @return
     */
    @Select("select * from easybuy_user")
    List<User> getAll();

    /**
     * 根据id或login Name获得用户信息
     * @param id id
     * @param loginName 登录名
     * @return
     */
    @SelectProvider(value = UserProvider.class, method = "getUser")
    User getUser(@Param("id") Integer id, @Param("loginName") String loginName) ;
}
