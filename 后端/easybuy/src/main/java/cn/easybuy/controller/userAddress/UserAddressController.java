package cn.easybuy.controller.userAddress;

import cn.easybuy.pojo.UserAddress;
import cn.easybuy.service.userAddress.UserAddressService;
import cn.easybuy.utils.TimeUtils;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户地址类
 * 实现用户地址的增删改查的类
 * @author 侯志强
 * @time 2020年6月21
 */
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Resource
    UserAddressService userAddressService;

    /**
     * 添加一条数据
     * @return
     */
    @RequestMapping("/insert")
    public String insert(@RequestParam("address") String address,
                         @RequestParam("userId") Integer userId,
                         @RequestParam(value = "remark", required = false) String remark) {
        return userAddressService.insert(new UserAddress(address, userId, TimeUtils.getNowTime(), remark));
    }

    @RequestMapping("/queryUserAddressListByUserId")
    public String queryUserAddressListByUserId(@RequestParam(value = "userId", required = false) Integer userId) {
        return userAddressService.queryUserAddressListByUserId(userId);
    }
}
