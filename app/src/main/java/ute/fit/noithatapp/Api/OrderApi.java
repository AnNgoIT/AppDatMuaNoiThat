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

public interface
OrderApi {

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

    @GET("user/getOrderInCartByProductAndUser/{productId}/{userId}")
    Call<OrderModel> getOrderInCartByProductAndUser(@Path("productId")Integer productId,@Path("userId")Integer userId);

    @GET("user/getOrderInCart/{userId}")
    Call<ArrayList<Integer>> getOrderInCartByUser(@Path("userId")Integer userId);

    @GET("user/notificationOrder/{userId}")
    Call<ArrayList<Integer>> getNotificationOrder(@Path("userId")Integer userId);

    @GET("user/productByOrders")
    Call<ArrayList<ProductModel>> getProductByOrder(@Query("listOrder")ArrayList<Integer> orderList);

    @GET("user/getCountOrders")
    Call<ArrayList<Long>> getCountOrder(@Query("listOrder") ArrayList<Integer> orderList);

    @GET("manager/ordersByState")
    Call<ArrayList<OrderModel>> getOrdersByState(@Query("state") String state);
    @GET("manager/getAllOrdersProcessingOrState/{state1}+{state2}")
    Call<ArrayList<OrderModel>> getAllOrdersProcessing(@Path("state1") String state1,@Path("state2") String state2);

    @POST("manager/changeStateOrder/{orderId}")
    Call<Void> changeStateOrder(@Path("orderId") int orderId);
    @DELETE("manager/orderDelete/{orderId}")
    Call<Void> deleteOrder(@Path("orderId") Integer orderId);

    @GET("manager/revenue")
    Call<Long> getTotalRevenue();

    @GET("manager/totalRevenueList")
    Call<ArrayList<Long>> getAllTotalRevenue();
}
