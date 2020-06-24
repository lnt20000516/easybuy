package cn.easybuy.pojo;

public class ProductCategory {
    // Id
    private Integer id;
    // 名称
    private String name;
    // 父级Id
    private Integer parentId;
    //
    private Integer type;
    //
    private String iconClass;

    private String parentName;

    public ProductCategory() {}

    public ProductCategory(Integer id, String name, Integer parentId, Integer type, String iconClass) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.iconClass = iconClass;
    }



    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                ", iconClass='" + iconClass + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
}

