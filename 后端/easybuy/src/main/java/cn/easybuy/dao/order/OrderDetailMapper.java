package cn.easybuy.dao.order;

import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface OrderDetailMapper {
    @Insert("insert into easybuy_order_detail(orderId,productId,quantity,cost) " +
            "values(#{detail.orderId},#{detail.productId},#{detail.quantity},#{detail.cost})")
    @Options(useGeneratedKeys = true, keyProperty = "detail.id")
    int add(@Param("detail") OrderDetail detail);

    void deleteById(OrderDetail detail);

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

    @Select("select * from easybuy_product where id=#{id}")
    @ResultType(Product.class)
    Product getProductById(@Param("id") Integer id);

    @Select("select * from easybuy_order_detail where orderId=#{orderId}")
    @Results(value = {
            @Result(property = "productId", column = "productId"),
            @Result(property = "product", column = "productId", one = @One(select =
                    "getProductById"), javaType = Product.class)
    })
    List<OrderDetail> getOrderDetailList(@Param("orderId") Integer orderId);

    @Select("select * from easybuy_order_detail limit #{from},#{pageSize}")
    Integer queryOrderDetailCount(@Param("from") Integer from,
                                  @Param("pageSize") Integer pageSize);
}
/**

 */