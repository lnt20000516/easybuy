package cn.easybuy.controller;

import cn.easybuy.pojo.User;
import cn.easybuy.service.UserService;
import cn.easybuy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户类
 * 实现用户增删改查的类
 * @author 刘楠婷
 * @time 2020年6月23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 添加一条数据
     * @param loginName
     * @param userName
     * @param password
     * @param sex
     * @param identityCode
     * @param email
     * @param mobile
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestParam("loginName")String loginName,
                      @RequestParam("userName")String userName,
                      @RequestParam("password")String password,
                      @RequestParam("sex")Integer sex,
                      @RequestParam(value = "identityCode", required = false)String identityCode,
                      @RequestParam(value = "email", required = false)String email,
                      @RequestParam(value = "mobile", required = false)String mobile
//                      @RequestParam(value = "type", required = false)Integer type
                      ) {
        return userService.add(new User(loginName, userName, SecurityUtils.md5Hex(password), sex, identityCode, email, mobile));
    }

    /**
     * 检测用户名是否存在
     * @param loginName
     * @return
     */
    @RequestMapping("/checkLoginName")
    public int checkLoginName(@RequestParam("loginName") String loginName) {
        return userService.checkLoginName(loginName);
    }

    /**
     * 检测用户登录是否成功
     * @return
     */
    @RequestMapping("/login")
    public String loginByLoginNameAndPassword(@RequestParam("loginName")String loginName, @RequestParam("password")String password) {
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(SecurityUtils.md5Hex(password));
        System.out.println("进来了" + user);

        return userService.loginByLoginNameAndPassword(user);
    }

    /**
     * 根据id删除用户
     * @param id id
     * @return
     */
    @RequestMapping("/deleteUserById")
    public String deleteUserById(@RequestParam("id")Integer id) {
        return userService.deleteUserById(id);
    }

    /**
     * 获得用户表中的条目数
     * @return
     */
    @RequestMapping("/count")
    public String count() {
        return userService.count();
    }

    /**
     * 获得所有用户的信息
     * @return
     */
    @RequestMapping("/getAll")
    public String getAll() {
        return userService.getAll();
    }

    /**
     * 根据用户的id更新用户信息
     * @return
     */
    @RequestMapping("/updateUserById")
    public String updateUserById(@RequestParam(value = "id", required = false)Integer id,
                                 @RequestParam(value = "loginName", required = false)String loginName,
                                 @RequestParam(value = "userName", required = false)String userName,
                                 @RequestParam(value = "identityCode", required = false)String identityCode,
                                 @RequestParam(value = "email", required = false)String email,
                                 @RequestParam(value = "mobile", required = false)String mobile,
                                 @RequestParam(value = "type", required = false)Integer type
    ) {
        return userService.updateUserById(new User(id, loginName, userName, identityCode, email, mobile, type));
    }

    /**
     * 根据id或login Name获得用户信息
     * @param id id
     * @param loginName 登录名
     * @return
     */
    @RequestMapping("/getUser")
    public String getUser(@RequestParam(value = "id", required = false)Integer id,
                          @RequestParam(value = "loginName", required = false) String loginName) {
        return userService.getUser(id, loginName);
    }

    /**
     * 分页查询
     * @param from 开始的位置
     * @param pageSize 个数
     * @return
     */
    @RequestMapping("/getUserList")
    public String getUserList(@RequestParam("from") Integer from,
                              @RequestParam("pageSize") Integer pageSize) {
        return userService.getUserList(from, pageSize);
    }

}
