package cn.easybuy.service.product;

import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.pojo.Product;
import cn.easybuy.pojo.User;
import cn.easybuy.utils.MyJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情 类
 * 实现商品详情的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@Service
public class ProductService {
    @Resource
    ProductMapper productMapper;

    /**
     * 添加商品详情
     * @param product 商品
     * @return
     */
    public String add(Product product) {
        boolean ok = false;
        MyJsonUtils<Product> json = new MyJsonUtils<>();
        String code, message;

        int t = productMapper.add(product);
        if(t > 0) {
            code = "200";
            message = "SUCCESS";

            ok = true;
        } else {
            code = "500";
            message = "错误!!";
        }

        json.put("code", code);
        json.put("message", message);

        if(ok) {
            //把用户信息添加进去
            List<Product> users = new ArrayList<>();
            users.add(product);
            json.addArray("data", users);
        }
        return json.toString();
    }

    /**
     * 根据id修改商品
     * @param product 商品的一些参数（包括需要修改的商品的id）
     * @return
     */
    public String update(Product product) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productMapper.update(product))
                .toString();
    }

    /**
     * 根据id删除商品
     * @param id id
     * @return
     */
    public String deleteProductById(Integer id) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productMapper.deleteProductById(id))
                .toString();
    }

    /**
     * 根据id获得商品
     * @param id id
     * @return
     */
    public String getProductById(Integer id) {
        List<Product> list = new ArrayList<>();
        list.add(productMapper.getProductById(id));
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

    /**
     * 分页查询,根据关键字模糊查询,级别查询
     * @param from 开始位置
     * @param pageSize 大小
     * @param proName 模糊查询关键字
     * @param categoryId 级别id
     * @param level 级别（1：一级，2：二级，3：三级)
     * @return
     */
    public String getProductList(Integer from,  Integer pageSize, String proName, Integer categoryId,  Integer level) {
        List<Product> list = productMapper.getProductList(from, pageSize, proName, categoryId, level);

        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .put("总个数", productMapper.count())
                .addArray("data", list)
                .toString();
    }

    /**
     * 商品的个数
     * @return
     */
    public String count() {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", productMapper.count())
                .toString();
    }


}
