package cn.easybuy.controller.order;

import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.service.order.OrderDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 订单详情类
 * 实现用户订单详情的增删改查的类
 * @author 杜煜兴
 * @time 2020年6月22
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Resource
    OrderDetailService orderDetailService;

    /**
     * 添加一条数据
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestParam("orderId") Integer orderId,
                      @RequestParam("productId") Integer productId,
                      @RequestParam("quantity") Integer quantity) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(productId);
        orderDetail.setQuantity(quantity);
        return orderDetailService.add(orderDetail);
    }

    /**
     * 根据id查询订单
     */
    @RequestMapping("/getOrderDetailById")
    public String getOrderDetailById(@RequestParam("id") Integer id) {
        return orderDetailService.getOrderDetailById(id);
    }

    /**
     * 根据订单id获得它下面的所有订单详情
     * @param orderId 订单id（orderId）
     * @return
     */
    @RequestMapping("/getOrderDetailList")
    public String getOrderDetailList(@RequestParam("orderId") Integer orderId) {
        return orderDetailService.getOrderDetailList(orderId);
    }
}
