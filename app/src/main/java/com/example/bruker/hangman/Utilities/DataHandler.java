package com.example.bruker.hangman.Utilities;

import android.app.Activity;
import android.content.Context;

import com.example.bruker.hangman.R;

import java.util.HashSet;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by bruker on 14-Sep-16.
 */
public class DataHandler {

    /**
     * Loads a String object based on the incoming key.
     * @param key
     * @param context
     * @return String
     */
    public static String loadData(String key, Context context) {
        return context.getSharedPreferences(context.getString(R.string.preference), Activity.MODE_PRIVATE).getString(key, "");
    }

    /**
     * Saves a String value with the incoming key.
     * @param key
     * @param value
     * @param context
     */
    public static void saveData(String key, String value, Context context) {
        context.getSharedPreferences(context.getString(R.string.preference), Activity.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .commit();
    }

    /**
     * Saves a HashSet with the incoming string.
     * @param key
     * @param hashSet
     * @param context
     */
    public static void saveSetData(String key, HashSet hashSet, Context context){
        context.getSharedPreferences(context.getString(R.string.preference), Activity.MODE_PRIVATE)
                .edit()
                .putStringSet(key, hashSet)
                .commit();
    }

    /**
     * Loads a HashSet object based on the incoming key.
     * @param key
     * @param context
     * @return HashSet<String>
     */
    public static HashSet<String> loadSetData(String key, Context context) {
        return (HashSet<String>) context.getSharedPreferences(context.getString(R.string.preference), Activity.MODE_PRIVATE)
                .getStringSet(key, new HashSet<String>());
    }

}
