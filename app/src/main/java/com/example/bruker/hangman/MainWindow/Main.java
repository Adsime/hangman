package com.example.bruker.hangman.MainWindow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Settings.Settings;
import com.example.bruker.hangman.Statistics.Statistics;
import com.example.bruker.hangman.Utilities.DataHandler;
import com.example.bruker.hangman.Utilities.Languages;

public class Main extends AppCompatActivity {

    private GameEngine gameEngine;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String lang = DataHandler.loadData(getString(R.string.language), this);
        Languages.changeLanguage((lang.equals("")?Languages.ENGLISH:lang), this);
        ((Button) findViewById(R.id.flag)).setBackground(getResources().getDrawable(R.drawable.flag));
        gameEngine = new GameEngine(this);
        setMenuListeners();
    }

    /**
     * Used to activate the the menu items.
     */
    private void setMenuListeners() {
        Button st = (Button)findViewById(R.id.stats);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameEngine.saveCurrentGame();
                Intent intent = new Intent(context, Statistics.class);
                startActivity(intent);
            }
        });
        st = (Button)findViewById(R.id.cog);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameEngine.saveCurrentGame();
                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
            }
        });
        st = (Button)findViewById(R.id.flag);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameEngine.saveCurrentGame();
                gameEngine.saveWords();
                if(Languages.getLanguage(context).equals(Languages.ENGLISH)) {
                    Languages.changeLanguage(Languages.NORWEGIAN, context);
                    DataHandler.saveData(getString(R.string.language_change), "true", context);

                } else {
                    DataHandler.saveData(getString(R.string.language_change), "true", context);
                    Languages.changeLanguage(Languages.ENGLISH, context);
                }
                reload();
            }
        });
        st = (Button)findViewById(R.id.exit);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameEngine.saveCurrentGame();
                finish();
            }
        });
    }

    /**
     * Used to refresh the current window
     */
    private void reload() {
        startActivity(new Intent(this, Main.class));
        finish();
    }

    @Override
    protected void onPause() {
        if(DataHandler.loadData(getString(R.string.language_change), context).equals("true")) {
            DataHandler.saveData(getString(R.string.language_change), "", context);
        } else {
            String lang = DataHandler.loadData(getString(R.string.language), this);
            Languages.changeLanguage((lang.equals("")?Languages.ENGLISH:lang), this);
            gameEngine.saveCurrentGame();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.loadSavedGame();
    }
}