package cn.easybuy.service.product;

import cn.easybuy.dao.product.ProductCategoryMapper;
import cn.easybuy.pojo.Product;
import cn.easybuy.pojo.ProductCategory;
import cn.easybuy.utils.MyJsonUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类 类
 * 实现商品分类的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@Service
public class ProductCategoryService {
    @Resource
    ProductCategoryMapper productCategoryMapper;

    /**
     * 修改商品分类
     */
    public String update(ProductCategory productCategory) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productCategoryMapper.update(productCategory))
                .toString();
    }

    /**
     * 根据id删除商品
     */
    public String deleteById(Integer id) {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "受影响条数" + productCategoryMapper.deleteById(id))
                .toString();
    }

    /**
     * 根据条件查询商品列表
     */
    public String queryProductCategoryList(Integer from, Integer pageSize) {
        List<ProductCategory> list = productCategoryMapper.queryProductCategoryList(from, pageSize);
        return new MyJsonUtils<ProductCategory>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .put("sum", productCategoryMapper.count())
                .addArray("data", list)
                .toString();
    }

    /**
     * 查询商品分类的条目
     * @return
     */
    public String count() {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", productCategoryMapper.count())
                .toString();
    }

    /**
     * 根据id查询名字
     */
    public String queryNameById(Integer id) {
        String name = productCategoryMapper.queryNameById(id);
        System.out.println(name);
        if(name == null) {
            return new MyJsonUtils<ProductCategory>()
                    .put("code", 201)
                    .put("message", "没查到")
                    .toString();
        }
        return new MyJsonUtils<ProductCategory>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("name", name)
                .toString();
    }

    /**
     * 根据parentId查询
     */
    public String queryListByParentId(Integer parentId) {
        List<ProductCategory> list;
        if(parentId == null) {
            list = productCategoryMapper.queryAll();
        } else {
            list = productCategoryMapper.queryListByParentId(parentId);
        }
        return new MyJsonUtils<ProductCategory>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }

    /**
     * 添加商品分类
     */
    public String add(ProductCategory productCategory) {
        productCategoryMapper.add(productCategory);
        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory);
        return new MyJsonUtils<ProductCategory>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .addArray("data", list)
                .toString();
    }

    /**
     * 获得所有信息
     * @return
     */
    public String getAll() {
        JSONObject json = new JSONObject();

        // 获得所有的一级分类
        List<ProductCategory> list1 = productCategoryMapper.queryListByType(1);

        JSONArray array1 = new JSONArray();
        for(ProductCategory t1 : list1) {
            JSONObject json1 = new JSONObject();
            JSONArray array2 = new JSONArray();

            List<ProductCategory> list2 = productCategoryMapper.queryListByParentId(t1.getId());
            for(ProductCategory t2 : list2) {
                JSONObject json2 = new JSONObject();
                JSONArray array3 = new JSONArray();

                // 获得数据
                List<ProductCategory> list3 = productCategoryMapper.queryListByParentId(t2.getId());

                // 第三层查询
                for(ProductCategory t3 : list3) {
                    JSONObject json3 = new JSONObject();
                    json3.put("id", t3.getId());
                    json3.put("name", t3.getName());
                    json3.put("type", 3);
                    array3.add(json3);
                }

                json2.put("id", t2.getId());
                json2.put("name", t2.getName());
                json2.put("type", 2);
                json2.put("children", array3);


                array2.add(json2);
            }

            json1.put("id", t1.getId());
            json1.put("name", t1.getName());
            json1.put("type", 1);
            json1.put("children", array2);

            array1.add(json1);
        }

        json.put("code", 200);
        json.put("message", "SUCCESS");
        json.put("data", array1);

        return json.toString();
    }
}
