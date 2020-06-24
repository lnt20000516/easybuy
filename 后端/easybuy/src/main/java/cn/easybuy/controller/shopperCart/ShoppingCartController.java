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

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("productId") Integer productId,
                         @RequestParam("userId") Integer userId,
                         @RequestParam("price") Float price,
                         @RequestParam("quantity") Integer quantity) {
        return shoppingCartService.insert(new ShoppingCart(productId, userId, price, quantity));
    }

    @RequestMapping("/deleteCartById")
    public String deleteCartById(@RequestParam("id") Integer id) {
        return shoppingCartService.deleteCartById(id);
    }

    @RequestMapping("/updateQuantityById")
    public String updateQuantityById(@RequestParam("id") Integer id,
                                 @RequestParam("quantity") Integer quantity) {
        return shoppingCartService.updateQuantityById(id, quantity);
    }

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

    @RequestMapping("/getShopperCartByUserId")
    public String getShopperCartByUserId(@RequestParam("userId") Integer userId) {
        return shoppingCartService.getShopperCartByUserId(userId);
    }
}
