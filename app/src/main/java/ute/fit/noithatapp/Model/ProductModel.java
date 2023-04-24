package ute.fit.noithatapp.Model;

public class ProductModel {
    private int productId;
    private String name;
    private Long price;
    private CategoryModel category;
    private long quantity;
    private CartItemModel cartItem;
    private String image;

    public ProductModel(int productId, String name, Long price, long quantity, CartItemModel cartItem, String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cartItem = cartItem;
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public CartItemModel getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItemModel cartItem) {
        this.cartItem = cartItem;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
