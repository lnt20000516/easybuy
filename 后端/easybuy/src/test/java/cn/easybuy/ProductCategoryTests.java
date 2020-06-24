package cn.easybuy;

import cn.easybuy.service.product.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ProductCategoryTests {
    @Resource
    ProductCategoryService productCategoryService;

    @Test
    public void getAll() {
        System.out.println(productCategoryService.getAll());
    }
}
