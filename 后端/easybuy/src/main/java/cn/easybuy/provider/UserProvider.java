package cn.easybuy.provider;

import cn.easybuy.pojo.User;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * 用户类
 * 实现用户增删改查的拼接类
 * @author 刘楠婷
 * @time 2020年6月23
 */
public class UserProvider {
    public String insertUserByParam(User user) {
        SQL sql = new SQL().INSERT_INTO("easybuy_user");
        // 必传参数
        sql.VALUES("loginName", "#{user.loginName}");
        sql.VALUES("userName", "#{user.userName}");
        sql.VALUES("password", "#{user.password}");
        sql.VALUES("sex", "#{user.sex}");

        // 可选参数

        // 身份证
        if(StringUtils.hasText(user.getIdentityCode())) {
            sql.VALUES("identityCode", "#{user.identityCode}");
        }
        // 邮箱
        if(StringUtils.hasText(user.getEmail())) {
            sql.VALUES("email", "#{user.email}");
        }
        // 手机
        if(StringUtils.hasText(user.getMobile())) {
            sql.VALUES("mobile", "#{user.mobile}");
        }
        return sql.toString();
    }

    public String updateUserById(User user) {
        SQL sql = new SQL().UPDATE("easybuy_user");

        // 条件
        sql.WHERE("id=#{user.id}");

        // 可选参数

        /**
         @RequestParam(value = "id")Integer id,
         @RequestParam(value = "loginName", required = false)String loginName,
         @RequestParam(value = "userName", required = false)String userName,
         @RequestParam(value = "identityCode", required = false)String identityCode,
         @RequestParam(value = "email", required = false)String email,
         @RequestParam(value = "mobile", required = false)String mobile,
         @RequestParam(value = "type", required = false)Integer type
         */

        // 用户名
        if(StringUtils.hasText(user.getLoginName())) {
            sql.SET("loginName=#{user.loginName}");
        }
        // 真实姓名
        if(StringUtils.hasText(user.getUserName())) {
            sql.SET("userName=#{user.userName}");
        }
        // 身份证
        if(StringUtils.hasText(user.getIdentityCode())) {
            sql.SET("identityCode=#{user.identityCode}");
        }
        // 邮箱
        if(StringUtils.hasText(user.getEmail())) {
            sql.SET("email=#{user.email}");
        }
        // 手机
        if(StringUtils.hasText(user.getMobile())) {
            sql.SET("mobile=#{user.mobile}");
        }
        // 类型
        if(user.getType() != null) {
            sql.SET("type=#{user.type}");
        }
        return sql.toString();
    }

    public String getUser(Integer id, String loginName) {
        SQL sql = new SQL().SELECT("*").FROM("easybuy_user");
        // 可选参数

        // id
        if(id != null) {
            sql.WHERE("id=#{id}");
        } else if(StringUtils.hasText(loginName)) {//用户名
            sql.WHERE("loginName=#{loginName}");
        }
        return sql.toString();
    }
}
