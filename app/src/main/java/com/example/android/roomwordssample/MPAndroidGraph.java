package com.example.android.roomwordssample;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.text.format.DateFormat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class MPAndroidGraph extends AppCompatActivity {


    private int mYear, mMonth, mDay;

    private WordViewModel mWordViewModel;
    List<Word> dataList;
    PieChart pieChart;
    // DatabaseHandler databaseHandler;
    // List<DataModel> dataList;
    boolean isDatabasePresent;
    String formattedDate, month;

    Button btn_all, btn_2018, btn_thisMonth, btn_today, btn_refresh;
    TextView tv_result;
    EditText start_date, end_date;

    private static final String DATABASE_NAME = "SampleDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_category);

        pieChart = findViewById(R.id.piechart);
        btn_all = findViewById(R.id.btn_all);
        btn_2018 = findViewById(R.id.btn_2018);
        btn_thisMonth = findViewById(R.id.btn_thisMonth);
        btn_today = findViewById(R.id.btn_today);
        tv_result = findViewById(R.id.tv_result);
        start_date=findViewById(R.id.selectStartDateForPie);
        end_date=findViewById(R.id.selectEndDateForPie);
        btn_refresh=findViewById(R.id.refreshGraphs);


        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFilter();
            }
        });


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

        showAllData(start, end);

    }


    public void setDateFilter()  {



        Log.d("LiveData", " typed " + start_date.getText()+end_date.getText());

      //  Date start = null;
      //  Date end = null;

        // (1) create a SimpleDateFormat object with the desired format.
        // this is the format/pattern we're expecting to receive.
        String expectedPattern = "dd-MM-yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try
        {
            // (2) give the formatter a String that matches the SimpleDateFormat pattern
            String startDate = String.valueOf(start_date.getText());
            Date start = formatter.parse(startDate);

            String endDate = String.valueOf(end_date.getText());
            Date end = formatter.parse(endDate);

            Log.d("LiveData", " datepre " + String.valueOf(start)+ " dateprestart "+String.valueOf(end));

            showAllData(start, end);

        }
        catch (ParseException e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }


    }




    public void showAllData(Date start, Date end) {
        tv_result.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);

        Log.d("LiveData", " dated " + String.valueOf(start)+ " dated "+String.valueOf(end));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getExpFilt(start , end).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(words);
                dataList=words;



                ArrayList<Entry> yValues = new ArrayList<> ();
                ArrayList<String> xValues = new ArrayList<> ();
                for (int i = 0; i < dataList.size (); i++) {


                    Log.d("LiveData", (dataList.get(i).getCategory())+" haveing amoutn "+ String.valueOf(dataList.get(i).getAmount()));


                    //DataModel dataModel = dataList.get (i);
                    yValues.add (new Entry (dataList.get(i).total, i));
                    xValues.add (dataList.get(i).getCategory());
                }


                PieDataSet pieDataSet = new PieDataSet (yValues, "");
                pieDataSet.setValueTextSize (18f);
                pieDataSet.setColors (ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData (xValues, pieDataSet);
                data.setValueFormatter (new PercentFormatter ());
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.DKGRAY);


                pieChart.setData (data);
                pieChart.setDrawHoleEnabled (true);

                pieChart.setDescription ("");

                pieChart.setCenterText("Total Expenses By Category");
                pieChart.setCenterTextSize(18f);

                pieChart.getLegend ().setEnabled (false);
                pieChart.setUsePercentValues (true);
                pieChart.animateY (1500);


            }
        });

    }

}