package cn.easybuy.provider;

import cn.easybuy.pojo.Product;
import cn.easybuy.pojo.ProductCategory;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class ProductCategoryProvider {
    public String update(ProductCategory productCategory) {
        SQL sql = new SQL().UPDATE("easybuy_product_category");

        sql.WHERE("id=#{productCategory.id}");
        // 可选参数

        // 名字
        if(StringUtils.hasText(productCategory.getName())) {
            sql.SET("name=#{productCategory.name}");
        }
        // parentId
        if(productCategory.getParentId() != null) {
            sql.SET("parentId=#{productCategory.parentId}");
        }
        // 类型
        if(productCategory.getType() != null) {
            sql.SET("type=#{productCategory.type}");
        }
        return sql.toString();
    }
}
