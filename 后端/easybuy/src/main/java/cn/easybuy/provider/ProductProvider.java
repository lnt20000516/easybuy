package cn.easybuy.provider;

import cn.easybuy.pojo.Product;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * 商品详情 类
 * 实现商品详情的增删改查的拼接类
 * @author 高骏
 * @time 2020年6月21
 */
public class ProductProvider {
    public String insertProduct(Product product) {
        SQL sql = new SQL().INSERT_INTO("easybuy_product");
        // 必传参数
        sql.VALUES("name", "#{product.name}");// 产品名
        sql.VALUES("price", "#{product.price}");// 价格
        sql.VALUES("stock", "#{product.stock}");// 数量

        // 可选参数

        // 描述
        if(StringUtils.hasText(product.getDescription())) {
            sql.VALUES("description", "#{product.description}");
        }
        // 分类1
        if(product.getCategoryLevel1Id() != null) {
            sql.VALUES("categoryLevel1Id", "#{product.categoryLevel1Id}");
        }
        // 分类2
        if(product.getCategoryLevel2Id() != null) {
            sql.VALUES("categoryLevel2Id", "#{product.categoryLevel2Id}");
        }
        // 分类3
        if(product.getCategoryLevel3Id() != null) {
            sql.VALUES("categoryLevel3Id", "#{product.categoryLevel3Id}");
        }
        if(StringUtils.hasText(product.getFileName())) {
            sql.VALUES("fileName", "#{product.fileName}");
        }
        return sql.toString();
    }

    public String getProductList(Integer from, Integer pageSize, String proName, Integer categoryId, Integer level) {
        SQL sql = new SQL().SELECT("*").FROM("easybuy_product");
        /**
         *
         select a.name, a.price, a.stock, b.name as 'abc' from easybuy_product as a
         LEFT JOIN easybuy_product_category as b on a.categoryLevel1Id =  b.parentId
         where a.name like '%水%' LIMIT 1
         */
        if(StringUtils.hasText(proName)) {
            sql.WHERE("name like concat('%', #{proName}, '%')");
        }
        if(level != null) {
            if(level == 1) {
                sql.WHERE("categoryLevel1Id =  #{categoryId}");
            } else if(level == 2) {
                sql.WHERE("categoryLevel2Id =  #{categoryId}");
            } else if(level == 3) {
                sql.WHERE("categoryLevel3Id =  #{categoryId}");
            }
        }
        if(from != null && pageSize != null) {
            sql.LIMIT("#{from},#{pageSize}");
        }
        return sql.toString();
    }

    public String update(Product product) {
        SQL sql = new SQL().UPDATE("easybuy_product");

        sql.WHERE("id=#{product.id}");
        // 可选参数

        // 描述
        if(StringUtils.hasText(product.getName())) {
            sql.SET("name=#{product.name}");
        }
        // 描述
        if(product.getPrice() != null) {
            sql.SET("price=#{product.price}");
        }
        // 描述
        if(product.getStock() != null) {
            sql.SET("stock=#{product.stock}");
        }
        // 描述
        if(StringUtils.hasText(product.getDescription())) {
            sql.SET("description=#{product.description}");
        }
        // 分类1
        if(product.getCategoryLevel1Id() != null) {
            sql.SET("categoryLevel1Id=#{product.categoryLevel1Id}");
        }
        // 分类2
        if(product.getCategoryLevel2Id() != null) {
            sql.SET("categoryLevel2Id=#{product.categoryLevel2Id}");
        }
        // 分类3
        if(product.getCategoryLevel3Id() != null) {
            sql.SET("categoryLevel3Id=#{product.categoryLevel3Id}");
        }
        // 文件名
        if(StringUtils.hasText(product.getFileName())) {
            sql.SET("fileName=#{product.fileName}");
        }
        return sql.toString();
    }

//    public String queryProductCount(String proName, Integer categoryId, Integer level) {
//        SQL sql = new SQL().SELECT("count(1)").FROM("easybuy_product");
//        if(StringUtils.hasText(proName)) {
//            sql.WHERE("proName like concat()")
//        }
//    }

    public static void main(String[] args) {
        System.out.println(new ProductProvider().getProductList(5, 100, "哈哈哈", 25, 1));
    }
}
