package cn.easybuy.controller.shopperCart;

import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.ShoppingCart;
import cn.easybuy.service.shoppingCart.ShoppingCartService;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.utils.TimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 购物车类
 * 实现购物车的增删改查的类
 * @author 侯志强
 * @time 2020年6月22
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 插入一条数据
     * @return
     */
    @RequestMapping("/insert")
    public String insert(@RequestParam("productId") Integer productId,
                         @RequestParam("userId") Integer userId,
                         @RequestParam("price") Float price,
                         @RequestParam("quantity") Integer quantity) {
        return shoppingCartService.insert(new ShoppingCart(productId, userId, price, quantity));
    }

    /**
     * 根据id删除购物车中的数据
     * @param id id
     * @return
     */
    @RequestMapping("/deleteCartById")
    public String deleteCartById(@RequestParam("id") Integer id) {
        return shoppingCartService.deleteCartById(id);
    }

    /**
     * 根据id修改数量
     * @param id id
     * @param quantity 数量
     * @return
     */
    @RequestMapping("/updateQuantityById")
    public String updateQuantityById(@RequestParam("id") Integer id,
                                 @RequestParam("quantity") Integer quantity) {
        return shoppingCartService.updateQuantityById(id, quantity);
    }

    /**
     * 提交，根据用户id删除表中的所有信息
     * @return
     */
    // userId, loginName, userAddress, createTime, cost, serialNumber
    @RequestMapping("/submit")
    public String submit(@RequestParam("userId") Integer userId,
                      @RequestParam("loginName") String loginName,
                      @RequestParam("userAddress") String userAddress,
                      @RequestParam("cost") Float cost) {
        Order order = new Order();
        // 设置当前时间
        order.setCreateTime(TimeUtils.getNowTime());
        // 设置SerialNumber
        order.setSerialNumber(SecurityUtils.md5Hex(String.valueOf(System.currentTimeMillis() % 6)));
        order.setUserId(userId);
        order.setLoginName(loginName);
        order.setUserAddress(userAddress);
        order.setCost(cost);
        return shoppingCartService.submit(order);
    }

    /**
     * 根据用户id获得购物车中的内容
     * @param userId 用户的id
     * @return
     */
    @RequestMapping("/getShopperCartByUserId")
    public String getShopperCartByUserId(@RequestParam("userId") Integer userId) {
        return shoppingCartService.getShopperCartByUserId(userId);
    }
}
