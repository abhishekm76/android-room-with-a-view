package com.example.android.roomwordssample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    Button buttonRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_graph);
        barChart = findViewById(R.id.TrendChart);
        start_date=findViewById(R.id.selectStartDateForTrend);
        end_date=findViewById(R.id.selectEndDateForTrend);
        setDates();

        buttonRefresh = findViewById(R.id.refreshTrendGraphs);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateFilter();
            }
        });

    }


    private void getDataAndGraphIt(Date start, Date end){

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
//        mWordViewModel.getExpFilt(start , end).observe(this, new Observer<List<Word>>() {

        mWordViewModel.getMonthTotal(start , end).observe(this, new Observer<List<Word>>() {

            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(words);
                dataList=words;
                Log.d("Data",words.toString());

                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<String>();

                for (int i = 0; i < dataList.size (); i++) {
                    entries.add(new BarEntry(dataList.get(i).total, i));
                    labels.add((dataList.get(i).getMonth()));
                   // labels.add(dataList.get(i).getCategory());
                    Log.d("Data", String.valueOf(dataList.size())+"index"+i);



                }
                drawGraph(entries,labels);
            }
        });
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
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date start = cal.getTime();
        Date end = new Date();
        String startDate = start.toString();
        String endDate = end.toString();
        TextViewDatePicker editTextEndDatePicker = new TextViewDatePicker(this, end_date);
        TextViewDatePicker editTextStartDatePicker = new TextViewDatePicker(this, start_date);
        start_date.setText(startDate);
        end_date.setText(endDate);
        getDataAndGraphIt(start,end);
    }


    public void setDateFilter()  {
        String expectedPattern = "dd-MM-yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try
        {
            // (2) give the formatter a String that matches the SimpleDateFormat pattern
            String startDate = String.valueOf(start_date.getText());
            Date start = formatter.parse(startDate);
            String endDate = String.valueOf(end_date.getText());
            Date end = formatter.parse(endDate);
            getDataAndGraphIt(start, end);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }

}