package ute.fit.noithatapp.Formatter;

import com.github.mikephil.charting.components.AxisBase;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Callback;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.Const;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.OrderModel;


public class MyXAxisFormatter extends ValueFormatter {

    public MyXAxisFormatter(ArrayList<String> days){
        this.days = days;
    }

    ArrayList<String> days;

    @Override
    public String getAxisLabel(float value, AxisBase axis){
        return days.get((int)value);
    }
}

