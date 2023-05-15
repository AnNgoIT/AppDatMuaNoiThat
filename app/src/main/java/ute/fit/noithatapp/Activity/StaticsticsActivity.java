package ute.fit.noithatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ute.fit.noithatapp.R;

public class StaticsticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staticstics_activity);

        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> visitors = new ArrayList<>();
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

    }
}