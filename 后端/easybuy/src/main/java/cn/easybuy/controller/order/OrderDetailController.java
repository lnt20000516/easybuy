package cn.easybuy.controller.order;

import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.service.order.OrderDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Resource
    OrderDetailService orderDetailService;

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

    @RequestMapping("/getOrderDetailById")
    public String getOrderDetailById(@RequestParam("id") Integer id) {
        return orderDetailService.getOrderDetailById(id);
    }

    @RequestMapping("/getOrderDetailList")
    public String getOrderDetailList(@RequestParam("orderId") Integer orderId) {
        return orderDetailService.getOrderDetailList(orderId);
    }
}
