package cn.easybuy.dao.order;

import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.provider.OrderProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单类
 * 实现用户订单的类
 * @author 杜煜兴
 * @time 2020年6月22
 */
@Mapper
@Component
public interface OrderMapper {
    /**
     * 添加一条数据
     * @param detail 需要插入的信息
     * @return
     */
    @Insert("insert into easybuy_order(userId, loginName, userAddress, createTime, cost, serialNumber) " +
            "values(#{detail.userId}, #{detail.loginName}, #{detail.userAddress}, #{detail.createTime}," +
            " #{detail.cost}, #{detail.serialNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "detail.id")
    int add(@Param("detail") Order detail);


    /**
     * 根据id获得order
     * @param id easybuy_order中的主键id
     * @return
     */
    @Select("select * from easybuy_order where id=#{id}")
    Order getOrderById(@Param("id") Integer id);

    /**
     * 根据用户id获得用户的订单，不传返回全部
     * @param userId 用户id
     * @return
     */
    @SelectProvider(value = OrderProvider.class, method = "getOrderByUserId")
    List<Order> getOrderByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id获得该用户有多少条订单信息
     * @param userId 用户id
     * @return
     */
    @Select("select count(1) from easybuy_order where userId=#{userId}")
    Integer count(@Param("userId") Integer userId);

    /**
     * 根据id修改总价格
     * @param id id
     * @param cost 价格
     */
    @Update("update easybuy_order set cost=#{cost} where id=#{id}")
    void updateCostById(@Param("id") Integer id, @Param("cost") Float cost);
}
