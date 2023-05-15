package ute.fit.noithatapp.Api;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import ute.fit.noithatapp.Model.UserModel;

public interface UserApi {
    @POST("/user/login")
    Call<UserModel> getUser(@Body UserModel user);

    @POST("/user/signup")
    Call<UserModel> save(@Body UserModel user);


    @GET("user/getUser/{id}")
    Call<UserModel> getUserById(@Path("id")int userId);

    @FormUrlEncoded
    @POST("user/setting/{id}")
    Call<UserModel> saveUserSetting(@Path("id")int userId, @Field("name") String name, @Field("password") String password, @Field("address") String address);

}
