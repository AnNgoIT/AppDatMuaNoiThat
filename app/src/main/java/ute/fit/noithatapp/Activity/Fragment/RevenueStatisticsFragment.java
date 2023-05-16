package ute.fit.noithatapp.Activity.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.Const;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueStatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RetrofitServer retrofitServer;

    OrderApi orderApi;

    public RevenueStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueStatisticsFragment newInstance(String param1, String param2) {
        RevenueStatisticsFragment fragment = new RevenueStatisticsFragment();
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
        View mView = inflater.inflate(R.layout.fragment_revenue_statistics, container, false);


        BarChart barChart = mView.findViewById(R.id.barChart);

        ArrayList<BarEntry> visitors = new ArrayList<>();

        retrofitServer = new RetrofitServer();

        orderApi = retrofitServer.getRetrofit(Const.ROOT_URL).create(OrderApi.class);
        orderApi.getOrdersByState().enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                System.out.println("HELLO: "+ response.body().get(0).getDate().getDate());
            }

            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
                System.out.println("HELLO: ");
            }
        });

        visitors.add(new BarEntry(2014,420));
        visitors.add(new BarEntry(2015,475));
        visitors.add(new BarEntry(2016,508));
        visitors.add(new BarEntry(2017,660));
        visitors.add(new BarEntry(2018,550));
        visitors.add(new BarEntry(2019,630));
        visitors.add(new BarEntry(2020,470));
        visitors.add(new BarEntry(2021,450));
        visitors.add(new BarEntry(2022,520));
        visitors.add(new BarEntry(2023,680));


        BarDataSet barDataSet =  new BarDataSet(visitors,"Revenue");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Vendor Management");
        barChart.animateY(2000);
        // Inflate the layout for this fragment
        return mView;
    }
}