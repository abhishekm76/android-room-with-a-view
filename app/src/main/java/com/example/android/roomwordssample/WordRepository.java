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

import androidx.lifecycle.LiveData;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    private LiveData<List<Word>> mCatTotal;
    private LiveData<List<Word>> mExpFilt;
    private LiveData<List<Word>> mMonthTotal;
    List<Word> mWordList;


    List<String> DisCat = new ArrayList<String>();
    public static final String TAG = "Category";

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
        mCatTotal =mWordDao.getCatTotal();





      // mWordList =mWordDao.getAllExpenses();
//        DisCat=mWordDao.getCategory();

   }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {return mAllWords;}
    LiveData<List<Word>> getCatTotal() {return mCatTotal;}
    LiveData<List<Word>> getExpFilt(Date start, Date end) {
        mExpFilt=mWordDao.loadAllExpensesBetweenDates(start ,end);
        return mExpFilt;}

        LiveData<List<Word>> getMonthTotal(Date start, Date end) {
        mMonthTotal=mWordDao.loadAllExpensesByMonth(start ,end);
        return mMonthTotal;}

/*

    List<Word> getAllExpenses() {
         new getExpensesAsyncTask(mWordDao).execute();



     //   Log.d("this is my array", "arr: " + mWordList.get(1).toString());

        return mWordList;
    }

*/


   // List<String> mAllCat = mWordDao.getCategory();

  //  public List<String> getCat() {return mAllCat;    }








    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }


    public void deleteexpense(Word word) {
        new deleteWordAsyncTask(mWordDao).execute(word);

    }



    public void deleteAllExpense() {
        new deleteAllAsyncTask(mWordDao).execute();

    }


    public void update(Word word) {
        new updateWordAsyncTask(mWordDao).execute(word);
    }




    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    //delete asynctask

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }


    private static class deleteAllAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteAllAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

















    private static class updateWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        updateWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.updateWord(params[0]);
            return null;
        }
    }

/*

    private static class getExpensesAsyncTask extends AsyncTask<Word, Void, List<Word>> {
        private WordDao mAsynTaskDao;
        ProgressDialog dialog = new ProgressDialog();



        public getExpensesAsyncTask(WordDao mWordDao) {
            mAsynTaskDao= mWordDao;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setIndeterminate(true);
            dialog.setMessage("Please Wait...");
            dialog.setTitle("Loading Messages");
            dialog.setCancelable(true);
            dialog.show();


        }

        @Override
        protected List<Word> doInBackground(Word... words) {
           mWordList=  mAsynTaskDao.getAllExpenses();
//            Log.d("this is my array", "arr: " + mWordList.get(1).toString());
            return mWordList;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            super.onPostExecute(words);
            mWordList=words;
            dialog.dismiss();
        }
    }

*/
}
