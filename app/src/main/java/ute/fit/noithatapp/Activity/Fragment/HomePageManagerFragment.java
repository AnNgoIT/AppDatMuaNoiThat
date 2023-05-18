package ute.fit.noithatapp.Activity.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Activity.HomeManagerActivity;
import ute.fit.noithatapp.Activity.LoginActivity;
import ute.fit.noithatapp.Activity.SettingActivity;
import ute.fit.noithatapp.Api.ManagerApi;
import ute.fit.noithatapp.Contants.Const;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageManagerFragment extends Fragment {
    View view;
    RetrofitServer retrofitServer;
    ManagerApi managerApi;
    ImageButton imgManager;
    TextView tvTotalDay,tvTotalAll,tvTotalProcessed,tvTotalProcessing,
            tvPerTotalDay,tvPerTotalAll,tvPerProcessed,tvPerProcessing,menuSetting,menuLogout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageManagerFragment newInstance(String param1, String param2) {
        HomePageManagerFragment fragment = new HomePageManagerFragment();
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
        view=inflater.inflate(R.layout.fragment_homepage_manager, container, false);
        Init(view);
        view.setOnClickListener(view1 -> {
            menuSetting.setVisibility(View.INVISIBLE);
            menuLogout.setVisibility(View.INVISIBLE);
        });
        menuLogout.setOnClickListener(view1 -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            // Icon Of Alert Dialog
            alertDialogBuilder.setIcon(R.drawable.baseline_question_mark_24);
            // Setting Alert Dialog Message
            alertDialogBuilder.setMessage("Do you want to logout ?");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
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
        });


        menuSetting.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
        return view;
    }
    public void Init(View view){
        tvTotalAll = view.findViewById(R.id.tvTotalAll);
        tvTotalDay=view.findViewById(R.id.tvTotalDay);
        tvTotalProcessed=view.findViewById(R.id.tvTotalProcessed);
        tvTotalProcessing=view.findViewById(R.id.tvTotalProcessing);
        tvPerProcessed=view.findViewById(R.id.tv3);
        tvPerProcessing=view.findViewById(R.id.tv4);
        tvPerTotalDay=view.findViewById(R.id.tv1);
        tvPerTotalAll=view.findViewById(R.id.tv2);
        imgManager=view.findViewById(R.id.managerImg);
        menuLogout=view.findViewById(R.id.menuItemLogout);
        menuSetting=view.findViewById(R.id.menuItemSetting);
        imgManager.setOnClickListener(view1 -> {
            menuLogout.setVisibility(View.VISIBLE);
            menuSetting.setVisibility(View.VISIBLE);
        });
        retrofitServer=new RetrofitServer();
        managerApi=retrofitServer.getRetrofit(Const.ROOT_URL).create(ManagerApi.class);
        managerApi.getTotalAll().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String count=formatter.format(response.body());
                tvTotalAll.setText(count);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
        managerApi.getTotalProcessed().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String count=formatter.format(response.body());
                tvTotalProcessed.setText(count);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        managerApi.getTotalProcessing().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String count=formatter.format(response.body());
                tvTotalProcessing.setText(count);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd");
        String formattedDate = date.format(dateTimeFormatter);
        managerApi.getTotalDay(formattedDate).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long total=response.body();
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String count=formatter.format(total);
                tvTotalDay.setText(count);
                managerApi.getTotalDay(String.valueOf(Integer.valueOf(formattedDate)-1)).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        double per=(double) total / response.body() * 100;

                        tvPerTotalDay.setText( (Math.round(per * 10) / 10)+ " % \nTo yesterday");
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }

}