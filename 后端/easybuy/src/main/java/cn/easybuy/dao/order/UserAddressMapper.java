package cn.easybuy.dao.order;

import cn.easybuy.pojo.UserAddress;
import cn.easybuy.provider.UserAddressProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户地址类
 * 实现用户地址的增删改查的类
 * @author 侯志强
 * @time 2020年6月21
 */
@Mapper
@Component
public interface UserAddressMapper {

    /**
     * 查询所有用户的地址
     * @return
     */
    @Select("select * from easybuy_user_address")
    List<UserAddress> queryUserAddressList();

    /**
     * 根据用户id查询用户的地址
     * @param userId 用户的id
     * @return
     */
    @Select("select * from easybuy_user_address where userId=#{userId}")
    List<UserAddress> queryUserAddressListByUserId(@Param("userId") Integer userId);

    /**
     * 添加一条数据
     * @param userAddress 用户的地址详情
     * @return
     */
    @InsertProvider(value = UserAddressProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "userAddress.id")
    Integer add(@Param("userAddress") UserAddress userAddress);

    /**
     * 根据用户地址id获得用户的地址
     * @param id id
     * @return
     */
    @Select("select * from easybuy_user_address where id=#{id}")
    UserAddress getUserAddressById(@Param("id") Integer id);
}
