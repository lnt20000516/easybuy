package cn.easybuy.dao.product;

import cn.easybuy.pojo.Product;
import cn.easybuy.provider.ProductProvider;
import cn.easybuy.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface ProductMapper {
    Integer updateStock(Integer id, Integer quantity) ;

    @InsertProvider(type = ProductProvider.class, method = "insertProduct")
    @Options(useGeneratedKeys = true, keyProperty = "product.id")
    Integer add(@Param("product") Product product) ;


    @UpdateProvider(value = ProductProvider.class, method = "update")
    Integer update(@Param("product") Product product) ;

    @Delete("delete from easybuy_product where id=#{id}")
    Integer deleteProductById(@Param("id") Integer id) ;

    @Select("select * from easybuy_product where id=#{id}")
    @ResultType(Product.class)
    Product getProductById(@Param("id") Integer id);

    @SelectProvider(value = ProductProvider.class, method = "getProductList")
    List<Product> getProductList(@Param("from") Integer from, @Param("pageSize") Integer pageSize,
                                 @Param("proName") String proName, @Param("categoryId") Integer categoryId,
                                 @Param("level") Integer level);


    @Select("select count(1) from easybuy_product")
    int count();

    @Select("")
    Integer queryProductCount(String proName, Integer categoryId, Integer level);
}
