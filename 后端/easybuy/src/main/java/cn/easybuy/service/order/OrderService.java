package cn.easybuy.service.order;

import cn.easybuy.dao.order.OrderDetailMapper;
import cn.easybuy.dao.order.OrderMapper;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.pojo.Order;
import cn.easybuy.pojo.OrderDetail;
import cn.easybuy.pojo.Product;
import cn.easybuy.utils.MyJsonUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderDetailMapper orderDetailMapper;

    /**
     * 添加一条数据
     * @param order 需要插入的信息
     * @return
     */
    public String add(Order order) {
        int i = orderMapper.add(order);
        // 添加到list中
        List<Order> list = new ArrayList<>();
        list.add(order);

        // 返回json
        return new MyJsonUtils<Order>()
                .put("code", 200)
                .put("message", "受影响条数为" + i)
                .addArray("data", list)
                .toString();
    }

    /**
     * 根据id获得order
     * @param id easybuy_order中的主键id
     * @return
     */
    public String getOrderById(Integer id) {
        List<Order> list = new ArrayList<>();
        Order order = orderMapper.getOrderById(id);
        order.setOrderDetailList(orderDetailMapper.getOrderDetailList(order.getId()));

        list.add(order);
        return new MyJsonUtils<Order>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

    /**
     * 根据用户id获得用户的订单，不传返回全部
     * @param userId 用户id
     * @return
     */
    public String getOrderByUserId(Integer userId) {
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        MyJsonUtils<Order> json = new MyJsonUtils<>();

        for(Order order : orders) {
            order.setOrderDetailList(orderDetailMapper.getOrderDetailList(order.getId()));
        }
//        JSONArray arr = new JSONArray();
//        for (Order order : orders) {
//            JSONObject t = new JSONObject();
//            t.put("id", order.getId());
//            t.put("userId", order.getUserId());
//            t.put("userAddress", order.getUserAddress());
//            t.put("serialNumber", order.getSerialNumber());
//
//            List<OrderDetail> o1 = orderDetailMapper.getOrderDetailList(order.getId());
//            // 把每则订单添加进arr中
//            JSONArray arr2 = new JSONArray();
//            for (OrderDetail detail : o1) {
//                JSONObject object1 = new JSONObject();
//                object1.put("id", detail.getId());
//                object1.put("quantity", detail.getQuantity());
//
//                Product p = detail.getProduct();
//                JSONObject product = new JSONObject();
//                product.put("id", p.getId());
//                product.put("name", p.getName());
//                product.put("fileName", p.getFileName());
//
//                object1.put("product", product);
//                arr2.add(object1);
//            }
//            t.put("order", arr2);
//            arr.add(t);
//        }

        return json.put("code", 200)
                .put("message", "SUCCESS")
                .put("length", orders.size())
                .addArray("data", orders)
                .toString();
    }

//    public String getOrderDetailList(Integer orderId) {
//        List<OrderDetail> list = orderDetailMapper.getOrderDetailList(orderId);
//        return new MyJsonUtils<OrderDetail>()
//                .put("code", 200)
//                .put("message", "SUCCESS")
//                .put("length", list.size())
//                .addArray("data", list)
//                .toString();
//    }

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
