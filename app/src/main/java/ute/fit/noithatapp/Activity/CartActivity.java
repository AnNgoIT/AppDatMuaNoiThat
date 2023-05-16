package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.OrderAdapter;
import ute.fit.noithatapp.Activity.Adapter.ProductByCategoryAdapter;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class CartActivity extends AppCompatActivity {
    Button btnBack,inCrease,deCrease,btnCheckOut;
    TextView tvTotalPrice;
    RecyclerView recyclerViewOrderList;
    RetrofitServer retrofitServer;
    OrderApi orderApi;
    UserApi userApi;
    ProductApi productApi;
    String[] listAddress;
    String addressSelect;

    Long totalPrice=Long.valueOf("0");
    private OrderAdapter adapter;
    private ArrayList<ProductModel> productList;
    private ArrayList<Long> countList;
    int userId;
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
        userId=SharedPrefManager.getInstance(this).getUserId();
        tvTotalPrice=findViewById(R.id.totalPrice);

        //retrofit
        retrofitServer=new RetrofitServer();
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);
        productApi=retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
        userApi=retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
        //recycler view
        recyclerViewOrderList = findViewById(R.id.CartList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewOrderList.setLayoutManager(mLayoutManager);
        getData();
        //
        Checkout();
    }
    public void TotalPrice(ArrayList<Long> mCountList,ArrayList<ProductModel> mProductList){
        for(int i=0;i<mCountList.size();i++){
            totalPrice+=mCountList.get(i)*mProductList.get(i).getPrice();
        }
        tvTotalPrice.setText(totalPrice.toString()+" VNĐ");
    }
    public void getData(){
        //Call api
         orderApi.getProductInCart(userId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.isSuccessful()){
                    productList=response.body();
                    adapter = new OrderAdapter(new ArrayList<>(), productList, CartActivity.this, new OrderAdapter.IClick() {
                        @Override
                        public void onClickOrderItem(Integer productId, int position) {
                            orderApi.deleteOrderByProductAndUser(productId, userId).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(CartActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Thành công", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }, new OrderAdapter.IClickIncrease() {
                        @Override
                        public void onClickIncrease(Long count, Integer productId) {
                            orderApi.updateCart(userId, productId, count).enqueue(new Callback<OrderModel>() {
                                @Override
                                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {

                                }
                                @Override
                                public void onFailure(Call<OrderModel> call, Throwable t) {
                                }
                            });
                            productApi.getProductById(productId).enqueue(new Callback<ProductModel>() {
                                @Override
                                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                    ProductModel productModel=response.body();
                                    totalPrice+=productModel.getPrice();
                                    tvTotalPrice.setText(totalPrice.toString()+" VNĐ");
                                }

                                @Override
                                public void onFailure(Call<ProductModel> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Xin thử lại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, new OrderAdapter.IClickDecrease() {
                        @Override
                        public void onCLickDecrease(Long count, Integer productId) {
                            orderApi.updateCart(userId, productId, count).enqueue(new Callback<OrderModel>() {
                                @Override
                                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {

                                }

                                @Override
                                public void onFailure(Call<OrderModel> call, Throwable t) {

                                }
                            });
                            productApi.getProductById(productId).enqueue(new Callback<ProductModel>() {
                                @Override
                                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                    ProductModel productModel=response.body();
                                    totalPrice-=productModel.getPrice();
                                    tvTotalPrice.setText(totalPrice.toString()+" VNĐ");
                                }

                                @Override
                                public void onFailure(Call<ProductModel> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Xin thử lại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    });
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

    }
    public void Checkout(){
        //check out
        btnCheckOut=findViewById(R.id.checkout);
        btnCheckOut.setOnClickListener(view -> {
            listAddress=new String[3];
            listAddress=SharedPrefManager.getInstance(this).getUserAddress();

            //dialog select address
            AlertDialog.Builder mBuilder=new AlertDialog.Builder(CartActivity.this);
            mBuilder.setTitle("Select address");
            mBuilder.setIcon(R.drawable.baseline_chair_24);
            mBuilder.setSingleChoiceItems(listAddress, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    addressSelect=listAddress[i];
                    if (!addressSelect.equals("")){
                        orderApi.checkOut(userId,addressSelect).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(CartActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(CartActivity.this, "Xin thử lại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        adapter.clearAll();
                        totalPrice=Long.valueOf("0");
                        tvTotalPrice.setText("0 VNĐ");
                        dialogInterface.dismiss();
                    }
                    else{
                        AlertDialog.Builder mBuilder=new AlertDialog.Builder(CartActivity.this);
                        mBuilder.setTitle("Do you want to create ?");
                        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(CartActivity.this,SettingActivity.class);
                                startActivity(intent);
                            }
                        });
                        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog mDialog=mBuilder.create();
                        mDialog.show();
                    }
                }
            });
            mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog mDialog=mBuilder.create();
            mDialog.show();
        });
    }
}