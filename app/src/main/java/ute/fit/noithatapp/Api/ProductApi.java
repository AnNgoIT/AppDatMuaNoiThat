package ute.fit.noithatapp.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import ute.fit.noithatapp.Model.ProductModel;

public interface ProductApi {
    @GET("/user/products")
    Call<ArrayList<ProductModel>> getProducts();

}
