package cn.easybuy.dao.shoppingCart;

import cn.easybuy.pojo.ShoppingCart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 购物车类
 * 实现购物车的增删改查的类
 * @author 侯志强
 * @time 2020年6月22
 */
@Mapper
@Component
public interface ShoppingCartMapper {
    /**
     * 插入一条数据
     * @param cart 详情
     * @return
     */
    @Insert("insert into easybuy_shopping_cart(productId, userId, price, quantity) " +
            "values(#{cart.productId}, #{cart.userId}, #{cart.price} , #{cart.quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "cart.id")
    int insert(@Param("cart") ShoppingCart cart);

    /**
     * 根据用户id获得购物车中的内容
     * @param userId 用户的id
     * @return
     */
    @Select("select * from easybuy_shopping_cart where userId=#{userId}")
    List<ShoppingCart> queryCartByUserId(@Param("userId") Integer userId);

    /**
     * 根据id删除购物车中的数据
     * @param id id
     * @return
     */
    @Delete("delete from easybuy_shopping_cart where id=#{id}")
    int deleteCartById(@Param("id") Integer id);

    /**
     * 根据id修改数量
     * @param id id
     * @param quantity 数量
     * @return
     */
    @Update("update easybuy_shopping_cart set quantity=#{quantity} where id=#{id}")
    int updateQuantityById(@Param("id") Integer id, @Param("quantity") Integer quantity);

    /**
     * 根据用户id删除所有订单
     * @param userId 用户id
     * @return
     */
    @Delete("delete from easybuy_shopping_cart where userId=#{userId}")
    int deleteCartByUserId(@Param("userId") Integer userId);
}
