package cn.easybuy.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * 订单类
 * 实现用户订单的增删改查拼接类
 * @author 杜煜兴
 * @time 2020年6月22
 */
public class OrderProvider {
    public String getOrderByUserId(Integer userId) {
        SQL sql = new SQL().SELECT("*").FROM("easybuy_order");

        if(userId != null) {
            sql.WHERE("userId=#{userId}");
        }
        return sql.toString();
    }
}
