package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    EditText editQuantity;
    Button btnBack,inCrease,deCrease,addToCart;
    int productId;
    TextView productDetailName,productDetailPrice,productDetailDes,countProductDetail;
    ImageView productDetailImg;

    RetrofitServer retrofitServer;

    private ProductModel product;
    ProductApi productApi;
    OrderApi orderApi;
    int quantity=1;

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
        Init(productId);
        productDetail(productId);
    }
    public void Init(int productId){
        retrofitServer=new RetrofitServer();
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);
        productDetailName=findViewById(R.id.productDetailName);
        productDetailImg = findViewById(R.id.productDetailImg);
        productDetailPrice = findViewById(R.id.productDetailPrice);
        productDetailDes=findViewById(R.id.productDetailDes);
        countProductDetail=findViewById(R.id.countProductDetail);
        editQuantity=findViewById(R.id.increaseAmount);
        editQuantity.setText(String.valueOf(quantity));
        addToCart=findViewById(R.id.btnAddToCart);
        inCrease=findViewById(R.id.buttonIncrease);
        deCrease=findViewById(R.id.buttonDescrease);
        inCrease.setOnClickListener(view -> {
            quantity+=1;
            editQuantity.setText(String.valueOf(quantity));
        });
        deCrease.setOnClickListener(view -> {
            if (quantity > 1){
                quantity-=1;
                editQuantity.setText(String.valueOf(quantity));
            }
        });
        addToCart.setOnClickListener(view -> {
            int userId= SharedPrefManager.getInstance(this).getUserId();
            Long count=Long.parseLong(editQuantity.getText().toString());
            orderApi.addToCart(userId,productId,count).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    Toast.makeText(ProductDetailActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    Toast.makeText(ProductDetailActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
                }
            });

        });
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
                    countProductDetail.setText("Remaining "+String.valueOf(product.getQuantity()));
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