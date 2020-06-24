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

@Service
public class ProductService {
    @Resource
    ProductMapper productMapper;

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

    public String update(Product product) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productMapper.update(product))
                .toString();
    }

    public String deleteProductById(Integer id) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productMapper.deleteProductById(id))
                .toString();
    }

    public String getProductById(Integer id) {
        List<Product> list = new ArrayList<>();
        list.add(productMapper.getProductById(id));
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

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

    public String count() {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", productMapper.count())
                .toString();
    }


}
