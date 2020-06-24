package cn.easybuy.controller.product;

import cn.easybuy.pojo.Product;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.utils.ImageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 商品详情 类
 * 实现商品详情的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    ProductService productService;

    /**
     * 添加商品详情
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestParam("name")String name,
                      @RequestParam("price")Float price,
                      @RequestParam("stock")Integer stock,
                      @RequestParam(value = "description", required = false)String description,
                      @RequestParam(value = "categoryLevel1Id", required = false)Integer categoryLevel1Id,
                      @RequestParam(value = "categoryLevel2Id", required = false)Integer categoryLevel2Id,
                      @RequestParam(value = "categoryLevel3Id", required = false)Integer categoryLevel3Id,
                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if(file != null) {
            // 保存图片
            ImageUtils.saveImage(file.getInputStream(), null, file.getOriginalFilename());
        }
        // 保存数据库
        Product product = new Product(name, price, stock, description, categoryLevel1Id, categoryLevel2Id, categoryLevel3Id, file == null ? "" : file.getOriginalFilename());
        return productService.add(product);
    }

    /**
     * 根据id删除商品
     * @param id id
     * @return
     */
    @RequestMapping("/deleteProductById")
    public String deleteProductById(@RequestParam("id") Integer id) {
        return productService.deleteProductById(id);
    }

    /**
     * 根据id修改商品
     * @return
     */
    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id,
                      @RequestParam(value = "name", required = false)String name,
                      @RequestParam(value = "price", required = false)Float price,
                      @RequestParam(value = "stock", required = false)Integer stock,
                      @RequestParam(value = "description", required = false)String description,
                      @RequestParam(value = "categoryLevel1Id", required = false)Integer categoryLevel1Id,
                      @RequestParam(value = "categoryLevel2Id", required = false)Integer categoryLevel2Id,
                      @RequestParam(value = "categoryLevel3Id", required = false)Integer categoryLevel3Id,
                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        System.out.println("file是否为空 " + (file == null ? "空" : file.getOriginalFilename()));
        if(file != null) {
            // 保存图片
            ImageUtils.saveImage(file.getInputStream(), null, file.getOriginalFilename());
        }
        // 保存数据库
        Product product = new Product(id, name, description, price, stock, categoryLevel1Id, categoryLevel2Id, categoryLevel3Id, file == null ? "" : file.getOriginalFilename());
        return productService.update(product);
    }

    /**
     * 根据id获得商品
     * @param id id
     * @return
     */
    @RequestMapping("/getProductById")
    public String getProductById(@RequestParam("id") Integer id) {
        return productService.getProductById(id);
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
    @RequestMapping("/getProductList")
    public String getProductList(@RequestParam(value = "from", required = false)Integer from,
                      @RequestParam(value = "pageSize", required = false)Integer pageSize,
                      @RequestParam(value = "proName", required = false)String proName,
                      @RequestParam(value = "categoryId", required = false)Integer categoryId,
                      @RequestParam(value = "level", required = false)Integer level) {
        //Integer from,  Integer pageSize, String proName, Integer categoryId,  Integer level
        return productService.getProductList(from, pageSize, proName, categoryId, level);
    }

    @RequestMapping("/image/{name}")
    public void image(@PathVariable(value = "name", required = false) String name, HttpServletResponse response) throws Exception{
        ImageUtils.image(name, response);
    }

    /**
     * 商品的个数
     * @return
     */
    @RequestMapping("/count")
    public String count() {
        return productService.count();
    }
}
