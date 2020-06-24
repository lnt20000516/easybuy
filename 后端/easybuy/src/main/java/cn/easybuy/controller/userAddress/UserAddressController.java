package cn.easybuy.controller.userAddress;

import cn.easybuy.pojo.UserAddress;
import cn.easybuy.service.userAddress.UserAddressService;
import cn.easybuy.utils.TimeUtils;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Resource
    UserAddressService userAddressService;

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
