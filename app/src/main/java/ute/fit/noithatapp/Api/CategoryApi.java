package ute.fit.noithatapp.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.ProductModel;

public interface CategoryApi {
    @FormUrlEncoded
    @GET("/categories")
    Call<ArrayList<CategoryModel>> getCategory();
    @FormUrlEncoded
    @GET("/category/{id}")
    Call<List<ProductModel>> getProductByCategory(@Field("id") String idCategory);
}
