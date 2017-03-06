package com.example.bruker.hangman.Statistics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Utilities.DataHandler;

/**
 * Created by bruker on 14-Sep-16.
 */
public class StatisticsHandler {

    private View view;

    /**
     * Constructor
     * @param context
     */
    public StatisticsHandler(Context context) {
        view = ((Activity)context).getWindow().getDecorView();
        setStyle((TextView)view.findViewById(R.id.wins_display), context);
        setStyle((TextView)view.findViewById(R.id.losses_display), context);
        setStyle((TextView)view.findViewById(R.id.winrate_display), context);
        setResetButton(context);
        loadScreen(context);
     }

    /**
     * Sets the style for a specific TextArea
     * @param t
     * @param context
     */
    private void setStyle(TextView t, Context context) {
        t.setTextColor(context.getResources().getColor(R.color.black));
        t.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "fonts/edo.ttf"));
    }

    /**
     * Sets the values and behaviour for a button which is used to reset
     * the score.
     * @param context
     */
    private void setResetButton(final Context context) {
        boolean vertical = context.getResources().getConfiguration().orientation == 1;
        Button b = (Button)view.findViewById(R.id.reset_stats_btn);
        b.setTextSize(vertical?25f:40f);
        b.setText(context.getString(R.string.reset_score));
        b.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "fonts/edo.ttf"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHandler.saveData(context.getString(R.string.wins), "0", context);
                DataHandler.saveData(context.getString(R.string.losses), "0", context);
                loadScreen(context);
            }
        });
    }

    /**
     * Loads and places the data in the view.
     * @param context
     */
    private void loadScreen(Context context) {
        TextView textView = (TextView)view.findViewById(R.id.wins_display);
        double wins = Double.parseDouble(DataHandler.loadData(context.getString(R.string.wins), context));
        double losses = Double.parseDouble(DataHandler.loadData(context.getString(R.string.losses), context));
        textView.setText(context.getString(R.string.stat_wins) + " " + (int)wins);
        textView = (TextView) view.findViewById(R.id.losses_display);
        textView.setText(context.getString(R.string.stat_losses) + " " + (int)losses);
        textView = (TextView) view.findViewById(R.id.winrate_display);
        textView.setText(context.getString(R.string.stat_win_rate) + " " + (int)(wins/(wins+losses)*100) + "%");
    }

    /**
     * Based on the incoming string, the number saved will be increased by one.
     * @param type
     * @param context
     */
    public static void increaseStat(String type, Context context) {
        String key = context.getString(type.equals(R.string.wins)?R.string.wins:R.string.losses);
        String value = DataHandler.loadData(key, context);
        int stat = Integer.parseInt(value.equals("")?"0":value) + 1;
        DataHandler.saveData(key, new StringBuilder().append(stat).toString(), context);
    }
}
