package ute.fit.noithatapp.Contants;

import com.google.gson.Gson;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private Retrofit retrofit;
    public static RetrofitServer retrofitClient;


    public static RetrofitServer getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitServer();
        }
        return retrofitClient;
    }
//    public RetrofitServer(){
//        initializeRetrofit();
//    }

    public Retrofit initializeRetrofit(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
    public Retrofit getRetrofit(String url){
        return initializeRetrofit(url);
    }
}
