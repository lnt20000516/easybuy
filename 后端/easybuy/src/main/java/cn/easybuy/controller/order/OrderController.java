package cn.easybuy.controller.order;

import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.service.order.OrderDetailService;
import cn.easybuy.service.order.OrderService;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.utils.TimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderService orderService;

    //userId, loginName, userAddress, createTime, cost, serialNumber
    @RequestMapping("/add")
    public String add(@RequestParam("userId") Integer userId,
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
        return orderService.add(order);
    }

    @RequestMapping("/getOrderById")
    public String getOrderDetailById(@RequestParam("id") Integer id) {
        return orderService.getOrderById(id);
    }

    @RequestMapping("getOrderByUserId")
    public String getOrderByUserId(@RequestParam(value = "userId", required = false) Integer userId) {
        return orderService.getOrderByUserId(userId);
    }

//    @RequestMapping("/getOrderDetailList")
//    public String getOrderDetailList(@RequestParam("orderId") Integer orderId) {
//        return orderDetailService.getOrderDetailList(orderId);
//    }
}
