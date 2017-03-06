package com.example.bruker.hangman.MainWindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bruker.hangman.Statistics.StatisticsHandler;
import com.example.bruker.hangman.Utilities.DataHandler;
import com.example.bruker.hangman.Utilities.GameState;
import com.example.bruker.hangman.R;
import com.example.bruker.hangman.Utilities.Keyboard;
import com.example.bruker.hangman.Utilities.Languages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by bruker on 09-Sep-16.
 */
public class GameEngine {

    private TableRow scrollKeyboard, rowOne, rowTwo, rowThree;
    private HorizontalScrollView horizontalScrollView;
    private Keyboard keyboard;
    private ArrayList<String> words;
    private GameState gameState;
    private TextView correctWord;
    private ImageView imageView;
    private Context context;
    private View view;
    private boolean land, stat_kb;
    private Button keyboard_changer;
    public static final char EMPTY = ' ';
    public static final String SEPERATOR_W_UNDERLINE = " _", SEPERATOR = " ";


    /**
     * Constructor
     * @param context
     */
    public GameEngine(Context context) {

        this.context = context;
        view = ((Activity)context).getWindow().getDecorView();

        rowOne = (TableRow) view.findViewById(R.id.static_container_one);
        rowTwo = (TableRow) view.findViewById(R.id.static_container_two);
        rowThree = (TableRow) view.findViewById(R.id.static_container_three);

        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollable_keyboard_container);

        this.scrollKeyboard = (TableRow) view.findViewById(R.id.scrollable_keyboard);
        this.correctWord = (TextView) view.findViewById(R.id.correct_word);
        this.imageView = (ImageView) view.findViewById(R.id.progress_image);
        words = new ArrayList<>();

