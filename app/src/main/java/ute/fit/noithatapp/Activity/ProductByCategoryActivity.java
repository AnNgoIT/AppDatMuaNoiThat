package ute.fit.noithatapp.Activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.ProductByCategoryAdapter;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

public class ProductByCategoryActivity extends AppCompatActivity {
    CategoryApi categoryApi;
    private RecyclerView recyclerViewProductList;
    private RecyclerView.Adapter adapter;
    private List<ProductModel> productList;
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

        String idCategory = getIntent().getStringExtra("idCategory");
        recyclerViewProductList(idCategory);
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
    private void recyclerViewProductList(String idCategory){
        recyclerViewProductList = findViewById(R.id.productList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewProductList.setLayoutManager(mLayoutManager);
        //Get API
        categoryApi.getProductByCategory("1").enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if(response.isSuccessful()){
                    productList=response.body();
                    adapter = new ProductByCategoryAdapter(productList, ProductByCategoryActivity.this);
                    recyclerViewProductList.setAdapter(adapter);
                }else{
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }
}
