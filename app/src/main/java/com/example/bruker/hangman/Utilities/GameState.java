package com.example.bruker.hangman.Utilities;

/**
 * Created by bruker on 09-Sep-16.
 */
public class GameState {
    private String correct, current, used;
    private int tries;

    /**
     * Constructor
     */
    public GameState() {
        tries = 0;
    }

    /**
     * Sets the current word used
     * @param correct
     */
    public void setWord(String correct) {
        this.correct = correct;
    }

    /**
     * Returns the String object that contains the currently
     * active word in the game.
     * @return String
     */
    public String getWord() {
        return correct;
    }

    /**
     * Increases the amount of tries done by the player. Also returns
     * what this value is after the increase.
     * @return int
     */
    public int increseTries() {
        return ++tries;
    }

    /**
     * Resets the active fiels.
     */
    public void reset() {
        tries = 0;
        correct = null;
    }

    /**
     * Sets the current letters the players have gotten right.
     * @param current
     */
    public void setCurrent(String current) {
        this.current = current;
    }

    /**
     * Returns a String object with the letters the player has gotten right.
     * @return String
     */
    public String getCurrent() {
        return current;
    }

    /**
     * Made specifically for separation of concern. This will
     * check the incoming CharSequence if it's contained in the
     * correct word.
     * @param c
     * @return boolean
     */
    public boolean containes(CharSequence c) {
        return correct.contains(c);
    }

    /**
     * Returns a String object with the letters currently tried by the player.
     * @return String
     */
    public String getUsed() {
        return used;
    }

    /**
     * Updates the letters the player has currently tried.
     * @param used
     */
    public void setUsed(String used) {
        this.used = used;
    }
}