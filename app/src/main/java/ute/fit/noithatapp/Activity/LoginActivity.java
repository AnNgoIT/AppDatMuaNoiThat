package ute.fit.noithatapp.Activity;


import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
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
            if(SharedPrefManager.getInstance(this).getRole().equals("customer")){
                startActivity(new Intent(this, HomeActivity.class));

            }else{
                startActivity(new Intent(this, HomeManagerActivity.class));

            }
        }
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        //
        Init();
        ButtonLogin();
        ButtonSignup();
    }
    public void ButtonLogin(){
        loginButton.setOnClickListener(view -> {
            String user=String.valueOf(editTextUserName.getText());
            String pass=String.valueOf(editTextPassWord.getText());
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
                        if(user.getRole().toString().equals("manager")){
                            intent = new Intent(LoginActivity.this, HomeManagerActivity.class);
                        }
                        else {
                            intent = new Intent(LoginActivity.this, HomeActivity.class);
                        }
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        startActivity(intent);
                        finish();
                    }
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
        userApi=retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
    }
}