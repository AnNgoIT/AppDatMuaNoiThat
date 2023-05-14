package ute.fit.noithatapp.Api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ute.fit.noithatapp.Model.UserModel;

public interface UserApi {
    @POST("/user/login")
    Call<UserModel> getUser(@Body UserModel user);

    @POST("/user/signup")
    Call<UserModel> save(@Body UserModel user);




}
