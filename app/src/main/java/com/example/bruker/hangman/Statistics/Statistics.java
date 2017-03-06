package com.example.bruker.hangman.Statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bruker.hangman.Utilities.Languages;
import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Utilities.DataHandler;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        String lang = DataHandler.loadData(getString(R.string.language), this);
        Languages.changeLanguage((lang.equals("")?Languages.ENGLISH:lang), this);
        new StatisticsHandler(this);
        findViewById(R.id.back_statistic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
