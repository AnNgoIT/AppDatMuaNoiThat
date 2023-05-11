package ute.fit.noithatapp.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.ProductModel;

public interface CategoryApi {

    @GET("user/categories")
    Call<ArrayList<CategoryModel>> getCategory();

//    @FormUrlEncoded
    @GET("user/category/{id}")
    Call<ArrayList<ProductModel>> getProductByCategory(@Path("id")int categoryId);
}
