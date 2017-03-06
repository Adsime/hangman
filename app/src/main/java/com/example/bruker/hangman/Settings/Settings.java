package com.example.bruker.hangman.Settings;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bruker.hangman.Utilities.Languages;
import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Utilities.DataHandler;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String lang = DataHandler.loadData(getString(R.string.language), this);
        Languages.changeLanguage((lang.equals("")?Languages.ENGLISH:lang), this);
        ((TextView)findViewById(R.id.rules_header)).setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/edo.ttf"));
        ((TextView)findViewById(R.id.rules_txt)).setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/edo.ttf"));
        new SettingsHandler(this);
        ((Button) findViewById(R.id.back_settings)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
