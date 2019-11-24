package com.example.android.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchApp extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_app);
        final Button buttonGraph = findViewById(R.id.viewGraph);
        final Button buttonExp = findViewById(R.id.viewExpenses);
        final Button buttonSettings = findViewById(R.id.settings);

        buttonGraph.setOnClickListener(this);
        buttonExp.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.viewGraph:
                Intent intentGraph = new Intent(this, MPAndroidGraph.class);
                startActivity(intentGraph);
                break;
            case R.id.viewExpenses:
                Intent intentExpenses = new Intent(this, MainActivity.class);
                startActivity(intentExpenses);
                break;
            case R.id.settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
        }


    }
}
