package ute.fit.noithatapp.Model;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private int id;
    private String name;

    private ArrayList<ProductModel> productModels;

    public CategoryModel(int id, String name, ArrayList<ProductModel> productModels) {
        this.id = id;
        this.name = name;
        this.productModels = productModels;
    }

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
