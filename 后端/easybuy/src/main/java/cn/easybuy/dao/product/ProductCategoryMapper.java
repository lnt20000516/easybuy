package cn.easybuy.dao.product;

import cn.easybuy.pojo.ProductCategory;
import cn.easybuy.provider.ProductCategoryProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品分类 类
 * 实现商品分类的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@Mapper
@Component
public interface ProductCategoryMapper {
    /**
     * 根据id删除商品
     */
    @Delete("delete from easybuy_product_category where id=#{id}")
    int deleteById(@Param("id") Integer id);

    /**
     * 根据条件查询商品列表
     */
    @Select("select * from easybuy_product_category limit #{from}, #{pageSize}")
    List<ProductCategory> queryProductCategoryList(@Param("from") Integer from,
                                                   @Param("pageSize") Integer pageSize);

    /**
     * 查询商品分类的条目
     * @return
     */
    @Select("select count(1) from easybuy_product_category")
    int count();

    /**
     * 根据id查询商品分类
     */
    ProductCategory queryProductCategoryById(Integer id);

    /**
     * 根据id查询名字
     */
    @Select("select name from easybuy_product_category where id = #{id}")
    String queryNameById(Integer id);



    /**
     * 根据parentId查询
     */
    @Select("select * from easybuy_product_category where parentId = #{parentId}")
    List<ProductCategory> queryListByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据type查询
     */
    @Select("select * from easybuy_product_category where type = #{type}")
    List<ProductCategory> queryListByType(@Param("type") Integer type);

    /**
     * 查询全部信息
     */
    @Select("select * from easybuy_product_category")
    List<ProductCategory> queryAll();

    /**
     * 添加商品分类
     */
    @Insert("insert into easybuy_product_category(name, parentId, type) values(#{productCategory.name}, " +
            "#{productCategory.parentId}, #{productCategory.type})")
    @Options(useGeneratedKeys = true, keyProperty = "productCategory.id")
    Integer add(@Param("productCategory") ProductCategory productCategory);

    /**
     * 根据参数查询商品分类的数目
     */
    @Select("select count(1) from easybuy_product_category")
    Integer queryProductCategoryCount();

    /**
     * 修改商品分类
     */
    @UpdateProvider(value = ProductCategoryProvider.class, method = "update")
    int update(@Param("productCategory") ProductCategory productCategory);
}
