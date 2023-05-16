package ute.fit.noithatapp.Api;

import android.app.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ute.fit.noithatapp.Model.NotificationModel;
import ute.fit.noithatapp.Model.OrderModel;

public interface NotificationApi {
    @GET("user/notification/{userId}")
    Call<ArrayList<NotificationModel>> getNotificationByUser(@Path("userId")Integer userId);

    @FormUrlEncoded
    @POST("user/saveNotification/{userId}")
    Call<Void> saveNotification(@Path("userId")Integer userId, @Field("description")String description, @Field("orderId") Integer orderId,@Field("state")String state);

    @GET("user/notificationOrder/{userId}")
    Call<OrderModel> getOrderByNotification(@Path("userId")Integer userId);

    @POST("user/saveNotificationHide/{notificationId}")
    Call<Void> saveNotificationHide(@Path("notificationId") Integer notificationId);
}
