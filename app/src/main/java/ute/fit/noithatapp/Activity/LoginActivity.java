package ute.fit.noithatapp.Activity;


<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
=======
import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
>>>>>>> origin/main
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD

import java.util.ArrayList;
import java.util.List;
=======
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> origin/main

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
<<<<<<< HEAD
import ute.fit.noithatapp.Model.CartModel;
=======
>>>>>>> origin/main
import ute.fit.noithatapp.Model.UserModel;
import ute.fit.noithatapp.R;
public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName,editTextPassWord;
    Button loginButton,signupButton;
    UserApi userApi;
    Intent intent;
    UserModel userModel;

    RetrofitServer retrofitServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
=======
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        //
>>>>>>> origin/main
        Init();
        ButtonLogin();
        ButtonSignup();
    }
    public void ButtonLogin(){
        loginButton.setOnClickListener(view -> {
            String user=String.valueOf(editTextUserName.getText());
            String pass=String.valueOf(editTextPassWord.getText());
<<<<<<< HEAD
            userModel=new UserModel(user,pass);
=======
>>>>>>> origin/main
            if (TextUtils.isEmpty(pass)) {
                editTextPassWord.setError("Please enter password");
                editTextPassWord.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(user)) {
                editTextUserName.setError("Please enter username");
                editTextUserName.requestFocus();
                return;
            }
<<<<<<< HEAD
            userApi.getUser(userModel).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(userModel);
                    startActivity(intent);
                    finish();
=======
            userModel=new UserModel(user,pass);
            userApi.getUser(userModel).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response.body() != null){
                        //get user
                        UserModel user;
                        user=response.body();
                        /////////////
                        Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginActivity.this, HomeActivity.class);
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        startActivity(intent);
                        finish();
                    }
>>>>>>> origin/main
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Fail",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    public void ButtonSignup(){
        signupButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        });

    }
    public void Init(){
        loginButton=findViewById(R.id.btnLogin);
        signupButton=findViewById(R.id.btnSignup);
        editTextUserName=findViewById(R.id.UserName);
        editTextPassWord=findViewById(R.id.PassWord);
        retrofitServer=new RetrofitServer();
<<<<<<< HEAD
        userApi=retrofitServer.getRetrofit().create(UserApi.class);
=======
        userApi=retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
>>>>>>> origin/main
    }
}