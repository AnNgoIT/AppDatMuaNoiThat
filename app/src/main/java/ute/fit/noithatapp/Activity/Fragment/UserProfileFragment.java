package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;





import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.LoginActivity;
import ute.fit.noithatapp.Activity.OrderActivity;
import ute.fit.noithatapp.Activity.SettingActivity;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;

import ute.fit.noithatapp.Model.UserModel;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    public Activity activity= getActivity();
    RelativeLayout setting,myOrder;

    Intent intent;

    View mView;

    ImageView logout;
    TextView tvName;
    ImageView userImg;
    private UserApi userApi;
    private RetrofitServer retrofitServer;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_user_profile, container, false);
        //loggout
        logout = mView.findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            exit(view);
        });
        setting = mView.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        //Profile
        tvName = mView.findViewById(R.id.tvName);
        userImg = mView.findViewById(R.id.userImg);
        retrofitServer = new RetrofitServer();
        userApi = retrofitServer.getRetrofit(ROOT_URL).create(UserApi.class);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        int userId = sharedPrefManager.getUserId();
        userApi.getUserByUserId(userId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    tvName.setText(userModel.getName());
                    Glide.with(getContext())
                            .load(userModel.getImage())
                            .into(userImg);
                    Log.e("Tên của user là: ",  userModel.getName());
                    Toast.makeText(getContext(), "Lấy tên của user thành công", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                    Log.e("TAG", errorMessage);
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra quyền truy cập
                if (checkStoragePermission()) {
                    // Quyền đã được cấp, tiếp tục chọn ảnh
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else {
                    // Yêu cầu quyền truy cập
                    requestStoragePermission();
                }
            }
        });
        //Order
        myOrder=mView.findViewById(R.id.myorder);
        myOrder.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),OrderActivity.class);
            startActivity(intent);
        });

        return  mView;
    }
    private boolean checkStoragePermission() {
        int resultRead = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        int resultWrite = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        return resultRead == PackageManager.PERMISSION_GRANTED && resultWrite == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Sử dụng Glide để hiển thị ảnh được chọn lên ImageView
            Glide.with(getContext())
                    .load(selectedImageUri)
                    .into(userImg);

            // Gửi ảnh lên server
            if (selectedImageUri != null) {
                uploadImageToServer(selectedImageUri);
            } else {
                Toast.makeText(getContext(), "Không thể lấy đường dẫn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void uploadImageToServer(Uri imageUri) {
        String realPath = getRealPathFromUri(imageUri);
        if (realPath != null && !realPath.isEmpty()) {
            File imageFile = new File(realPath);

            // Kiểm tra xem tệp tin có tồn tại và có thể đọc được không
            if (imageFile.exists() && imageFile.canRead()) {
                // Tiếp tục xử lý tải lên ảnh lên server
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

                // Gọi API để tải ảnh lên server
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
                int userId = sharedPrefManager.getUserId();
                userApi.uploadImage(imagePart, userId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Xử lý thành công
                            Toast.makeText(getContext(), "Tải ảnh lên server thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                            Log.e("TAG", errorMessage);
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                        Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Tệp tin không tồn tại hoặc không thể đọc được", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Không thể lấy đường dẫn tệp tin", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String realPath = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPath = cursor.getString(columnIndex);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realPath;
    }

    public void exit(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.baseline_question_mark_24);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Do you want to logout ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                intent=new Intent(getActivity(), LoginActivity.class);
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}