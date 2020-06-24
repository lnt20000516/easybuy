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

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Resource
    ProductCategoryService productCategoryService;

    @RequestMapping("/queryProductCategoryList")
    public String queryProductCategoryList(@RequestParam("from") Integer from,
                                           @RequestParam("pageSize") Integer pageSize) {
        return productCategoryService.queryProductCategoryList(from, pageSize);
    }

    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        return productCategoryService.deleteById(id);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam(value = "name", required = false)String name,
                         @RequestParam(value = "parentId", required = false)Integer parentId,
                         @RequestParam(value = "type", required = false)Integer type) {

        ProductCategory productCategory = new ProductCategory(id, name, parentId, type, null);
        return productCategoryService.update(productCategory);
    }

    @RequestMapping("/queryNameById")
    public String queryNameById(@RequestParam("id") Integer id) {
        return productCategoryService.queryNameById(id);
    }

    @RequestMapping("/queryListByParentId")
    public String queryListByParentId(@RequestParam(value = "parentId", required = false) Integer parentId) {
        return productCategoryService.queryListByParentId(parentId);
    }

    @RequestMapping("/count")
    public String count() {
        return productCategoryService.count();
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "name")String name,
                      @RequestParam(value = "parentId")Integer parentId,
                      @RequestParam(value = "type")Integer type) {
        return productCategoryService.add(new ProductCategory(null, name, parentId, type, null));
    }

    @RequestMapping("/getAll")
    public String getAll() {
        return productCategoryService.getAll();
    }
}
