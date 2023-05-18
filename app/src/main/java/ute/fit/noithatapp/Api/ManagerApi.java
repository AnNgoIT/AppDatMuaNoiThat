package ute.fit.noithatapp.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ManagerApi {
    @GET("manager/revenue")
    Call<Long> getTotalAll();

    @GET("manager/totalProcessing")
    Call<Integer> getTotalProcessing();

    @GET("manager/totalProcessed")
    Call<Integer> getTotalProcessed();

    @GET("manager/getTotalDay")
    Call<Long> getTotalDay(@Query("day") String day);
}
