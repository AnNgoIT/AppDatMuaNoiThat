package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.OrderAdapter;
import ute.fit.noithatapp.Activity.Adapter.ProductByCategoryAdapter;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class CartActivity extends AppCompatActivity {
    Button btnBack;
    TextView tvTotalPrice;
    RecyclerView recyclerViewOrderList;
    RetrofitServer retrofitServer;
    OrderApi orderApi;
    private OrderAdapter adapter;
    private ArrayList<ProductModel> productList;
    private ArrayList<Long> countList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        setContentView(R.layout.activity_cart);
        btnBack=findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        tvTotalPrice=findViewById(R.id.totalPrice);

        //retrofit
        retrofitServer=new RetrofitServer();
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);

        //recycler view
        recyclerViewOrderList = findViewById(R.id.CartList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewOrderList.setLayoutManager(mLayoutManager);
        //Call api
        int userId=SharedPrefManager.getInstance(this).getUserId();
        orderApi.getProductInCart(userId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.isSuccessful()){
                    productList=response.body();
                    adapter = new OrderAdapter(null,productList, CartActivity.this);
                    orderApi.getCountInCart(userId).enqueue(new Callback<ArrayList<Long>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Long>> call, Response<ArrayList<Long>> response) {
                            if(response.isSuccessful()){
                                countList=response.body();
                                adapter.setCountList(countList);
                                recyclerViewOrderList.setAdapter(adapter);
                                TotalPrice(countList,productList);
                            }else{
                                response.code();
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Long>> call, Throwable t) {

                        }
                    });
                }else{
                    response.code();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

            }
        });
        /*
        orderApi.getCountInCart(SharedPrefManager.getInstance(this).getUserId()).enqueue(new Callback<ArrayList<Long>>() {
            @Override
            public void onResponse(Call<ArrayList<Long>> call, Response<ArrayList<Long>> response) {
                if(response.isSuccessful()){
                    countList=response.body();
                    adapter.setCountList(countList);
                    recyclerViewOrderList.setAdapter(adapter);
                }else{
                    response.code();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Long>> call, Throwable t) {

            }
        });
        */

    }
    public void TotalPrice(ArrayList<Long> mCountList,ArrayList<ProductModel> mProductList){
        Long totalPrice= Long.parseLong("0".toString());
        for(int i=0;i<mCountList.size();i++){
            totalPrice+=mCountList.get(i)*mProductList.get(i).getPrice();
        }
        tvTotalPrice.setText(totalPrice.toString()+" vnd");
    }
}