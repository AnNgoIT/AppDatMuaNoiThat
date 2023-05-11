package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.ProductByCategoryAdapter;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductByCategoryActivity extends AppCompatActivity {
    CategoryApi categoryApi;
    RetrofitServer retrofitServer;
    TextView name,price;
    ImageView image;
    private RecyclerView recyclerViewProductList;
    private RecyclerView.Adapter adapter;
    private ArrayList<ProductModel> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_product_by_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().setHomeButtonEnabled(true);
        Init();
        int id = getIntent().getIntExtra("id",1);
        recyclerViewProductList(id);
    }
    public void Init(){
        name=findViewById(R.id.productTitle);
        image = findViewById(R.id.productPic);
        price = findViewById(R.id.productPrice);
        retrofitServer=new RetrofitServer();
        categoryApi=retrofitServer.getRetrofit(ROOT_URL).create(CategoryApi.class);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void recyclerViewProductList(int categoryId){
        recyclerViewProductList = findViewById(R.id.productList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewProductList.setLayoutManager(mLayoutManager);
        //Get API
        categoryApi.getProductByCategory(categoryId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.isSuccessful()){
                    productList=response.body();
                    adapter = new ProductByCategoryAdapter(productList, ProductByCategoryActivity.this);
                    recyclerViewProductList.setAdapter(adapter);
                }else{
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }
}
