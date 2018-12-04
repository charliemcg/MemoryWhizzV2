package com.violenthoboenterprises.memorygame.view;

import android.widget.Button;

/*
 * this is the interface for the MainActivity methods
 */
public interface TheView {
    void seqAnimate(Button btnSeq, Button[] btn);
    void clickAnimate(Button btn);
    void updateScore(String score);
    void updatePoints(int points);
    void checkHighScore(int i, int highScore);
    void mainSplash();
    void showToast(String buttonsRememberedString);
    void toggleBanner(boolean showBanner);
}
