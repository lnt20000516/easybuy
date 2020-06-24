package cn.easybuy.provider;

import cn.easybuy.pojo.User;
import cn.easybuy.pojo.UserAddress;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * 用户地址类
 * 实现用户地址的增删改查的拼接类
 * @author 侯志强
 * @time 2020年6月21
 */
public class UserAddressProvider {

    /*
    // 地址
    private String address;
    //
    private Integer userId;
    // 创建时间
    private Date createTime;
    //
    private String remark;
     */
    public String insert(UserAddress userAddress) {
        SQL sql = new SQL().INSERT_INTO("easybuy_user_address");

        // 必传参数
        sql.VALUES("address", "#{userAddress.address}");
        sql.VALUES("userId", "#{userAddress.userId}");


        // 可选参数
        if(StringUtils.hasText(userAddress.getRemark())) {
            sql.VALUES("remark", "#{userAddress.remark}");
        }
        if (StringUtils.hasText(userAddress.getCreateTime())) {
            // 创建时间
            sql.VALUES("createTime", "#{userAddress.createTime}");
        }
        return sql.toString();
    }
}
