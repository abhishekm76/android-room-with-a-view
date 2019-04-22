package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity for entering a word.
 */

public class NewEditWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText meditnote;
    private EditText meditamount;
    private EditText meditdate;
    private Spinner meditCat;
    private Spinner meditSubCat,mspinmode;
    private int mYear, mMonth, mDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewexpenses);
        meditnote = findViewById(R.id.editTextNote);
        meditamount=findViewById(R.id.editTextAmount);
        meditdate=findViewById(R.id.editTextDate);
        meditCat=findViewById(R.id.spinnerCategory);
        meditSubCat=findViewById(R.id.spinnerSubcat);
        mspinmode = findViewById(R.id.spinnerMode);

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("ID")){
            setTitle("Edit Item");

            Long date = editIntent.getLongExtra("Date",1);
            String cat = editIntent.getStringExtra("Cat");
            String subcat = editIntent.getStringExtra("SubCat");
            String mode = editIntent.getStringExtra("Mode");
            String note = editIntent.getStringExtra("Note");
            Float amount = editIntent.getFloatExtra("Amount",-1);
            int ID = editIntent.getIntExtra("ID",-1);



            meditnote.setText(note);
            meditamount.setText(String.valueOf(amount));

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date);

            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH)+1;
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            meditdate.setText(mDay+"-"+mMonth+"-"+mYear);


            String category[] = getResources().getStringArray(R.array.category);
            int catpostion = Arrays.asList(category).lastIndexOf(cat);
            meditCat.setSelection(catpostion);

            String subcategory[] = getResources().getStringArray(R.array.subcategory);
            int subcatpostion = Arrays.asList(subcategory).lastIndexOf(subcat);
            meditSubCat.setSelection(subcatpostion);

            String modelist[] = getResources().getStringArray(R.array.mode);
            int modeposition = Arrays.asList(modelist).lastIndexOf(mode);
            mspinmode.setSelection(modeposition);

        }else {
            setTitle("Add New item");


        // sets the current date into the edit text - could be improved by moving it out to class
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        meditdate.setText(mDay+"-"+mMonth+"-"+mYear);

        }

        // this is the line that calls the class
        EditText myEditText = meditdate;


        TextViewDatePicker editTextDatePicker = new TextViewDatePicker(this, myEditText);
            //TextViewDatePicker editTextDatePicker = new TextViewDatePicker(context, myEditText); //without min date, max date

        final Button button = findViewById(R.id.buttonsave);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(meditamount.getText().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = meditnote.getText().toString();


                    float amount = Float.valueOf(meditamount.getText().toString());


                    String date = meditdate.getText().toString();
                    String category = meditCat.getSelectedItem().toString();
                    String subcategory = meditSubCat.getSelectedItem().toString();
                    String mode = mspinmode.getSelectedItem().toString();

                   // replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra("Note", word);
                    replyIntent.putExtra("Amount", amount);
                    replyIntent.putExtra("Date", date);
                    replyIntent.putExtra("Category", category);
                    replyIntent.putExtra("SubCat", subcategory);
                    replyIntent.putExtra("Mode", mode);
                    int ID = getIntent().getIntExtra("ID", -1);

                        if (ID != -1){
                            replyIntent.putExtra("ID",ID);

                        }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
