package cn.easybuy.pojo;

public class MessageBook {
    private Integer id;
    private Integer userId;
    private String title;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String content;

    public MessageBook(Integer userId, String title, String name, String phone, String email, String address, String content) {
        this.userId = userId;
        this.title = title;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
