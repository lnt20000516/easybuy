package cn.easybuy.pojo;

import java.util.Date;
import java.util.List;

public class Order {
    // id
    private Integer id;
    // 订单号
    private String serialNumber;
    // 用户id
    private Integer userId;
    // 收货地址
    private String userAddress;
    // 创建时间
    private String createTime;
    // 订单总计价格
    private Float cost;
    // 登录名称
    private String loginName;

    // 订单明细列表
    private List<OrderDetail> orderDetailList;

    public Order() {}

    public Order(Integer id, String serialNumber, Integer userId, String userAddress, String createTime, Float cost, String loginName, List<OrderDetail> orderDetailList) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.userId = userId;
        this.userAddress = userAddress;
        this.createTime = createTime;
        this.cost = cost;
        this.loginName = loginName;
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", userId=" + userId +
                ", userAddress='" + userAddress + '\'' +
                ", createTime=" + createTime +
                ", cost=" + cost +
                ", loginName='" + loginName + '\'' +
                ", orderDetailList=" + orderDetailList +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
