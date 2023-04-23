package ute.fit.noithatapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ute.fit.noithatapp.Activity.Fragment.UserProfileFragment;
import ute.fit.noithatapp.R;

public class SettingActivity extends AppCompatActivity {

    Intent intent;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SettingActivity.this, UserProfileFragment.class);
                startActivity(intent);
            }
        });
    }
}