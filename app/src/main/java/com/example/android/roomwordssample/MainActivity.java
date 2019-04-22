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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_RECYCLER_VIEW_ITEM_CODE = 2;

    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEditWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getexpenseatposition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myWord.getDateentry(), Toast.LENGTH_SHORT).show();

                        // Delete the word
                        mWordViewModel.deleteexpense(myWord);
                    }
                });

        helper.attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new WordListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Word clickedexpenses) {
                Intent intentEdit = new Intent(MainActivity.this, NewEditWordActivity.class);
                intentEdit.putExtra("ID", clickedexpenses.getExpenseID());
                intentEdit.putExtra("Date", clickedexpenses.getDateentry().getTime());
                intentEdit.putExtra("Cat", clickedexpenses.getCategory());
                intentEdit.putExtra("SubCat", clickedexpenses.getSubCat());
                intentEdit.putExtra("Note", clickedexpenses.getNote());
                intentEdit.putExtra("Amount", clickedexpenses.getAmount());
                intentEdit.putExtra("Mode", clickedexpenses.getMode());

                startActivityForResult(intentEdit, EDIT_RECYCLER_VIEW_ITEM_CODE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word();

            // word.setCategory(data.getStringExtra(NewEditWordActivity.EXTRA_REPLY));

            String notetext = data.getStringExtra("Note");
            Float amountf = (Float) data.getFloatExtra("Amount", 1);
            String datetext = data.getStringExtra("Date");
            String cattext = data.getStringExtra("Category");
            String subcattext = data.getStringExtra("SubCat");
            String modetext = data.getStringExtra("Mode");

            word.setNote(notetext);
            //  word.setDateentry(datetext);
            word.setCategory(cattext);
            word.setSubCat(subcattext);
            word.setAmount(amountf);
            word.setMode(modetext);


            SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date2 = null;
            try {
                date2 = formatter2.parse(datetext);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            word.setDateentry(date2);


            mWordViewModel.insert(word);
        } else if (requestCode == EDIT_RECYCLER_VIEW_ITEM_CODE && resultCode == RESULT_OK) {

            int ID =data.getIntExtra("ID",-1);
            //int ID = Integer.parseInt(data.getStringExtra("ID"));

            if (ID != -1) {
                Word word = new Word();
                String notetext = data.getStringExtra("Note");
                Float amountf = (Float) data.getFloatExtra("Amount", 1);
                String datetext = data.getStringExtra("Date");
                String cattext = data.getStringExtra("Category");
                String subcattext = data.getStringExtra("SubCat");
                String modetext = data.getStringExtra("Mode");

                word.setNote(notetext);
                //  word.setDateentry(datetext);
                word.setCategory(cattext);
                word.setSubCat(subcattext);
                word.setAmount(amountf);
                word.setMode(modetext);
                word.setExpenseID(ID);


                SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
                Date date2 = null;
                try {
                    date2 = formatter2.parse(datetext);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                word.setDateentry(date2);


                mWordViewModel.update(word);

            }


        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Entry not save",
                    Toast.LENGTH_LONG).show();
        }
    }


}
