package com.example.bruker.hangman.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Utilities.DataHandler;

import java.util.Locale;

/**
 * Created by bruker on 09-Sep-16.
 */
public class Languages {
    public static final String NORWEGIAN = "nb";
    public static final String ENGLISH = "en";

    /**
     * Will change the language of the application based on input.
     * @param language
     * @param context
     */
    public static void changeLanguage(String language, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = new Locale(language);
        resources.updateConfiguration(conf, dm);
        DataHandler.saveData(context.getString(R.string.language), language, context);
    }

    /**
     * Returns a string containing the language code of the currently active
     * language.
     * @param context
     * @return String
     */
    public static String getLanguage(Context context) {
        Resources resources = context.getResources();
        return resources.getConfiguration().locale.getLanguage();
    }
}
