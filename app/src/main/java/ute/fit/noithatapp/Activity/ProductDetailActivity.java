package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    Button btnBack;
    TextView productDetailName,productDetailPrice;
    ImageView productDetailImg;

    RetrofitServer retrofitServer;

    private ProductModel product;
    ProductApi productApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        setContentView(R.layout.activity_product_detail);
        btnBack=findViewById(R.id.buttonBackProductDetail);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        Init();
        int id = getIntent().getIntExtra("id",1);
        productDetail(id);
    }
    public void Init(){
        productDetailName=findViewById(R.id.productDetailName);
        productDetailImg = findViewById(R.id.productDetailImg);
        productDetailPrice = findViewById(R.id.productDetailPrice);
        retrofitServer=new RetrofitServer();
        productApi=retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
    }

    private void productDetail(int id){
        productApi.getProductById().enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.isSuccessful()){
                    product=response.body();
                    productDetailName.setText(product.getName());
                    productDetailPrice.setText(product.getPrice().toString());
                    Glide.with(getApplicationContext()).load(product.getImage()).into(productDetailImg);
                }else{
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }
}