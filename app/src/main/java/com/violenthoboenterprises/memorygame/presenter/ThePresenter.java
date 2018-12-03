package com.violenthoboenterprises.memorygame.presenter;

import android.widget.Button;

import com.violenthoboenterprises.memorygame.model.HighScoreViewModel;

/*
 * this is the interface for the business logic methods
 */
public interface ThePresenter {

    void sequence(Button[] btn, HighScoreViewModel highScoreViewModel);

}
