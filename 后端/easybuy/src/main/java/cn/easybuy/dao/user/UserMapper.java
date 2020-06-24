package cn.easybuy.dao.user;

import cn.easybuy.pojo.User;
import cn.easybuy.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @UpdateProvider(value = UserProvider.class, method = "updateUserById")
    int updateUserById(@Param("user") User user) ;//更新用户信息

    @Delete("delete from easybuy_user where id=#{id}")
    int deleteUserById(@Param("id") Integer id);

    @Select("select * from easybuy_user limit #{from}, #{pageSize}")
    List<User> getUserList(@Param("from") Integer from, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from easybuy_user")
    Integer count();

    @Select("select * from easybuy_user")
    List<User> getAll();

    @SelectProvider(value = UserProvider.class, method = "getUser")
    User getUser(@Param("id") Integer id, @Param("loginName") String loginName) ;
}
