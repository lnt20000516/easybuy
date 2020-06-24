package cn.easybuy.pojo;

/**
 * 购物车类
 * 实现购物车的实体类
 * @author 侯志强
 * @time 2020年6月22
 */
public class ShoppingCart {
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Float price;
    private Integer quantity;

    private Product product;

    public ShoppingCart(Integer productId, Integer userId, Float price, Integer quantity) {
        this.productId = productId;
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
