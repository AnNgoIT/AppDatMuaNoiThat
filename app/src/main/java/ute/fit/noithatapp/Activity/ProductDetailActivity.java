package ute.fit.noithatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import ute.fit.noithatapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    Button btnBack;
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int productId = bundle.getInt("productId", 0);
            System.out.println(productId);
        }
    }
}