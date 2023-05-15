package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.OrderProcessingAdapter;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class OrderActivity extends AppCompatActivity {
    RetrofitServer retrofitServer;
    OrderApi orderApi;
    ProductApi productApi;
    RecyclerView recyclerViewOrderList;
    Button btnBack;
    int userId;
    private OrderProcessingAdapter orderProcessingAdapter;
    private ArrayList<ProductModel> productList;
    private ArrayList<Long> countList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        setContentView(R.layout.activity_order);
        //btn back
        btnBack=findViewById(R.id.buttonBackOrder);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        //retrofit
        retrofitServer=new RetrofitServer();
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);
        productApi=retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
        //userId
        userId= SharedPrefManager.getInstance(this).getUserId();
        //recycler view
        recyclerViewOrderList = findViewById(R.id.OrderList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewOrderList.setLayoutManager(mLayoutManager);
        getData();
    }
    public void getData(){
        orderApi.getProductProcessing(userId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                productList=response.body();
                orderProcessingAdapter = new OrderProcessingAdapter(productList,new ArrayList<>(),OrderActivity.this);

            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

            }
        });
        orderApi.getCountProcessing(userId).enqueue(new Callback<ArrayList<Long>>() {
            @Override
            public void onResponse(Call<ArrayList<Long>> call, Response<ArrayList<Long>> response) {
                if(response.isSuccessful()){
                    countList=response.body();
                    orderProcessingAdapter.setCountOrderList(countList);
                    recyclerViewOrderList.setAdapter(orderProcessingAdapter);
                }else{
                    response.code();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Long>> call, Throwable t) {

            }
        });
    }
}