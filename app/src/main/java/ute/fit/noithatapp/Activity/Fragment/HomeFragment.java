package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.CategoryAdapter;
import ute.fit.noithatapp.Activity.Adapter.PhotoAdapter;
import ute.fit.noithatapp.Activity.CartActivity;
import ute.fit.noithatapp.Activity.ProductByCategoryActivity;
import ute.fit.noithatapp.Activity.ProductDetailActivity;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.PhotoModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;


public class HomeFragment extends Fragment {
    View mView;
    ImageButton imgBtnAddToCart1,imgBtnAddToCart2;
    TextView productName1,productName2,productPrice1,productPrice2,countProduct1,countProduct2;
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
    //circle indicator
    ViewPager viewPagerPhoto;
    CircleIndicator circleIndicatorPhoto;
    PhotoAdapter photoAdapter;
    ArrayList<PhotoModel> photoModelArrayList;
    Timer timer;
    //Search
    SearchView searchView;
    ListView lvSearch;
    ArrayList<String> arrayListSearch;
    ArrayAdapter<String> searchAdapter;


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
        countProduct1=mView.findViewById(R.id.countProduct1);
        countProduct2=mView.findViewById(R.id.countProduct2);
        //CART
        //
        //Search
        searchView = mView.findViewById(R.id.imgvSearch);
        lvSearch = mView.findViewById(R.id.lvSearch);
        searchProduct();
        //
        imgvCart=mView.findViewById(R.id.imgvCart);
        imgvCart.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CartActivity.class));
        });

        recyclerViewCategory=mView.findViewById(R.id.rcv_category);//
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        //
        getProduct();
        getListCategory();
        //circle indicator
        viewPagerPhoto=mView.findViewById(R.id.viewPagerPhoto);
        circleIndicatorPhoto=mView.findViewById(R.id.circleIndicatorPhoto);
        photoModelArrayList=new ArrayList<>();
        photoModelArrayList.add(new PhotoModel(R.drawable.photo1));
        photoModelArrayList.add(new PhotoModel(R.drawable.photo2));
        photoModelArrayList.add(new PhotoModel(R.drawable.photo3));
        photoModelArrayList.add(new PhotoModel(R.drawable.photo4));
        photoAdapter=new PhotoAdapter(getContext(),photoModelArrayList);
        viewPagerPhoto.setAdapter(photoAdapter);
        circleIndicatorPhoto.setViewPager(viewPagerPhoto);
        photoAdapter.registerDataSetObserver(circleIndicatorPhoto.getDataSetObserver());
        autoSlidePhoto();
        return mView;
    }
    private void searchProduct(){

        arrayListSearch = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                if ( s.length() > 0)
                {
                    getListProductContainingName(s);
                    searchAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayListSearch);
                    lvSearch.setAdapter(searchAdapter);
                    lvSearch.setVisibility(View.VISIBLE);
                }else {
                    lvSearch.setVisibility(View.GONE);
                }
                if (searchAdapter != null) {
                    searchAdapter.getFilter().filter(s);
                }
                lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String productName = adapterView.getItemAtPosition(i).toString();
                        productApi.getProductByName(productName).enqueue(new Callback<ProductModel>() {
                            @Override
                            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                if (response.isSuccessful()) {
                                    ProductModel product = response.body();
                                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                                    intent.putExtra("productId", product.getProductId());
                                    startActivity(intent);
                                } else {
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductModel> call, Throwable t) {
                            }
                        });
                    }
                });
                return false;
            }
        });
    }
    private void getListProductContainingName(String s) {
        productModels = new ArrayList<>();
        retrofitServer = new RetrofitServer();
        productApi = retrofitServer.getRetrofit(ROOT_URL).create(ProductApi.class);
        productApi.getProductsConTainingName(s).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    productModels = response.body();
                    arrayListSearch.clear();
                    for (ProductModel product : productModels) {
                        arrayListSearch.add(product.getName());
                    }
                    Log.d("TAG", "giá trị của biến là: " + arrayListSearch);
                    Log.d("TAG", "duoc roi ");
                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
            }
        });
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
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String price1 = formatter.format(productModel1.getPrice());
                String price2 = formatter.format(productModel2.getPrice());
                productPrice1.setText(price1+" VNĐ");
                productName2.setText(productModel2.getName());
                productPrice2.setText(price2+" VNĐ");
                countProduct1.setText("Remaining "+String.valueOf(productModel1.getQuantity()));
                countProduct2.setText("Remaining "+String.valueOf(productModel2.getQuantity()));


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
    private void autoSlidePhoto(){
        if(photoModelArrayList == null || photoModelArrayList.isEmpty() || viewPagerPhoto == null){
            return;
        }
        if (timer==null){
            timer= new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItemPhoto=viewPagerPhoto.getCurrentItem();
                        int totalItem=photoModelArrayList.size()-1;
                        if(curentItemPhoto < totalItem){
                            curentItemPhoto++;
                            viewPagerPhoto.setCurrentItem(curentItemPhoto);
                        }else {
                            viewPagerPhoto.setCurrentItem(0);

                        }
                    }
                });
            }
        },500,5000);
    }

}