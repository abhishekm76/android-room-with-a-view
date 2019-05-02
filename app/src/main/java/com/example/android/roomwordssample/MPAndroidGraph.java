package com.example.android.roomwordssample;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class MPAndroidGraph extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    List<Word> dataList;
    PieChart pieChart;
    // DatabaseHandler databaseHandler;
    // List<DataModel> dataList;
    boolean isDatabasePresent;
    String formattedDate, month;

    Button btn_all, btn_2018, btn_thisMonth, btn_today;
    TextView tv_result;

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

        showAllData();


    }

    public void showAllData() {
        tv_result.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);


        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getCatTotal().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(words);
                dataList=words;

                ArrayList<Entry> yValues = new ArrayList<> ();
                ArrayList<String> xValues = new ArrayList<> ();
                for (int i = 0; i < dataList.size (); i++) {

                    Log.d("LiveData", i+" out of " + String.valueOf(dataList.size()));
                    Log.d("LiveData", (dataList.get(i).getCategory())+" haveing amoutn "+ String.valueOf(dataList.get(i).getAmount()));
                    //DataModel dataModel = dataList.get (i);
                    yValues.add (new Entry (dataList.get(i).total, i));
                    xValues.add (dataList.get(i).getCategory());
                }


                PieDataSet pieDataSet = new PieDataSet (yValues, "");
                pieDataSet.setValueTextSize (18f);
                pieDataSet.setColors (ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData (xValues, pieDataSet);
             //   data.setValueFormatter (new PercentFormatter ());
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.DKGRAY);


                pieChart.setData (data);
                pieChart.setDrawHoleEnabled (true);

                pieChart.setDescription ("");

                pieChart.setCenterText("Total Expenses By Category");
                pieChart.setCenterTextSize(18f);

                pieChart.getLegend ().setEnabled (false);
                pieChart.setUsePercentValues (false);
                pieChart.animateY (1500);


            }
        });

    }

}