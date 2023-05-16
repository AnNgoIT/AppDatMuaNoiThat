package ute.fit.noithatapp.Contants;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import ute.fit.noithatapp.Activity.LoginActivity;
import ute.fit.noithatapp.Model.UserModel;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "retrofitregisterlogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASSWORD="keypassword";

    private static final String KEY_USER="userid";
    private static final String KEY_ADDRESS1="address1";

    private static final String KEY_ADDRESS2="address2";

    private static final String KEY_ADDRESS3="address3";
    private static final String KEY_ROLE="role";

    private static SharedPrefManager instance;
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized  SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }
    //this method will store the user data in shared preferences
    public void userLogin (UserModel user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putInt(KEY_USER,user.getId());
        editor.putString(KEY_ADDRESS1,user.getAddress());
        editor.putString(KEY_ADDRESS2,user.getAddress2());
        editor.putString(KEY_ADDRESS3,user.getAddress3());
        editor.putString(KEY_ROLE,user.getRole());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) != null;
    }
    //this method will give the logged in user
    public UserModel getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserModel(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD,null
                )
        );
    }
    public int getUserId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER,0);
    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ctx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
    public String[] getUserAddress(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String[] listUserAddress=new String[3];
        listUserAddress[0]=sharedPreferences.getString(KEY_ADDRESS1,null);
        listUserAddress[1]=sharedPreferences.getString(KEY_ADDRESS2,null);
        listUserAddress[2]=sharedPreferences.getString(KEY_ADDRESS3,null);
        return listUserAddress;
    }

    public String getRole(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE,null);
    }


}