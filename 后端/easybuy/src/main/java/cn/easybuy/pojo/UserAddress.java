package cn.easybuy.pojo;

import java.util.Date;

public class UserAddress {
    // id
    private Integer id;
    // 地址
    private String address;
    //
    private Integer userId;
    // 创建时间
    private String createTime;
    //
    private String remark;

    public UserAddress() {}

    public UserAddress(Integer id, String address, Integer userId, String createTime, String remark) {
        this.id = id;
        this.address = address;
        this.userId = userId;
        this.createTime = createTime;
        this.remark = remark;
    }

    public UserAddress(String address, Integer userId, String createTime, String remark) {
        this.address = address;
        this.userId = userId;
        this.createTime = createTime;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
