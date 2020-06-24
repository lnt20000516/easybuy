package cn.easybuy.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class OrderProvider {
    public String getOrderByUserId(Integer userId) {
        SQL sql = new SQL().SELECT("*").FROM("easybuy_order");

        if(userId != null) {
            sql.WHERE("userId=#{userId}");
        }
        return sql.toString();
    }
}
