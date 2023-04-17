package ute.fit.noithatapp.Contants;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private Retrofit retrofit;
    public RetrofitServer(){
        initializeRetrofit();
    }
    public void initializeRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.11:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
