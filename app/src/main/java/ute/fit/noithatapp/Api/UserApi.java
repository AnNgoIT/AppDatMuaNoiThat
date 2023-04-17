package ute.fit.noithatapp.Api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ute.fit.noithatapp.Model.UserModel;

public interface UserApi {
    @POST("/user/get")
    Call<UserModel> getUser(@Body UserModel user);

    @POST("/user/signup")
    Call<UserModel> save(@Body UserModel user);

}
