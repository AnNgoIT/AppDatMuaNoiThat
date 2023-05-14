package ute.fit.noithatapp.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.Model.ProductModel;

public interface OrderApi {

    @FormUrlEncoded
    @POST("/user/addtocart/{userId}/{productId}")
    Call<OrderModel> addToCart(@Path("userId")int userId, @Path("productId")int productId,@Field("count") Long count);

    @GET("user/order/inCart/{id}")
    Call<ArrayList<ProductModel>> getProductInCart(@Path("id")Integer userId);

    @GET("user/order/countProduct/inCart/{id}")
    Call<ArrayList<Long>> getCountInCart(@Path("id")Integer userId);
}
