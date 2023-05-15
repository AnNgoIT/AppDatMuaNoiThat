package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    Button btnBack;
    int productId;
    TextView productDetailName,productDetailPrice,productDetailDes;
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
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        btnBack=findViewById(R.id.buttonBackProductDetail);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            productId = bundle.getInt("productId", 0);
        }
        Init();
        productDetail(productId);
    }
    public void Init(){
        productDetailName=findViewById(R.id.productDetailName);
        productDetailImg = findViewById(R.id.productDetailImg);
        productDetailPrice = findViewById(R.id.productDetailPrice);
        productDetailDes=findViewById(R.id.productDetailDes);
        retrofitServer=new RetrofitServer();
        productApi=retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
    }

    private void productDetail(int productId){
        productApi.getProductById(productId).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.isSuccessful()){
                    product=response.body();
                    productDetailName.setText(product.getName());
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String price = formatter.format(product.getPrice());
                    productDetailPrice.setText(price + " VNĐ");
                    productDetailDes.setText(product.getDescription());
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