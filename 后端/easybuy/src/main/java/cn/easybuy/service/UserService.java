package cn.easybuy.service;

import cn.easybuy.dao.user.UserMapper;
import cn.easybuy.pojo.User;
import org.springframework.stereotype.Service;
import cn.easybuy.utils.MyJsonUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public String add(User user) {
        boolean ok = false;
        MyJsonUtils<User> json = new MyJsonUtils<>();
        String message;
        int code;

        // 该用户名已经被注册
        if(userMapper.checkLoginName(user.getLoginName()) > 0) {
            code = 500;
            message = "loginName重复";
        } else {// 如果用户没有被注册过
            int t = userMapper.add(user);
            if(t > 0) {
                code = 200;
                message = "SUCCESS";

                ok = true;
            } else {
                code = 500;
                message = "错误!!";
            }
        }

        json.put("code", code);
        json.put("message", message);

        if(ok) {
            //把用户信息添加进去
            List<User> users = new ArrayList<>();
            users.add(user);
            json.addArray("data", users);
        }
        return json.toString();
    }

    public int checkLoginName(String loginName) {
        return userMapper.checkLoginName(loginName);
    }

    public String loginByLoginNameAndPassword(User user) {
        boolean ok = false;
        MyJsonUtils<User> json = new MyJsonUtils<>();
        String message;
        int code;
        List<User> users = null;

        // 该用户名不存在则提示错误
        if(userMapper.checkLoginName(user.getLoginName()) <= 0) {
            code = 500;
            message = "用户名不存在";
        } else {// 存在则检测用户密码
            users = userMapper.loginByLoginNameAndPassword(user);
            if(users.size() > 0) {
                code = 200;
                message = "SUCCESS";

                ok = true;
            } else {
                code = 500;
                message = "用户名或密码错误";
            }
        }

        json.put("code", code);
        json.put("message", message);

        if(ok) {
            //把用户信息添加进去
            json.addArray("data", users);
        }
        return json.toString();
    }


    public String deleteUserById(Integer id) {
        int i = userMapper.deleteUserById(id);
        MyJsonUtils<User> json = new MyJsonUtils<>();
        String message;
        int code;

        if(i > 0) {
            code = 200;
            message = "删除成功，受影响条数为" + i;
        } else if(i == 0){
            code = 201;
            message = "id不存在";
        } else {
            code = 500;
            message = "未知错误";
        }
        json.put("code", code);
        json.put("message", message);
        return json.toString();
    }

    public String count() {
        return new MyJsonUtils<User>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("count", userMapper.count())
                .toString();
    }

    public String getAll(){
        MyJsonUtils<User> json = new MyJsonUtils<>();
        List<User> users = userMapper.getAll();

        json.put("code", 200);
        json.put("message", "SUCCESS");
        json.addArray("data", users);

        return json.toString();
    }

    public String getUser(Integer id, String loginName) {
        if(id == null && loginName == null) {
            return new MyJsonUtils<User>().put("code", 500).put("message", "id和loginName不能全为空").toString();
        }

        MyJsonUtils<User> json = new MyJsonUtils<>();
        User i = userMapper.getUser(id, loginName);
        String message;
        int code;

        if(i != null) {
            code = 200;
            message = "SUCCESS";
        } else {
            code = 500;
            message = "请检查id或loginName";
        }
        json.put("code", code);
        json.put("message", message);
        if(i != null) {
            List<User> list = new ArrayList<>();
            list.add(i);
            json.addArray("data", list);
        }
        return json.toString();
    }

    public String updateUserById(User user) {
        if(user.getId() == null) {
            return new MyJsonUtils<User>().put("code", 500).put("message", "缺少必传参数id").toString();
        }
        User t = userMapper.getUser(user.getId(), null);
        if(t != null && user.getLoginName() != null ){
            // 检测用户名不为原来的而且已经存在
            if(!t.getLoginName().equals(user.getLoginName()) && userMapper.checkLoginName(user.getLoginName()) > 0) {
                return new MyJsonUtils<User>().put("code", 500).put("message", "用户名已经存在").toString();
            }
        }

        int i = userMapper.updateUserById(user);
        MyJsonUtils<User> json = new MyJsonUtils<>();
        String message;
        int code;


        if(i > 0) {
            code = 200;
            message = "修改成功，受影响条数为" + i;
        } else if(i == 0){
            code = 201;
            message = "id不存在";
        } else {
            code = 500;
            message = "未知错误";
        }
        json.put("code", code);
        json.put("message", message);
        return json.toString();
    }

    public String getUserList(Integer from, Integer pageSize) {
        List<User> list = userMapper.getUserList(from, pageSize);

        return new MyJsonUtils<User>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }
}
