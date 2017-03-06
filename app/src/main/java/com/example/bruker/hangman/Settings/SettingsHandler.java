package com.example.bruker.hangman.Settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.bruker.hangman.R;

/**
 * Created by bruker on 15-Sep-16.
 */
public class SettingsHandler {

    private View view;
    private Context context;
    private TextView rules, rulesHeader;

    /**
     * Constructor
     * @param context
     */
    public SettingsHandler(Context context) {
        this.context = context;
        this.view = ((Activity)context).getWindow().getDecorView();
        this.rules = (TextView) view.findViewById(R.id.rules_txt);
        this.rulesHeader = (TextView) view.findViewById(R.id.rules_header);

        rules.setText(context.getString(R.string.rules));
        rulesHeader.setText(context.getString(R.string.rule_header));
     }
}
