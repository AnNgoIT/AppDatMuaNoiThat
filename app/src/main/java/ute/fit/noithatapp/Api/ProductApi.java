package ute.fit.noithatapp.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import ute.fit.noithatapp.Model.ProductModel;

public interface ProductApi {
    @GET("user/products")
    Call<ArrayList<ProductModel>> getProducts();

    @GET("user/product/2")
    Call<ProductModel> getProductById();
}
