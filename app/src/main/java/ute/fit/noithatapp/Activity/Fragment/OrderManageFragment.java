package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.ManageOrderAdapter;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManageFragment extends Fragment {

    private RetrofitServer retrofitServer;
    private ListView listViewOrders;
    private ManageOrderAdapter orderAdapter;
    private OrderApi orderApi;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderManageFragment newInstance(String param1, String param2) {
        OrderManageFragment fragment = new OrderManageFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_order_manage, container, false);

        listViewOrders = rootView.findViewById(R.id.listViewOrders);
        orderAdapter = new ManageOrderAdapter(getContext(), new ArrayList<>());
        listViewOrders.setAdapter(orderAdapter);

        retrofitServer = new RetrofitServer();
        orderApi = retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);

        // Gọi API để lấy danh sách đơn hàng
        orderApi.getAllOrders().enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<OrderModel> orders = response.body();
                    Log.d("TAG", "Danh sách đơn hàng: " + orders.toString());
                    orderAdapter.updateData(orders);
                } else {
                    String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                    Log.e("TAG", errorMessage);
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
                Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}