package com.example.bruker.hangman.Utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.example.bruker.hangman.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruker on 09-Sep-16.
 */
public class Keyboard {

    /**
     * Will decide how to fill a list of buttons based on input.
     * @param language
     * @param context
     * @return a list of buttons
     */
    public List<Button> fillScrollBoard(String language, Context context) {
        switch (language) {
            case "nb": return fillList(norwegian(), context);
                default: {
                    return fillList(english(), context);
                }
        }
    }

    /**
     * Will make buttons containing the letters from the incoming array of letters.
     * @param c
     * @param context
     * @return list of buttons
     */
    private List<Button> fillList(char [] c, Context context) {
        List<Button> returnVal = new ArrayList<>();
        Button b;
        for(int i : c) {
            b = new Button(context);
            b.setText(new StringBuilder().append((char)i).toString());
            setScrollingButtonStyle(b, context.getResources());
            returnVal.add(b);

        }
        return returnVal;
    }

    /**
     * Sets both style and size of the incoming button.
     * @param b
     * @param resources
     */
    private void setScrollingButtonStyle(Button b, Resources resources) {
        b.getBackground().setAlpha(0);
        /*b.setPadding(0,0,0,0);
        b.setMaxWidth(resources.getInteger(R.integer.btn_width));
        b.setMinimumWidth(resources.getInteger(R.integer.btn_width));
        b.setWidth(resources.getInteger(R.integer.btn_width));
        b.setMinHeight(resources.getInteger(R.integer.btn_height));
        b.setMinimumHeight(resources.getInteger(R.integer.btn_height));*/
        b.setTypeface(Typeface.createFromAsset(resources.getAssets(), "fonts/edo.ttf"));
    }

    public void test(TableRow tableRow) {
        for(int i = 0; i < tableRow.getVirtualChildCount(); i++) {
            ((Button) tableRow.getVirtualChildAt(i))
                    .setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.FILL_PARENT, 1f));
        }
    }

    /**
     * Will extend an array of the english alphabet by three norwegian letters.
     * @return char array containing norwegian alphabet.
     */
    private char[] norwegian() {
        char[] lang = Arrays.copyOf(english(), 29);
        lang[26] = 'Æ';
        lang[27] = 'Ø';
        lang[28] = 'Å';
        return lang;
    }

    /**
     * Will create a char array containing the english alphabet.
     * @return char array containing the english alphabet.
     */
    private char[] english() {
        char[] lang = new char[26];
        for(int i = 0; i < lang.length; i++) {
            lang[i] = (char)(i + (int)'A');
        }
        return lang;
    }

}
