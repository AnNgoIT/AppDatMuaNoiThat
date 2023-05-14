package ute.fit.noithatapp.Activity;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.UserModel;
import ute.fit.noithatapp.R;

public class SignupActivity extends AppCompatActivity {
    EditText edtUserName,edtPassword,edtName,edtConfirmPassword;
    TextView haveAccount;
    Button signupButton;
    Intent intent;
    UserApi userApi;
    RetrofitServer retrofitServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        //
        Init();
        HaveAccountClick();
        SignUp();
    }
    public void HaveAccountClick(){
        haveAccount.setOnClickListener(view -> {
            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        });
    }
    public void Init(){
        haveAccount=findViewById(R.id.HaveAccount);
        edtConfirmPassword=findViewById(R.id.ConfirmPassWord);
        edtName=findViewById(R.id.Name);
        edtUserName=findViewById(R.id.UserName);
        edtPassword=findViewById(R.id.PassWord);
        signupButton=findViewById(R.id.Signup);
        retrofitServer=new RetrofitServer();
        userApi=retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
    }
    public void SignUp(){
        signupButton.setOnClickListener(view -> {
            String user=String.valueOf(edtUserName.getText());
            String pass=String.valueOf(edtPassword.getText());
            String name=String.valueOf(edtName.getText());
            String confirmPassword=String.valueOf(edtConfirmPassword.getText());
            if (TextUtils.isEmpty(name)) {
                edtName.setError("Please enter name");
                edtName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                edtPassword.setError("Please enter password");
                edtPassword.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(user)) {
                edtUserName.setError("Please enter username");
                edtUserName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                edtConfirmPassword.setError("Please enter confirm password");
                edtConfirmPassword.requestFocus();
                return;
            }
            if(pass.equals(confirmPassword))
            {
                UserModel userModel=new UserModel(name,user,pass);
                userApi.save(userModel).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Toast.makeText(SignupActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(SignupActivity.this,"Fail",Toast.LENGTH_SHORT).show();
                    }
                });


            }
            else {
                Toast.makeText(SignupActivity.this,"please enter confirm password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
