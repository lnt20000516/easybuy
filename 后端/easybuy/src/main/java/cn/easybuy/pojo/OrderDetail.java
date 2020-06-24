package cn.easybuy.pojo;

public class OrderDetail {
    // id
    private Integer id;
    // 订单id
    private Integer orderId;
    // 商品id
    private Integer productId;
    // 数量
    private Integer quantity;
    // 单价
    private Float cost;

    // 商品
    private Product product;

    public OrderDetail() {}

    public OrderDetail(Integer id, Integer orderId, Integer quantity, Float cost, Integer productId, Product product) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.cost = cost;
        this.productId = productId;
        this.product = product;
    }

    // // orderId,productId,quantity,cost


    public OrderDetail(Integer orderId, Integer productId, Integer quantity, Float cost) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", productId=" + productId +
                ", product=" + product +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
