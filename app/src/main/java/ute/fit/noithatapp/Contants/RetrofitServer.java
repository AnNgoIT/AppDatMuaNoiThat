package ute.fit.noithatapp.Contants;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private Retrofit retrofit;
<<<<<<< HEAD
    public RetrofitServer(){
        initializeRetrofit();
    }
    public void initializeRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl("http://172.28.240.1:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
=======
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
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(String url){
        return initializeRetrofit(url);
>>>>>>> origin/main
    }
}
