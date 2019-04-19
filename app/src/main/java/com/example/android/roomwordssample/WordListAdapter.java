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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemCategory;
        private final TextView itemDate;
        private final TextView itemMonth;
        private final TextView itemYear;
        private final TextView amount;
        private final TextView note;
        private final  TextView mode;
        private final TextView itemSubcat;

        private WordViewHolder(View itemView) {
            super(itemView);
            itemCategory = itemView.findViewById(R.id.categorytextView);
            itemDate = itemView.findViewById(R.id.datetextViewdate);
            itemMonth= itemView.findViewById(R.id.datetextViewmonth);
            itemYear = itemView.findViewById(R.id.datetextViewyear);
            amount=itemView.findViewById(R.id.amounttextView);
            note=itemView.findViewById(R.id.notetextView);
            mode=itemView.findViewById(R.id.modetextView);
            itemSubcat=itemView.findViewById(R.id.subcategorytextView);


        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_recycleerview, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word current = mWords.get(position);
        holder.itemCategory.setText(current.getCategory());


        if (current.getDateentry()!=null) {



        DateFormat dateFormat = new SimpleDateFormat("dd");
        String strDate = dateFormat.format(current.getDateentry());
        holder.itemDate.setText(strDate);

        DateFormat  monthFormat = new SimpleDateFormat("MMMM");
        String strDate1 = monthFormat.format(current.getDateentry());
        holder.itemMonth.setText(strDate1);

        DateFormat  yearFormat = new SimpleDateFormat("YYYY");
        String strDate2 = yearFormat.format(current.getDateentry());
        holder.itemYear.setText(strDate2);

        }

         holder.amount.setText(String.valueOf(current.getAmount()));
        holder.note.setText(current.getNote());
        holder.mode.setText(current.getMode());
        holder.itemSubcat.setText(current.getSubCat());

    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}


