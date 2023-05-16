package ute.fit.noithatapp.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
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
    @FormUrlEncoded
    @POST("/user/updateCart/{userId}/{productId}")
    Call<OrderModel> updateCart(@Path("userId")int userId, @Path("productId")int productId,@Field("count") Long count);

    @GET("user/order/inCart/{id}")
    Call<ArrayList<ProductModel>> getProductInCart(@Path("id")Integer userId);

    @GET("user/order/countProduct/inCart/{id}")
    Call<ArrayList<Long>> getCountInCart(@Path("id")Integer userId);

    @DELETE("user/orderDelete/{productId}/{userId}")
    Call<Void> deleteOrderByProductAndUser(@Path("productId")Integer productId,@Path("userId")Integer userId);

    @FormUrlEncoded
    @POST("user/paying/{userId}")
    Call<Void> checkOut(@Path("userId")Integer userId,@Field("address") String address);

    @GET("user/order/processing/{id}")
    Call<ArrayList<ProductModel>> getProductProcessing(@Path("id")Integer userId);

    @GET("user/order/countProduct/processing/{id}")
    Call<ArrayList<Long>> getCountProcessing(@Path("id")Integer userId);


}
