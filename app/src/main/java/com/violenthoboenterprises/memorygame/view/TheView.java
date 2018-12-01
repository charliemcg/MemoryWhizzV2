package com.violenthoboenterprises.memorygame.view;

import android.widget.Button;

public interface TheView {

    void seqAnimate(Button btnSeq, Button[] btn);
    void clickAnimate(Button btn);
    void updateScore(String score);
    void checkHighScore(int i, int highScore);
    void mainSplash();
}
