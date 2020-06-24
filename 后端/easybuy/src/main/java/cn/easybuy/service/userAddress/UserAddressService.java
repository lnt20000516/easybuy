package cn.easybuy.service.userAddress;

import cn.easybuy.dao.order.UserAddressMapper;
import cn.easybuy.pojo.UserAddress;
import cn.easybuy.utils.MyJsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户地址类
 * 实现用户地址的增删改查的类
 * @author 侯志强
 * @time 2020年6月21
 */
@Service
public class UserAddressService {
    @Resource
    UserAddressMapper userAddressMapper;

    /**
     * 添加一条数据
     * @param userAddress 用户的地址详情
     * @return
     */
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

    /**
     * 根据用户id查询，不传查询所有用户的地址
     * @return
     */
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
