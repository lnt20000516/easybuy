package cn.easybuy.service.userAddress;

import cn.easybuy.dao.order.UserAddressMapper;
import cn.easybuy.pojo.UserAddress;
import cn.easybuy.utils.MyJsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAddressService {
    @Resource
    UserAddressMapper userAddressMapper;

    public String insert(UserAddress userAddress) {
        int i = userAddressMapper.add(userAddress);
        List<UserAddress> list = new ArrayList<>();
        list.add(userAddress);

        return new MyJsonUtils<UserAddress>()
                .put("code", 200)
                .put("message", "受影响条数" + i)
                .addArray("data", list)
                .toString();
    }

    public String queryUserAddressListByUserId(Integer id) {
        List<UserAddress> list;
        if(id != null) {
            list = userAddressMapper.queryUserAddressListByUserId(id);
        } else {
            list = userAddressMapper.queryUserAddressList();
        }

        return new MyJsonUtils<UserAddress>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }
}
