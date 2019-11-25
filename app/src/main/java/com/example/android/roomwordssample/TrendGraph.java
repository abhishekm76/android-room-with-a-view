package com.example.android.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrendGraph extends AppCompatActivity {


    BarChart barChart;
    BarData data;
    BarDataSet bardataset;


    private int mYear, mMonth, mDay;
    private WordViewModel mWordViewModel;
    List<Word> dataList;
    boolean isDatabasePresent;
    String formattedDate, month;
    EditText start_date, end_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_graph);
      //  barChart = findViewById(R.id.TrendChart);
        barChart = findViewById(R.id.TrendChart);
        start_date=findViewById(R.id.selectStartDateForTrend);
        end_date=findViewById(R.id.selectEndDateForTrend);
        setDates();
    }


    private void getDataAndGraphIt(String start, String end){

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        drawGraph(entries,labels);

    }

    private void drawGraph(ArrayList entries, ArrayList labels){
        bardataset = new BarDataSet(entries, "Total Expense");
        data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Expense per month");  // set the description
        bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barChart.animateY(1500);

    }

    private void setDates(){

        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH) + 1;
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        end_date.setText(mDay + "-" + mMonth + "-" + mYear);

        TextViewDatePicker editTextEndDatePicker = new TextViewDatePicker(this, end_date);

        cal.set(Calendar.DAY_OF_YEAR, 1);

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH) + 1;
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        start_date.setText(mDay + "-" + mMonth + "-" + mYear);
        TextViewDatePicker editTextStartDatePicker = new TextViewDatePicker(this, start_date);


        Date start = cal.getTime();
        Date end = new Date();

        String startDate = start.toString();
        String endDate = end.toString();

        getDataAndGraphIt(startDate,endDate);

    }


}