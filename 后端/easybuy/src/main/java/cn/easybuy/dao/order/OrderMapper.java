package cn.easybuy.dao.order;

import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.provider.OrderProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into easybuy_order(userId, loginName, userAddress, createTime, cost, serialNumber) " +
            "values(#{detail.userId}, #{detail.loginName}, #{detail.userAddress}, #{detail.createTime}," +
            " #{detail.cost}, #{detail.serialNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "detail.id")
    int add(@Param("detail") Order detail);

    void deleteById(Integer id);

    @Select("select * from easybuy_order where id=#{id}")
    Order getOrderById(@Param("id") Integer id);

    // 根据用户id获得用户的订单，不传返回全部
    @SelectProvider(value = OrderProvider.class, method = "getOrderByUserId")
    List<Order> getOrderByUserId(@Param("userId") Integer userId);

    List<Order> getOrderList(Integer orderId, Integer currentPageNo, Integer pageSize);

    @Select("select count(1) from easybuy_order where userId=#{userId}")
    Integer count(@Param("userId") Integer userId);

    @Update("update easybuy_order set cost=#{cost} where id=#{id}")
    void updateCostById(@Param("id") Integer id, @Param("cost") Float cost);
}
