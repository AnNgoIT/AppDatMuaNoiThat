package ute.fit.noithatapp.Activity.Fragment;

import android.annotation.SuppressLint;
import android.gesture.GestureUtils;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.Const;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Formatter.MyXAxisFormatter;
import ute.fit.noithatapp.Formatter.MyYAxisFormatter;
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
    public static ArrayList<OrderModel> orderModelArrayList = null;
    public MyXAxisFormatter myXAxis;
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


        retrofitServer = new RetrofitServer();

        orderApi = retrofitServer.getRetrofit(Const.ROOT_URL).create(OrderApi.class);
         orderApi.getOrdersByState("processing").enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                if (response.body().size() != 0) {
                    orderModelArrayList = response.body();
                    ArrayList revenue = new ArrayList();
                    ArrayList<String> day = new ArrayList<>();
                    for (int i = 0; i < orderModelArrayList.size(); i++) {
                        Date input = orderModelArrayList.get(i).getDate();
                        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = date.format(dateTimeFormatter);
                        if (day.contains(formattedDate)) {
                            continue;
                        }
                        day.add(formattedDate);
                        int finalI = i;
                        orderApi.getTotalRevenueByDate(formattedDate).enqueue(new Callback<Long>() {
                            @Override
                            public void onResponse(Call<Long> call, Response<Long> response) {
                                float totalRevenue = response.body()/1000000;
                                revenue.add(new BarEntry(finalI, totalRevenue));
                                BarDataSet bardataset = new BarDataSet(revenue, "Revenue");
                                barChart.animateY(3000);
                                BarData data = new BarData(bardataset);
                                data.setBarWidth(0.2f);
                                myXAxis = new MyXAxisFormatter(day);
                                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                                barChart.setData(data);
                                barChart.getXAxis().setValueFormatter(myXAxis);
                                barChart.getXAxis().setGranularityEnabled(true);
                                barChart.getAxisLeft().setValueFormatter(new MyYAxisFormatter());
                                barChart.getAxisRight().setEnabled(false);
                                barChart.setFitBars(true);
                            }
                            @Override
                            public void onFailure(Call<Long> call, Throwable t) {}

                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
            }
        });

        // Inflate the layout for this fragment
        return mView;
    }
}