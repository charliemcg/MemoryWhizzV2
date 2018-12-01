package com.violenthoboenterprises.memorygame.presenter;

import android.widget.Button;

public interface ThePresenter {

    void sequence(Button[] btn);
    int getHighScoreA();
    int getHighScoreB();
    int getHighScoreC();
    int getHighScoreD();
    int getHighScoreE();

}
