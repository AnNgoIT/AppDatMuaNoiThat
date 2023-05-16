package ute.fit.noithatapp.Activity.Fragment;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.Adapter.NotificationAdapter;
import ute.fit.noithatapp.Api.NotificationApi;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.Model.NotificationModel;
import ute.fit.noithatapp.Model.ProductModel;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    Button btnLogout;
    View mView;
    Intent intent;
    RetrofitServer retrofitServer;
    NotificationApi notificationApi;
    OrderApi orderApi;
    RecyclerView recyclerViewOrderList;
    NotificationAdapter notificationAdapter;
    int userId;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        //logout btn
        mView=inflater.inflate(R.layout.fragment_notification, container, false);
        Init(mView);
        return mView;
    }
    public void Init(View mView){
        userId= SharedPrefManager.getInstance(getContext()).getUserId();
        retrofitServer=new RetrofitServer();
        notificationApi=retrofitServer.getRetrofit(ROOT_URL).create(NotificationApi.class);
        orderApi=retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);
        recyclerViewOrderList = mView.findViewById(R.id.rcv_notification);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerViewOrderList.setLayoutManager(mLayoutManager);
        notificationAdapter=new NotificationAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), getContext(), new NotificationAdapter.IClickClear() {
            @Override
            public void ClickClear(NotificationModel notificationModel) {
                notificationApi.saveNotificationHide(notificationModel.getNotificationId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(notificationAdapter.getItemCount() == 0){
                            recyclerViewOrderList.setBackgroundResource(R.drawable.emptynotification);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        notificationApi.getNotificationByUser(userId).enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {
                ArrayList<NotificationModel> notificationModelArrayList=response.body();
                notificationAdapter.setNotificationModelArrayList(notificationModelArrayList);
                orderApi.getNotificationOrder(userId).enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                        ArrayList<Integer> orderIdList=response.body();
                        orderApi.getProductByOrder(orderIdList).enqueue(new Callback<ArrayList<ProductModel>>() {
                            @Override
                            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                               notificationAdapter.setProductModelArrayList(response.body());
                               orderApi.getCountOrder(orderIdList).enqueue(new Callback<ArrayList<Long>>() {
                                   @Override
                                   public void onResponse(Call<ArrayList<Long>> call, Response<ArrayList<Long>> response) {
                                       notificationAdapter.setCountList(response.body());
                                       notificationAdapter.filter();
                                       if(notificationAdapter.getItemCount() == 0){
                                           recyclerViewOrderList.setBackgroundResource(R.drawable.emptynotification);
                                       }
                                       recyclerViewOrderList.setAdapter(notificationAdapter);
                                   }

                                   @Override
                                   public void onFailure(Call<ArrayList<Long>> call, Throwable t) {

                                   }
                               });
                            }

                            @Override
                            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

                            }
                        });

                    }
                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {

            }
        });
    }
}