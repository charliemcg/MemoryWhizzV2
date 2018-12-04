package com.violenthoboenterprises.memorygame.model;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.violenthoboenterprises.memorygame.MainActivity;
import com.violenthoboenterprises.memorygame.R;
import com.violenthoboenterprises.memorygame.presenter.ThePresenter;
import com.violenthoboenterprises.memorygame.view.TheView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import static android.view.View.GONE;

public class ThePresenterImpl implements ThePresenter {

    private static String TAG = "ThePresenterImpl";
    public SharedPreferences preferences;
    public static String HIGH_SCORE_KEY = "score_key";
    private TheView theView;
    private Context context;

//    //Initializing interstitial ad.
//    InterstitialAd interstitialAd;
//    AdRequest intRequest;
//    //Initializing banner ad.
//    AdView bannerAd;
//    AdRequest banRequest;

    public static int highScore;
    MediaPlayer fail;
    private int points = 0;
    private int multiplier = 1;
    //Used to display all intermittent values during all live score updates.
    private int intermittentValue;

    //Is incremented with every game play. Interstitial shows when certain values are reached.
    int showInterstitial= 0;

    public ThePresenterImpl(TheView theView, Context context) {
        this.theView = theView;
        this.context = context;
        //Initializing fail sound.
        fail = MediaPlayer.create(context.getApplicationContext(), R.raw.fail);
    }

    @Override
    public void sequence(final Button[] btn, final HighScoreViewModel highScoreViewModel) {

        //Initializing interstitial ad.
        final InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_ad_unit_id));
        final AdRequest intRequest = new AdRequest.Builder()/*.addTestDevice
                (AdRequest.DEVICE_ID_EMULATOR)*/.build();

        //New interstitial ad is loaded.
        interstitialAd.loadAd(intRequest);

//        //Initializing interstitial ad.
//        interstitialAd = new InterstitialAd(context);
//        interstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_ad_unit_id));
//        intRequest = new AdRequest.Builder()/*.addTestDevice
//                (AdRequest.DEVICE_ID_EMULATOR)*/.build();
//        //Initializing banner ad.
//        bannerAd = theView.findViewById(R.id.adView);
//        banRequest = new AdRequest.Builder()/*.addTestDevice
//                (AdRequest.DEVICE_ID_EMULATOR)*/.build();
//        bannerAd.loadAd(banRequest);

        theView.toggleBanner(false);

        final Button[] btnSeq = generateSequence(btn);

        preferences = context.getSharedPreferences(
                "com.violenthoboenterprises.memorygame", Context.MODE_PRIVATE);

        highScore = preferences.getInt(HIGH_SCORE_KEY, 1);

//        //Banner ad is disabled during game play.
//        bannerAd.setVisibility(GONE);
//
//        //New interstitial ad is loaded.
//        interstitialAd.loadAd(intRequest);

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
                            buttonsRememberedString = "You remembered " + (k[0] - 1) + " buttons";
                        } else {
                            buttonsRememberedString = "You remembered " + (k[0] - 1) + " button";
                        }
                        theView.showToast(buttonsRememberedString);
                        for (Button aBtn : btn) {
                            aBtn.setEnabled(false);
                        }

                        int minScore = highScoreViewModel.getMinScore();
                        HighScore minHighScore = highScoreViewModel.getMinHighScore(minScore);

                        if (minScore < /*(k[0] - 1)*/points) {
                            Calendar cal = Calendar.getInstance();
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int month = cal.get(Calendar.MONTH) + 1;
                            int year = cal.get(Calendar.YEAR);
                            String date = day + "/" + month + "/" + year;
                            highScoreViewModel.update(minHighScore);
//                            minHighScore.setScore(k[0] - 1);
                            minHighScore.setScore(points - 1);
                            minHighScore.setDate(date);
                        }

                        points = 0;
                        multiplier = 1;
                        intermittentValue = 0;

                        //Interstitial is displayed after the 1st, 5th and 20th game.
                        if(interstitialAd.isLoaded() && (showInterstitial == 1 ||
                                showInterstitial == 7 || showInterstitial == 20)){
                            interstitialAd.show();
                        }

                        //Incremented after every game to determine when interstitials
                        //should be shown.
                        showInterstitial++;

                        theView.updateScore("");
                        theView.updatePoints(0);

                        theView.mainSplash();

                    }
                }
            });

        }
    }

    @Override
    public void updateHighScore(int i) {
        preferences.edit().putInt(HIGH_SCORE_KEY, i).apply();
        highScore = i;
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
