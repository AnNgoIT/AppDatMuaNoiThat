package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Fragment.HomeFragment;
import ute.fit.noithatapp.Activity.Fragment.UserProfileFragment;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.UserModel;
import ute.fit.noithatapp.R;

public class SettingActivity extends AppCompatActivity {

    Intent intent;

    ImageView back;

    EditText name, address, password;

    Button save;

    int userId;

    String userName, userPassword, userAddress;

    RetrofitServer retrofitServer;

    private UserModel user;

    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        setContentView(R.layout.activity_setting);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userId = SharedPrefManager.getInstance(this).getUserId();

        Init();
        userProfile(userId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || password.getText().toString().equals("") || address.getText().toString().equals("")){
                    Toast.makeText(SettingActivity.this,"Vui lòng nhập đầy đủ thông tin!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    userApi.saveUserSetting(userId, name.getText().toString(), password.getText().toString(), address.getText().toString()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            try{
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(SettingActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                }else{
                                    response.code();
                                }
                            }
                            catch (Exception e) {
                                Toast.makeText(SettingActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Log.d("logg", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void  Init(){
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        retrofitServer = new RetrofitServer();
        userApi = retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
    }

    private void userProfile(int userId){
        userApi.getUserById(userId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    user = response.body();
                    name.setText((user.getName()));
                    address.setText(user.getAddress());
                    password.setText(user.getPassword());

                }else{
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}