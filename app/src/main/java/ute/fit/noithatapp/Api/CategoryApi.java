package ute.fit.noithatapp.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import ute.fit.noithatapp.Model.CategoryModel;

public interface CategoryApi {
    @GET("/user/categories")
    Call<ArrayList<CategoryModel>> getCategory();
}
