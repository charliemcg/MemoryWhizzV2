package com.violenthoboenterprises.memorygame.model;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.violenthoboenterprises.memorygame.presenter.ThePresenter;
import com.violenthoboenterprises.memorygame.view.TheView;

import java.util.Arrays;
import java.util.Random;

public class ThePresenterImpl implements ThePresenter {

    private TheView theView;
    private Context context;
    private int count = 0;
    public static int[] scoreArray = new int[6];
    boolean arrayFill = false;
    public static int highScore = 1;

    public ThePresenterImpl(TheView theView, Context context){
        this.theView = theView;
        this.context = context;
    }

    @Override
    public void sequence(final Button[] btn) {

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

        final int[] seqNum = {0};//<-Index number for sequence buttons.
        final int[] k = {1};//<-This is the number of buttons which are so far available in the sequence.
//        final String zero = getResources().getString(R.string.zero);
        for (int j = 0; j < 9; j++) {//<-Iterates over the buttons to find which one was clicked.
            final int finalJ = j;
//            final TextView finalScore = score;
//            final TextView finalScore1 = score;
            btn[finalJ].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    theView.clickAnimate(btn[finalJ]);
                    if (btnSeq[seqNum[0]] == v) {//<-Checks if correct button selected.
                        seqNum[0]++;
                        String score = seqNum[0] + "/" + k[0];
                        theView.updateScore(score);
//                        finalScore.setText(seqNum[0] + "/" + k[0]);//<-Updates the score as player progresses
                        if (k[0] == seqNum[0]) {//<-This block entered once player has successfully selected all of the buttons which are so far available.
                            i[0] = 0;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    theView.seqAnimate(btnSeq[i[0]], btn);//<-Button sequence is replayed with a new button appended on the end
                                    i[0]++;
                                    if (i[0] < k[0]) {
                                        seqNum[0] = 0;//<-The index number for sequence buttons is reset. The player must now start from the beginning again.
                                        handler.postDelayed(this, 500);
                                    }
                                }
                            });
                            k[0]++;//<-'k' is incremented to allow the player to select one button more than previous.
                            theView.checkHighScore(k[0], highScore);
//                            if (k[0] > highScore) {//<-Checks if new high score is achieved
//                                if (showOnce[0]) {//<-Only informs of high score once per game
//                                    highScoreJingle.start();
//                                    Toast.makeText(context, "New High Score!", Toast.LENGTH_SHORT).show();
//                                    showOnce[0] = false;
//                                }
//                            }
                            String scoreReset = "0/" + k[0];
//                            finalScore.setText(scoreReset);
                            theView.updateScore(scoreReset);
                        }
                    } else {
                        //Resets the game by showing the player's final score and returning to menu.
                        if (k[0] != 2) {
                            Toast.makeText(context, "You remembered " + (k[0] - 1) + " buttons", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "You remembered " + (k[0] - 1) + " button", Toast.LENGTH_LONG).show();
                        }
                        for (Button aBtn : btn) {
                            aBtn.setEnabled(false);
                        }

                        for (int j = 0; j < 6; j++) {
                            if (scoreArray[j] != 0) {
                                count++;
                            }
                        }

                        if (count >= 5) {
                            arrayFill = true;
                        }

                        for (int i = 0; i < scoreArray.length; i++) {
                            if (!arrayFill) {//<-Adds all scores to array until array is full
                                if (scoreArray[i] == 0) {
                                    System.out.println("ScoreArray is " + scoreArray[i]);
                                    scoreArray[i] = k[0] - 1;
                                    System.out.println("ScoreArray is " + scoreArray[i]);
                                    break;
                                }
                            }
                            if (arrayFill) {//<-Only adds score to array if higher than any array value
                                if (scoreArray[i] < highScore - 1) {
                                    scoreArray[i] = highScore - 1;
                                    break;
                                }
                            }
                        }

                        Arrays.sort(scoreArray);//<-Sorts array numerically
                        System.out.println("End of high scores");
                        for (int i = 0; i <= 5; i++) {
                            System.out.println("Value: " + scoreArray[i] + " and index: " + i);
                        }
                        System.out.println("Count: " + count);
                        System.out.println("Array fill: " + arrayFill);

//                        finalScore1.setText("0/1");
                        theView.updateScore("0/1");

                        theView.mainSplash();

                    }
                }
            });
        }
    }

    @Override
    public int getHighScoreA() {
        return scoreArray[5];
    }

    @Override
    public int getHighScoreB() {
        return scoreArray[4];
    }

    @Override
    public int getHighScoreC() {
        return scoreArray[3];
    }

    @Override
    public int getHighScoreD() {
        return scoreArray[2];
    }

    @Override
    public int getHighScoreE() {
        return scoreArray[1];
    }

    private Button[] generateSequence(Button[] btn) {
        //An array is filled with random buttons. The order of these buttons will be the sequence.
        Button[] btnSeq = new Button[1000];
        for (int l = 0; l < 1000; l++) {
            Random random = new Random();
            int n = random.nextInt(9);
            btnSeq[l] = btn[n];
            //System.out.println(n);//<-To cheat with when solving the puzzle.
        }
        return btnSeq;
    }

}