        correctWord.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "fonts/edo.ttf"));
        keyboard_changer = (Button) view.findViewById(R.id.keyboard_button);
        keyboard_changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeKeyBoard();
            }
        });
        Resources resources = context.getResources();
        stat_kb = (DataHandler.loadData(context.getString(R.string.selected_keyboard), context).equals("static")?true:false);
        keyboard_changer.setBackground((stat_kb)?resources.getDrawable(R.drawable.scroll_keyboard):resources.getDrawable(R.drawable.stat_keyboard));

        gameState = new GameState();
        keyboard = new Keyboard();
        land = context.getResources().getConfiguration().orientation == 2;
        start();
    }

    /**
     * Changes the keyboard type as well as the indicating image of the keyboard
     * change buttton
     */
    private void changeKeyBoard() {
        saveCurrentGame();
        gameState.reset();
        clearKeyboard();
        Resources resources = context.getResources();
        keyboard_changer.setBackground((stat_kb)?resources.getDrawable(R.drawable.stat_keyboard):resources.getDrawable(R.drawable.scroll_keyboard));
        stat_kb = !stat_kb;
        horizontalScrollView.setVisibility((land || stat_kb)?View.GONE:View.VISIBLE);
        setKeyBoard();
        activateKeyBoard();
        loadSavedGame();
    }

    /**
     * Fills an array with words.
     */
    private void resetWords() {
        for(String s : context.getResources().getStringArray(R.array.words)) {
            words.add(s);
        }
    }

    /**
     * Controls the basic game "loop"
     */
    public void start() {
        clear();
        horizontalScrollView.setVisibility((land || stat_kb)?View.GONE:View.VISIBLE);
        correctWord.setTextSize(20f);
        gameState.reset();
        imageView.setImageResource(R.drawable.hang_stage_0);
        generateWord("");
        setKeyBoard();
        activateKeyBoard();
    }

    /**
     * Places the keyboard buttons on the UI elements.
     */
    private void setKeyBoard() {
        int i = 0;
        for(Button b : keyboard.fillScrollBoard(context.getResources().
                getConfiguration().locale.getLanguage(), context)) {
            if(land || stat_kb) {
                if(i < 10) {
                    i++;
                    rowOne.addView(b);
                } else if(i < 20) {
                    i++;
                    rowTwo.addView(b);
                } else {
                    rowThree.addView(b);
                }
            } else {
                scrollKeyboard.addView(b);
            }
        }
    }


    /**
     * Based on the input string, this will generate a new word,
     * or use the one which is provided. This is done so that words
     * can be saved across states of the program. Lastly it will provide
     * the information to GameState
     * @param word
     */
    private void generateWord(String word) {
        correctWord.setText("");
        if(words.size() == 0) resetWords();

        //This line allows two uses of this method. By providing a word, that word will be used
        //Otherwise a random word from a given list will be chosen.
        String s = (word.equals("")? words.remove(new Random().nextInt(words.size())):word);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == EMPTY) {
                correctWord.append(SEPERATOR + SEPERATOR);
                sb.append(SEPERATOR);
                continue;
            }
            correctWord.append(SEPERATOR_W_UNDERLINE);
            sb.append(context.getString(R.string.underline));
        }
        correctWord.append(SEPERATOR);
        gameState.setCurrent(sb.toString());
        gameState.setWord(s);
        gameState.setUsed("");
    }

    /**
     * Based on orientation and keyboard settings, this will
     * activate the keyboard in a certain way.
     */
    public void activateKeyBoard() {
        if(land || stat_kb) {
            activateKeyBoardRow(rowOne);
            activateKeyBoardRow(rowTwo);
            activateKeyBoardRow(rowThree);
        } else {
            activateKeyBoardRow(scrollKeyboard);
        }
    }

    /**
     * Based on keyboard type and orientation, this will give
     * an action to all the buttons in a given row.
     * @param tableRow
     */
    private void activateKeyBoardRow(TableRow tableRow) {
        for(int i = 0; i < tableRow.getVirtualChildCount(); i++) {
            if(!stat_kb && !land) {
                ((Button) tableRow.getVirtualChildAt(i)).setLayoutParams(new TableRow.LayoutParams(30, LinearLayout.LayoutParams.FILL_PARENT, 1f));
            } else {
                ((Button) tableRow.getVirtualChildAt(i)).setLayoutParams(new TableRow.LayoutParams(30, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                ((Button) tableRow.getVirtualChildAt(i)).setPadding(0,0,0,0);
            }
            tableRow.getVirtualChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deactivateButton(((Button)view).getText().charAt(0));
                    tryLetter(((Button) view).getText().charAt(0));
                }
            });
        }
    }

    /**
     * Tests if input char is contained within the correct word.
     * @param c
     */
    public void tryLetter(char c) {
        gameState.setUsed(gameState.getUsed() + c);
        String letter = new StringBuilder().append(c).toString();
        if(gameState.getWord().contains(letter)) {
            char[] current = gameState.getCurrent().toCharArray();
            String correct = gameState.getWord();
            StringBuilder stringBuilder = new StringBuilder().append(" ");
            for(int i = 0; i < correct.length(); i++) {
                if(correct.charAt(i) == c) {
                    current[i] = c;
                    stringBuilder.append(c).append(" ");
                    continue;
                } stringBuilder.append(current[i]).append(" ");
            }
            correctWord.setText(stringBuilder.toString());
            gameState.setCurrent(new String(current));
            if(win()) {
                imageView.setImageResource(R.drawable.icon);
                setEndGame(false);
                StatisticsHandler.increaseStat(context.getString(R.string.wins), context);
            }
        } else {
            nextStage();
        }
    }

    /**
     * Returns a boolean depending on the state of letters found by the user.
     * @return boolean
     */
    public boolean win() {
        return !(correctWord.getText().toString().contains(context.getString(R.string.underline)));
    }

    /**
     * Removes the old keyboard from the screen, replaces it with a larger button for game
     * restart.
     * Sets the appropriate screen text for a win or a loss.
     * @param loss
     */
    private void setEndGame(boolean loss) {
        clear();
        correctWord.setText((loss?context.getString(R.string.game_over):
        context.getString(R.string.game_win)) + gameState.getWord());
        correctWord.setTextSize(15f);
        Button b = new Button(context);
        b.setTypeface((Typeface.createFromAsset(context.getResources().getAssets(), "fonts/edo.ttf")));
        b.setText(context.getString(R.string.new_game));
        b.setBackground(view.getResources().getDrawable(R.drawable.new_game_background));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        b.setWidth(imageView.getWidth()*4/5);
        if(!land || !stat_kb) horizontalScrollView.setVisibility(View.GONE);
        rowOne.addView(b);

    }

    /**
     * Clears dynamic UI components.
     */
    private void clear() {
        clearKeyboard();
        correctWord.setText("");
        gameState.setUsed("");
    }

    /**
     * Specific clear for the keyboard.
     */
    private void clearKeyboard() {
        scrollKeyboard.removeAllViews();
        rowOne.removeAllViews();
        rowTwo.removeAllViews();
        rowThree.removeAllViews();
    }

    /**
     * Increases the about of wrong letters the player have chosen. Chooses an
     * image to display based on that number.
     */
    public void nextStage() {
        int i = gameState.increseTries();
        switch (i) {
            case 1:
                imageView.setImageResource(R.drawable.hang_stage_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.hang_stage_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.hang_stage_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.hang_stage_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.hang_stage_5);
                break;
            default:
                imageView.setImageResource(R.drawable.hang_stage_6);
                setEndGame(true);
                StatisticsHandler.increaseStat(context.getString(R.string.losses), context);
                break;
        }
    }


    /**
     * Made for external save calls. Will find the necessary information
     * to save the current state of the game.
     */
    public void saveCurrentGame() {
        String word = gameState.getWord();
        String used = gameState.getUsed();
        save(word, used);
    }

    /**
     * Saves current state based on input.
     * @param word
     * @param used
     */
    private void save(String word, String used) {
        DataHandler.saveData(context.getString(R.string.save_word), word, context);
        DataHandler.saveData(context.getString(R.string.save_letter_state), used, context);
        DataHandler.saveData(context.getString(R.string.selected_keyboard), (stat_kb)?"static":"", context);
    }

    /**
     * Saves current state of the word array.
     */
    public void saveWords() {
        DataHandler.saveSetData(Languages.getLanguage(context), new HashSet<>(words), context);
    }

    /**
     * Loads last known state of the game. Also resets the game save data upon completion.
     */
    public void loadSavedGame() {
        //Loads the relevant saved data.
        String word = DataHandler.loadData(context.getString(R.string.save_word), context);
        String usedLetter = DataHandler.loadData(context.getString(R.string.save_letter_state), context);

        //Using the saved word, this will remove it from the given list of words
        words = new ArrayList<>(DataHandler.loadSetData(Languages.getLanguage(context), context));
        generateWord(word);

        //Simulation of the game using the saved letters
        if(!usedLetter.equals("")) {
            for(int i = 0; i < usedLetter.length(); i++) {
                tryLetter(usedLetter.charAt(i));
                deactivateButton(usedLetter.charAt(i));
            }
        }
        save("", "");
    }

    /**
     * Searches for a Button containing the parameter char in the
     * parameter TableRow
     * @param c
     * @param tableRow
     */
    private void tryLetter(char c, TableRow tableRow) {
        for(int i = 0; i < tableRow.getVirtualChildCount(); i++) {
            Button b = (Button)tableRow.getVirtualChildAt(i);
            if(b.getText().charAt(0) == c) {
                disableType(b);
                return;
            }
        }
    }

    /**
     * Looks at the used buttons from a previous state and sends them to be
     * disabled.
     * @param c
     */
    private void deactivateButton(char c) {

        if(land || stat_kb) {
            tryLetter(c, rowOne);
            tryLetter(c, rowTwo);
            tryLetter(c, rowThree);
        } else {
            tryLetter(c, scrollKeyboard);
        }
    }

    /**
     * Will disable and remove/fade a button based on the type of
     * keyboard active.
     * @param b
     */
    private void disableType(Button b) {
        if(gameState.containes(b.getText())) {
            b.setBackground(context.getResources().getDrawable(R.drawable.check));
        } else {
            b.setBackground(context.getResources().getDrawable(R.drawable.uncheck));
            b.getBackground().setAlpha(200);
        }
        b.setClickable(false);
        if(!stat_kb && !land) b.setWidth(40);
    }
}
