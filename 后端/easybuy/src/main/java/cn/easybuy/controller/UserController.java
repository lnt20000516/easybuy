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

    @RequestMapping("/login")
    public String loginByLoginNameAndPassword(@RequestParam("loginName")String loginName, @RequestParam("password")String password) {
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(SecurityUtils.md5Hex(password));
        System.out.println("进来了" + user);

        return userService.loginByLoginNameAndPassword(user);
    }

    @RequestMapping("/deleteUserById")
    public String deleteUserById(@RequestParam("id")Integer id) {
        return userService.deleteUserById(id);
    }

    @RequestMapping("/count")
    public String count() {
        return userService.count();
    }

    @RequestMapping("/getAll")
    public String getAll() {
        return userService.getAll();
    }

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

    @RequestMapping("/getUser")
    public String getUser(@RequestParam(value = "id", required = false)Integer id,
                          @RequestParam(value = "loginName", required = false) String loginName) {
        return userService.getUser(id, loginName);
    }

    @RequestMapping("/getUserList")
    public String getUserList(@RequestParam("from") Integer from,
                              @RequestParam("pageSize") Integer pageSize) {
        return userService.getUserList(from, pageSize);
    }

}
