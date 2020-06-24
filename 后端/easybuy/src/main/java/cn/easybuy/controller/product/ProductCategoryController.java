package cn.easybuy.controller.product;

import cn.easybuy.pojo.Product;
import cn.easybuy.pojo.ProductCategory;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.utils.ImageUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 商品分类 类
 * 实现商品分类的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Resource
    ProductCategoryService productCategoryService;

    /**
     * 根据条件查询商品列表
     */
    @RequestMapping("/queryProductCategoryList")
    public String queryProductCategoryList(@RequestParam("from") Integer from,
                                           @RequestParam("pageSize") Integer pageSize) {
        return productCategoryService.queryProductCategoryList(from, pageSize);
    }

    /**
     * 根据id删除商品
     */
    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        return productCategoryService.deleteById(id);
    }

    /**
     * 修改商品分类
     */
    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam(value = "name", required = false)String name,
                         @RequestParam(value = "parentId", required = false)Integer parentId,
                         @RequestParam(value = "type", required = false)Integer type) {

        ProductCategory productCategory = new ProductCategory(id, name, parentId, type, null);
        return productCategoryService.update(productCategory);
    }

    /**
     * 根据id查询名字
     */
    @RequestMapping("/queryNameById")
    public String queryNameById(@RequestParam("id") Integer id) {
        return productCategoryService.queryNameById(id);
    }

    /**
     * 根据parentId查询
     */
    @RequestMapping("/queryListByParentId")
    public String queryListByParentId(@RequestParam(value = "parentId", required = false) Integer parentId) {
        return productCategoryService.queryListByParentId(parentId);
    }

    /**
     * 查询商品分类的条目
     * @return
     */
    @RequestMapping("/count")
    public String count() {
        return productCategoryService.count();
    }

    /**
     * 添加商品分类
     */
    @RequestMapping("/add")
    public String add(@RequestParam(value = "name")String name,
                      @RequestParam(value = "parentId")Integer parentId,
                      @RequestParam(value = "type")Integer type) {
        return productCategoryService.add(new ProductCategory(null, name, parentId, type, null));
    }

    /**
     * 获得所有信息
     * @return
     */
    @RequestMapping("/getAll")
    public String getAll() {
        return productCategoryService.getAll();
    }
}
