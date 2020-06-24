package cn.easybuy.dao.product;

import cn.easybuy.pojo.Product;
import cn.easybuy.provider.ProductProvider;
import cn.easybuy.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品详情 类
 * 实现商品详情的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@Mapper
@Component
public interface ProductMapper {
//    Integer updateStock(Integer id, Integer quantity) ;

    /**
     * 添加商品详情
     * @param product 商品
     * @return
     */
    @InsertProvider(type = ProductProvider.class, method = "insertProduct")
    @Options(useGeneratedKeys = true, keyProperty = "product.id")
    Integer add(@Param("product") Product product) ;


    /**
     * 根据id修改商品
     * @param product 商品的一些参数（包括需要修改的商品的id）
     * @return
     */
    @UpdateProvider(value = ProductProvider.class, method = "update")
    Integer update(@Param("product") Product product) ;

    /**
     * 根据id删除商品
     * @param id id
     * @return
     */
    @Delete("delete from easybuy_product where id=#{id}")
    Integer deleteProductById(@Param("id") Integer id) ;

    /**
     * 根据id获得商品
     * @param id id
     * @return
     */
    @Select("select * from easybuy_product where id=#{id}")
    @ResultType(Product.class)
    Product getProductById(@Param("id") Integer id);

    /**
     * 分页查询,根据关键字模糊查询,级别查询
     * @param from 开始位置
     * @param pageSize 大小
     * @param proName 模糊查询关键字
     * @param categoryId 级别id
     * @param level 级别（1：一级，2：二级，3：三级)
     * @return
     */
    @SelectProvider(value = ProductProvider.class, method = "getProductList")
    List<Product> getProductList(@Param("from") Integer from, @Param("pageSize") Integer pageSize,
                                 @Param("proName") String proName, @Param("categoryId") Integer categoryId,
                                 @Param("level") Integer level);


    /**
     * 商品的个数
     * @return
     */
    @Select("select count(1) from easybuy_product")
    int count();

//    @Select("")
//    Integer queryProductCount(String proName, Integer categoryId, Integer level);
}
