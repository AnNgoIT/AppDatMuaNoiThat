package ute.fit.noithatapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel implements Serializable {
    private int categoryId;
    private String name;

    private ArrayList<ProductModel> product;
<<<<<<< HEAD
=======
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryModel(int categoryId, String name, ArrayList<ProductModel> product, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.product = product;
        this.image = image;
    }
>>>>>>> origin/main

    public CategoryModel(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public CategoryModel(int categoryId, String name, ArrayList<ProductModel> product) {
        this.categoryId = categoryId;
        this.name = name;
        this.product = product;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductModel> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductModel> product) {
        this.product = product;
    }
}
