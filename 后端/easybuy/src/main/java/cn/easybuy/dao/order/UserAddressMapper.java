package cn.easybuy.dao.order;

import cn.easybuy.pojo.UserAddress;
import cn.easybuy.provider.UserAddressProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserAddressMapper {

    @Select("select * from easybuy_user_address")
    List<UserAddress> queryUserAddressList();

    @Select("select * from easybuy_user_address where userId=#{userId}")
    List<UserAddress> queryUserAddressListByUserId(@Param("userId") Integer userId);

    @InsertProvider(value = UserAddressProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "userAddress.id")
    Integer add(@Param("userAddress") UserAddress userAddress);

    @Select("select * from easybuy_user_address where id=#{id}")
    UserAddress getUserAddressById(@Param("id") Integer id);
}
