package com.violenthoboenterprises.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Klunj on 18/12/2016.
 */

public class HighScores extends Menu {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);

        TextView highScoreTextViewA = (TextView) findViewById(R.id.highScoreTextViewA);
        highScoreTextViewA.setVisibility(highScoreTextViewA.INVISIBLE);
        TextView highScoreTextViewB = (TextView) findViewById(R.id.highScoreTextViewB);
        highScoreTextViewB.setVisibility(highScoreTextViewB.INVISIBLE);
        TextView highScoreTextViewC = (TextView) findViewById(R.id.highScoreTextViewC);
        highScoreTextViewC.setVisibility(highScoreTextViewC.INVISIBLE);
        TextView highScoreTextViewD = (TextView) findViewById(R.id.highScoreTextViewD);
        highScoreTextViewD.setVisibility(highScoreTextViewD.INVISIBLE);
        TextView highScoreTextViewE = (TextView) findViewById(R.id.highScoreTextViewE);
        highScoreTextViewE.setVisibility(highScoreTextViewE.INVISIBLE);

        int tempA = Integer.valueOf(MainActivity.getHighScoreA());
        int tempB = Integer.valueOf(MainActivity.getHighScoreB());
        int tempC = Integer.valueOf(MainActivity.getHighScoreC());
        int tempD = Integer.valueOf(MainActivity.getHighScoreD());
        int tempE = Integer.valueOf(MainActivity.getHighScoreE());

        //TextViews are updated to the latest high scores
        highScoreTextViewA.setText(Integer.toString(tempA));
        highScoreTextViewB.setText(Integer.toString(tempB));
        highScoreTextViewC.setText(Integer.toString(tempC));
        highScoreTextViewD.setText(Integer.toString(tempD));
        highScoreTextViewE.setText(Integer.toString(tempE));

        //High score TextView only shows up once player actually sets one
        if (tempA != 0){
            highScoreTextViewA.setVisibility(highScoreTextViewA.VISIBLE);
        }
        if (tempB != 0){
            highScoreTextViewB.setVisibility(highScoreTextViewB.VISIBLE);
        }
        if (tempC != 0){
            highScoreTextViewC.setVisibility(highScoreTextViewC.VISIBLE);
        }
        if (tempD != 0){
            highScoreTextViewD.setVisibility(highScoreTextViewD.VISIBLE);
        }
        if (tempE != 0){
            highScoreTextViewE.setVisibility(highScoreTextViewE.VISIBLE);
        }

    }

}