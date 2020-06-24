package cn.easybuy.service.shoppingCart;

import cn.easybuy.dao.order.OrderDetailMapper;
import cn.easybuy.dao.order.OrderMapper;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.dao.shoppingCart.ShoppingCartMapper;
import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.pojo.Product;
import cn.easybuy.service.order.OrderService;
import cn.easybuy.utils.MyJsonUtils;
import cn.easybuy.pojo.ShoppingCart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {
    @Resource
    ShoppingCartMapper shoppingCartMapper;
    @Resource
    OrderDetailMapper orderDetailMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderService orderService;
    @Resource
    ProductMapper productMapper;

    public String insert(ShoppingCart cart) {
        int i = shoppingCartMapper.insert(cart);
        cart.setProduct(productMapper.getProductById(cart.getProductId()));
        List<ShoppingCart> list = new ArrayList<>();
        list.add(cart);

        return new MyJsonUtils<ShoppingCart>()
                .put("code", 200)
                .put("message", "受影响条数" + i)
                .addArray("data", list)
                .toString();
    }

    public String deleteCartById(Integer id) {
        int i = shoppingCartMapper.deleteCartById(id);

        return new MyJsonUtils<ShoppingCart>()
                .put("code", 200)
                .put("message", "受影响条数" + i)
                .toString();
    }

    public String updateQuantityById(Integer id, Integer quantity) {
        int i = shoppingCartMapper.updateQuantityById(id, quantity);

        return new MyJsonUtils<ShoppingCart>()
                .put("code", 200)
                .put("message", "受影响条数" + i)
                .toString();
    }

    // 提交，根据用户id删除表中的所有信息
    public String submit(Order detail) {
        // 添加到order表中
        List<ShoppingCart> list = shoppingCartMapper.queryCartByUserId(detail.getUserId());

        // userId, loginName, userAddress, createTime, cost, serialNumber
        // 添加order数据
        orderMapper.add(detail);

        // 添加orderDetail数据
        for (ShoppingCart shoppingCart : list) {
            // orderId,productId,quantity,cost
            orderDetailMapper.add(new OrderDetail(detail.getId(), shoppingCart.getProductId(), shoppingCart.getQuantity(),
                    shoppingCart.getPrice() * shoppingCart.getQuantity()));
        }

        // 清空购物车
        int i = shoppingCartMapper.deleteCartByUserId(detail.getUserId());

        return orderService.getOrderById(detail.getId());
    }

    public String getShopperCartByUserId(Integer userId) {
        List<ShoppingCart> list = shoppingCartMapper.queryCartByUserId(userId);
        for (ShoppingCart shoppingCart : list) {
            shoppingCart.setProduct(productMapper.getProductById(shoppingCart.getProductId()));
        }
        return new MyJsonUtils<ShoppingCart>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }
}
