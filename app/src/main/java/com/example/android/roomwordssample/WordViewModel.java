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

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Word>> mAllWords;
    private LiveData<List<Word>> mCatTotal;
    private LiveData<List<Word>> mExpFiltered;
    private LiveData<List<Word>> mMonthTotal;

    private List<Word> mAllExpenses;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mCatTotal = mRepository.getCatTotal();



     // mAllExpenses=mRepository.getAllExpenses();

    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    LiveData<List<Word>> getCatTotal() { return mCatTotal; }

    LiveData<List<Word>> getExpFilt(Date start, Date end) {
        mExpFiltered = mRepository.getExpFilt(start, end);
        return mExpFiltered; }

    LiveData<List<Word>> getMonthTotal(Date start, Date end) {
        mMonthTotal = mRepository.getMonthTotal(start, end);
        return mMonthTotal; }


 /*  List<Word> getAllExpenses(){
        return mAllExpenses;
    }*/


  //  List<String> getCat (){return mRepository.getCat();}

    void insert(Word word) {
        mRepository.insert(word);
    }

    void update(Word word) {
        mRepository.update(word);
    }

    void deleteexpense (Word word){mRepository.deleteexpense(word);}

    //public List<String> getCategory() {return mCat;}

    public void deleteAll() {mRepository.deleteAllExpense();}

}