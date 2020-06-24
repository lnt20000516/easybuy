package cn.easybuy.service.order;

import cn.easybuy.dao.order.OrderDetailMapper;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.pojo.Product;
import cn.easybuy.utils.MyJsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * 订单详情类
 * 实现用户订单详情的增删改查的类
 * @author 杜煜兴
 * @time 2020年6月22
 */
@Service
public class OrderDetailService {
    @Resource
    OrderDetailMapper orderDetailMapper;
    @Resource
    ProductMapper productMapper;
    /**
     * 添加一条数据
     * @param orderDetail 订单详情的内容
     * @return
     */
    public String add(OrderDetail orderDetail) {
        Product product = productMapper.getProductById(orderDetail.getProductId());
        // 如果product为null那他在数据库中没有该商品
        if(product == null) {
            return new MyJsonUtils<OrderDetail>()
                    .put("code", 404)
                    .put("message", "商品id未找到")
                    .toString();
        }

        // 计算价格(单价*数量)
        orderDetail.setCost(product.getPrice() * orderDetail.getQuantity());
        // 添加数据库
        int i = orderDetailMapper.add(orderDetail);
        // 放入产品
        orderDetail.setProduct(product);
        // 添加到list中
        List<OrderDetail> list = new ArrayList<>();
        list.add(orderDetail);

        // 返回json
        return new MyJsonUtils<OrderDetail>()
                .put("code", 200)
                .put("message", "受影响条数为" + i)
                .addArray("data", list)
                .toString();
    }

    /**
     * 根据id查询订单
     */
    public String getOrderDetailById(Integer id) {
        List<OrderDetail> list = new ArrayList<>();
        list.add(orderDetailMapper.getOrderDetailById(id));
        return new MyJsonUtils<OrderDetail>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

    /**
     * 根据订单id获得它下面的所有订单详情
     * @param orderId 订单id（orderId）
     * @return
     */
    public String getOrderDetailList(Integer orderId) {
        List<OrderDetail> list = orderDetailMapper.getOrderDetailList(orderId);
        return new MyJsonUtils<OrderDetail>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }

//    public String queryOrderDetailCount(Integer from, Integer pageSize) {
//        List<OrderDetail> list = orderDetailMapper.queryOrderDetailCount(from, pageSize);
//        return new MyJsonUtils<OrderDetail>()
//                .put("code", 200)
//                .put("message", "SUCCESS")
//                .put("length", list.size())
//                .addArray("data", list)
//                .toString();
//    }
}
