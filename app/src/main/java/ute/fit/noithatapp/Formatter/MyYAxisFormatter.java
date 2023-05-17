package ute.fit.noithatapp.Formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyYAxisFormatter extends LargeValueFormatter {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    @Override
    public String getBarLabel(BarEntry barEntry){
        return formatter.format(barEntry.getY() );
    }
    @Override
     public String getAxisLabel(float value, AxisBase axis){
        return formatter.format(value)+ " Triệu VNĐ";
    }
}
