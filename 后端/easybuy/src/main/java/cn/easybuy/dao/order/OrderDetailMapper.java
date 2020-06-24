package cn.easybuy.dao.order;

import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * 订单详情类
 * 实现用户订单详情的增删改查的类
 * @author 杜煜兴
 * @time 2020年6月22
 */
@Mapper
@Component
public interface OrderDetailMapper {
    /**
     * 添加一条数据
     * @param detail 订单详情的内容
     * @return
     */
    @Insert("insert into easybuy_order_detail(orderId,productId,quantity,cost) " +
            "values(#{detail.orderId},#{detail.productId},#{detail.quantity},#{detail.cost})")
    @Options(useGeneratedKeys = true, keyProperty = "detail.id")
    int add(@Param("detail") OrderDetail detail);

    /**
     * 根据id查询订单
     */
    @Select("select * from easybuy_order_detail where id=#{id}")
//    @Select("select a.*, b.name as 'pName', b.id as 'pId' from easybuy_order_detail as a LEFT JOIN easybuy_product as b on a.productId = b.id where a.id=#{id}")
    @Results(value = {
            @Result(property = "productId", column = "productId"),
            @Result(property = "product", column = "productId", one = @One(select =
                    "getProductById"), javaType = Product.class)
    })
    OrderDetail getOrderDetailById(@Param("id") Integer id);

    /**
     * 根据id获得商品
     * @param id id
     * @return
     */
    @Select("select * from easybuy_product where id=#{id}")
    @ResultType(Product.class)
    Product getProductById(@Param("id") Integer id);

    /**
     * 根据订单id获得它下面的所有订单详情
     * @param orderId 订单id（orderId）
     * @return
     */
    @Select("select * from easybuy_order_detail where orderId=#{orderId}")
    @Results(value = {
            @Result(property = "productId", column = "productId"),
            @Result(property = "product", column = "productId", one = @One(select =
                    "getProductById"), javaType = Product.class)
    })
    List<OrderDetail> getOrderDetailList(@Param("orderId") Integer orderId);

    /**
     * 分页查询订单详情信息
     * @param from 从哪个位置开始
     * @param pageSize 总大小
     * @return
     */
    @Select("select * from easybuy_order_detail limit #{from},#{pageSize}")
    Integer queryOrderDetailCount(@Param("from") Integer from,
                                  @Param("pageSize") Integer pageSize);
}
/**

 */