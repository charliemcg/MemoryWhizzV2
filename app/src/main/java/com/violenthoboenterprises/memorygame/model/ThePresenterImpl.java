package com.violenthoboenterprises.memorygame.model;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.violenthoboenterprises.memorygame.MainActivity;
import com.violenthoboenterprises.memorygame.R;
import com.violenthoboenterprises.memorygame.presenter.ThePresenter;
import com.violenthoboenterprises.memorygame.view.TheView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class ThePresenterImpl implements ThePresenter {

    private static String TAG = "ThePresenterImpl";
    private TheView theView;
    private Context context;
    //    private int count = 0;
//    public static int[] scoreArray = new int[6];
//    boolean arrayFill = false;
    public static int highScore = 1;
    MediaPlayer fail;
    private int points = 0;
    private int multiplier = 1;
    //Used to display all intermittent values during all live score updates.
    private int intermittentValue;

    public ThePresenterImpl(TheView theView, Context context) {
        this.theView = theView;
        this.context = context;
        //Initializing fail sound.
        fail = MediaPlayer.create(context.getApplicationContext(), R.raw.fail);
    }

    @Override
    public void sequence(final Button[] btn, final HighScoreViewModel highScoreViewModel) {

        final Button[] btnSeq = generateSequence(btn);

        //Sequence is played by lighting up one button at a time.
        final int[] i = {0};
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                theView.seqAnimate(btnSeq[i[0]], btn);
            }
        });

        //Index number for sequence buttons
        final int[] seqNum = {0};
        //This is the number of buttons which are so far available in the sequence
        final int[] k = {1};
        //Iterates over the buttons to find which one was clicked
        for (int j = 0; j < 9; j++) {
            final int finalJ = j;
            btn[finalJ].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    theView.clickAnimate(btn[finalJ]);
                    //Checks if correct button selected
                    if (btnSeq[seqNum[0]] == v) {
                        //Player is awarded 9 points for every correct button selected.
                        points = points + (9 * multiplier);
                        theView.updatePoints(points);
                        seqNum[0]++;
                        String score = seqNum[0] + "/" + k[0];
                        theView.updateScore(score);
                        createScoreThread(handler);
                        //This block entered once player has successfully selected
                        // all of the buttons which are so far available
                        if (k[0] == seqNum[0]) {
                            //Player is awarded an additional 39 points for completing
                            // an entire sequence.
                            points = points + (39 * multiplier);
                            theView.updatePoints(points);
                            i[0] = 0;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Button sequence is replayed with a new
                                    // button appended on the end
                                    theView.seqAnimate(btnSeq[i[0]], btn);
                                    i[0]++;
                                    if (i[0] < k[0]) {
                                        //The index number for sequence buttons is reset.
                                        // The player must now start from the beginning again
                                        seqNum[0] = 0;
                                        handler.postDelayed(this, 500);
                                    }
                                }
                            });
                            //Multiplier is incremented every time the player memorises
                            //an entire sequence.
                            multiplier++;
                            //'k' is incremented to allow the player to
                            // select one button more than previous
                            k[0]++;
                            theView.checkHighScore(k[0], highScore);
                            String scoreReset = "0/" + k[0];
                            theView.updateScore(scoreReset);
                        }
                    } else {
                        fail.start();
                        //Resets the game by showing the player's final score and returning to menu.
                        String buttonsRememberedString;
                        if (k[0] != 2) {
//                            Toast.makeText(context, "You remembered " + (k[0] - 1)
//                                    + " buttons", Toast.LENGTH_LONG).show();
                            buttonsRememberedString = "You remembered " + (k[0] - 1) + " buttons";
                        } else {
//                            Toast.makeText(context, "You remembered " + (k[0] - 1)
//                                    + " button", Toast.LENGTH_LONG).show();
                            buttonsRememberedString = "You remembered " + (k[0] - 1) + " button";
                        }
                        theView.showToast(buttonsRememberedString);
                        for (Button aBtn : btn) {
                            aBtn.setEnabled(false);
                        }

                        int minScore = highScoreViewModel.getMinScore();
                        HighScore minHighScore = highScoreViewModel.getMinHighScore(minScore);

                        if (minScore < (k[0] - 1)) {
                            Calendar cal = Calendar.getInstance();
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int month = cal.get(Calendar.MONTH) + 1;
                            int year = cal.get(Calendar.YEAR);
                            String date = day + "/" + month + "/" + year;
                            highScoreViewModel.update(minHighScore);
                            minHighScore.setScore(k[0] - 1);
                            minHighScore.setDate(date);
                        }

                        points = 0;
                        multiplier = 1;
                        intermittentValue = 0;

                        theView.mainSplash();

                    }
                }
            });

        }
    }

    //Updates score on separate thread
    private void createScoreThread(final Handler handler) {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                updateScore(handler);
            }
        };
        new Thread(myRunnable).start();
    }

    private void updateScore(Handler handler) {
        //Displays all numbers between previous score and current score
        for (int i = intermittentValue; i < points; i++) {
            try {
                //Short delay to make effect of counting up
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    theView.updatePoints(finalI);
                    intermittentValue = points;
                }
            });
        }
    }

    private Button[] generateSequence(Button[] btn) {
        //An array is filled with random buttons. The order of these buttons will be the sequence.
        Button[] btnSeq = new Button[1000];
        for (int l = 0; l < 1000; l++) {
            Random random = new Random();
            int n = random.nextInt(9);
            btnSeq[l] = btn[n];
            //To cheat with when solving the puzzle.
            //System.out.println(n);
        }
        return btnSeq;
    }

}
