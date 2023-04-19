package ute.fit.noithatapp.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.CategoryAdapter;
import ute.fit.noithatapp.Activity.HomeActivity;
import ute.fit.noithatapp.Activity.LoginActivity;
import ute.fit.noithatapp.Activity.MainActivity;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Api.UserApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.UserModel;
import ute.fit.noithatapp.R;


public class HomeFragment extends Fragment {
    View mView;
    RecyclerView recyclerViewCategory;

    CategoryApi categoryApi;
    RetrofitServer retrofitServer;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        //
        recyclerViewCategory=mView.findViewById(R.id.rcv_category);//
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(layoutManager);
        //
        getListCategory();
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerViewCategory.addItemDecoration(itemDecoration);
        return mView;
    }
    private void getListCategory() {
        categoryModelArrayList = new ArrayList<>();
        retrofitServer = new RetrofitServer();
        categoryApi = retrofitServer.getRetrofit().create(CategoryApi.class);
        categoryApi.getCategory().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                categoryModelArrayList = response.body();
                categoryAdapter = new CategoryAdapter(categoryModelArrayList, new CategoryAdapter.IClick() {
                    @Override
                    public void onClickCategoryItem(CategoryModel categoryModel) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
                recyclerViewCategory.setAdapter(categoryAdapter);
            }
            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
