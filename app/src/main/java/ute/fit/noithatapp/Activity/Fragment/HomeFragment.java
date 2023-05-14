package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.CategoryAdapter;
import ute.fit.noithatapp.Activity.CartActivity;
import ute.fit.noithatapp.Activity.ProductByCategoryActivity;
import ute.fit.noithatapp.Activity.ProductDetailActivity;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;


public class HomeFragment extends Fragment {
    View mView;
    ImageButton imgBtnAddToCart1,imgBtnAddToCart2;
    TextView productName1,productName2,productPrice1,productPrice2;
    ImageView productImg1,productImg2;
    RecyclerView recyclerViewCategory;
    ImageView imgvCart;
    ConstraintLayout layout1,layout2;
    CategoryApi categoryApi;
    ProductApi productApi;
    RetrofitServer retrofitServer;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;
    ArrayList<ProductModel> productModels;
    OrderApi orderApi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        //find product id
        //API
        retrofitServer=new RetrofitServer();
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);

        //
        productName1=mView.findViewById(R.id.tvProduct1);
        productName2=mView.findViewById(R.id.tvProduct2);
        productPrice1=mView.findViewById(R.id.tvPriceProduct1);
        productPrice2=mView.findViewById(R.id.tvPriceProduct2);
        productImg1=mView.findViewById(R.id.imgViewProduct1);
        productImg2=mView.findViewById(R.id.imgViewProduct2);
        //CART
        imgvCart=mView.findViewById(R.id.imgvCart);
        imgvCart.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CartActivity.class));
        });
        //ADD TO CART BTN
        imgBtnAddToCart1=mView.findViewById(R.id.btnAddToCart1);
        imgBtnAddToCart2=mView.findViewById(R.id.btnAddToCart2);
        imgBtnAddToCart1.setOnClickListener(view -> {
            int userId= SharedPrefManager.getInstance(getActivity()).getUserId();
            Long count=Long.parseLong("1");
            orderApi.addToCart(userId,1,count).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();

                }
            });
        });
        imgBtnAddToCart2.setOnClickListener(view -> {
            int userId= SharedPrefManager.getInstance(getActivity()).getUserId();
            Long count=Long.parseLong("3");
            orderApi.addToCart(userId,1,count).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();

                }
            });
        });
        ////

        recyclerViewCategory=mView.findViewById(R.id.rcv_category);//
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        //
        getProduct();
        getListCategory();
        return mView;
    }

    private void getProduct() {
        productModels=new ArrayList<>();
        retrofitServer=new RetrofitServer();
        productApi=retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
        productApi.getProducts().enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                productModels=response.body();
                ProductModel productModel1=productModels.get(0);
                ProductModel productModel2=productModels.get(2);
                Glide.with(getContext()).load(productModel1.getImage()).into(productImg1);
                Glide.with(getContext()).load(productModel2.getImage()).into(productImg2);
                productName1.setText(productModel1.getName());
                productPrice1.setText(productModel1.getPrice().toString()+" vnd");
                productName2.setText(productModel2.getName());
                productPrice2.setText(productModel2.getPrice().toString()+" vnd");

                //ProductDetail //transaction data
                layout1=mView.findViewById(R.id.layout_product1);
                layout2=mView.findViewById(R.id.layout_product2);
                layout1.setOnClickListener(view -> {
                    Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("productId",productModel1.getProductId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
                layout2.setOnClickListener(view -> {
                    Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("productId",productModel2.getProductId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

            }
        });
    }

    private void getListCategory() {
        categoryModelArrayList = new ArrayList<>();
        retrofitServer = new RetrofitServer();
        categoryApi = retrofitServer.getRetrofit(ROOT_URL).create(CategoryApi.class);
        categoryApi.getCategory().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                categoryModelArrayList = response.body();
                categoryAdapter = new CategoryAdapter(getContext(),categoryModelArrayList, new CategoryAdapter.IClick() {
                    @Override
                    public void onClickCategoryItem(CategoryModel categoryModel) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("categoryId",categoryModel.getCategoryId());
                        Intent intent =new Intent(getActivity(), ProductByCategoryActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
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