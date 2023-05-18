package ute.fit.noithatapp.Api;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<UserModel> saveUserSetting(@Path("id")int userId, @Field("name") String name, @Field("password") String password,
                                    @Field("address") String address, @Field("address2") String address2, @Field("address3") String address3);

    @GET("/user/{userId}")
    Call<UserModel> getUserByUserId(@Path("userId")int userId);

    @Multipart
    @POST("user/uploadImage/{userId}")
    Call<Void> uploadImage(@Part MultipartBody.Part image, @Path("userId") int userId);
}
