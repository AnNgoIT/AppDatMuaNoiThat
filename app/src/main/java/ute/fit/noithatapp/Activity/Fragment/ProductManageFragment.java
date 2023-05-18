package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.ProductDetailActivity;
import ute.fit.noithatapp.Api.CategoryApi;
import ute.fit.noithatapp.Api.ProductApi;
import ute.fit.noithatapp.Contants.Const;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.CategoryModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductManageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View mView;
    TextView tvCountSofa,tvCountCabinet,tvCountChair,tvCountArmChair,tvCountLamp,tvCountBed;

    SearchView searchView;

    ListView lvSearch;

    ArrayList<String> arrayListSearch;
    ArrayAdapter<String> searchAdapter;

    ProductApi productApi;

    ArrayList<ProductModel> productModels;

    RetrofitServer retrofitServer;

    ArrayList<CategoryModel> categoryModelArrayList;

    CategoryApi categoryApi;

    ArrayList<Integer> countProductsCategory;
    public ProductManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductManageFragment newInstance(String param1, String param2) {
        ProductManageFragment fragment = new ProductManageFragment();
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
        retrofitServer = new RetrofitServer();
        categoryApi=retrofitServer.getRetrofit(ROOT_URL).create(CategoryApi.class);
        mView = inflater.inflate(R.layout.fragment_product_manage, container, false);
        searchView = mView.findViewById(R.id.M_imgvSearch);
        lvSearch = mView.findViewById(R.id.M_lvSearch);
        searchProduct();
        getCountCategory(mView);
        return mView;
    }

    private void getCountCategory(View view) {
        tvCountSofa=view.findViewById(R.id.countSofaP_C);
        tvCountArmChair=view.findViewById(R.id.countArmChairP_C);
        tvCountBed=view.findViewById(R.id.countBedP_C);
        tvCountLamp=view.findViewById(R.id.countLampP_C);
        tvCountCabinet=view.findViewById(R.id.countCabinetP_C);
        tvCountChair=view.findViewById(R.id.countChairP_C);
        countProductsCategory = new ArrayList<>();
        categoryApi.getCountProductByCategory().enqueue(new Callback<ArrayList<Integer>>() {
            @Override
            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                countProductsCategory = response.body();
                int getCountC1 = countProductsCategory.get(0);
                int getCountC2 = countProductsCategory.get(1);
                int getCountC3 = countProductsCategory.get(2);
                int getCountC4 = countProductsCategory.get(3);
                int getCountC5 = countProductsCategory.get(4);
                int getCountC6 = countProductsCategory.get(5);
//                tvCountSofa.setText(getCountC1);
//                tvCountCabinet.setText(getCountC2);
//                tvCountChair.setText(getCountC3);
//                tvCountArmChair.setText(getCountC4);
//                tvCountLamp.setText(getCountC5);
//                tvCountBed.setText(getCountC6);

            }

            @Override
            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {

            }
        });
    }


    private void searchProduct() {
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
}