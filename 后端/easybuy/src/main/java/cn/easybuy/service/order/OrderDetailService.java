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

@Service
public class OrderDetailService {
    @Resource
    OrderDetailMapper orderDetailMapper;
    @Resource
    ProductMapper productMapper;

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

    public String getOrderDetailById(Integer id) {
        List<OrderDetail> list = new ArrayList<>();
        list.add(orderDetailMapper.getOrderDetailById(id));
        return new MyJsonUtils<OrderDetail>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

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
