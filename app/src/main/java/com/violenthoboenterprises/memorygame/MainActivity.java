package com.violenthoboenterprises.memorygame;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.violenthoboenterprises.memorygame.model.HighScore;
import com.violenthoboenterprises.memorygame.model.HighScoreAdapter;
import com.violenthoboenterprises.memorygame.model.HighScoreViewModel;
import com.violenthoboenterprises.memorygame.model.ThePresenterImpl;
import com.violenthoboenterprises.memorygame.presenter.ThePresenter;
import com.violenthoboenterprises.memorygame.view.TheView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.violenthoboenterprises.memorygame.R.id.score;

public class MainActivity extends AppCompatActivity implements TheView {

    private ThePresenter thePresenter;
    private HighScoreViewModel highScoreViewModel;

    static boolean playMusic = true;

    boolean exitGame = false;

//    boolean splashBack = false;

    //Flag used on all sound effects.
    boolean clickOnOff = true;

    boolean showOnce;

    boolean highScoresEnabled;

    //On click sound
    MediaPlayer buttonClick;
    MediaPlayer backgroundMusic;
    MediaPlayer highScoreJingle;

    Button[] btn;

    Button splashPlay;
    Button splashHighScores;
    Button splashOptions;

    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        thePresenter = new ThePresenterImpl(MainActivity.this, this.getApplicationContext());

        highScoresEnabled = true;

        showOnce = true;

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick);

        //High score sound
        highScoreJingle = MediaPlayer.create(this, R.raw.highscorejingle);

        highScoreViewModel = ViewModelProviders.of(this).get(HighScoreViewModel.class);

//        highScore = 1;

//        scoreArray = new int[6];

        //Initialising background music
        backgroundMusic = MediaPlayer.create(this, R.raw.demo5);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f, 1.0f);
        backgroundMusic.start();

        score = findViewById(R.id.score);

        //Each button is declared and stored in an array.
        final Button buttonA = this.findViewById(R.id.buttonA);
        final Button buttonB = this.findViewById(R.id.buttonB);
        final Button buttonC = this.findViewById(R.id.buttonC);
        final Button buttonD = this.findViewById(R.id.buttonD);
        final Button buttonE = this.findViewById(R.id.buttonE);
        final Button buttonF = this.findViewById(R.id.buttonF);
        final Button buttonG = this.findViewById(R.id.buttonG);
        final Button buttonH = this.findViewById(R.id.buttonH);
        final Button buttonI = this.findViewById(R.id.buttonI);

        btn = new Button[]{buttonA, buttonB, buttonC, buttonD, buttonE,
                buttonF, buttonG, buttonH, buttonI};

        mainSplash();
    }

    //Turns button white for 400 milliseconds with click sound. See 'sequence' for more information.
    public void seqAnimate(final Button btnSeq, final Button btn[]/*, final MediaPlayer click*/) {

        for (Button aBtn : btn) {//<-Buttons are disabled while the sequence plays
            aBtn.setEnabled(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btnSeq.setBackgroundColor(Color.WHITE);
                if (clickOnOff) {
                    buttonClick.start();
                }

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        btnSeq.setBackgroundColor(Color.CYAN);
                    }
                }, 400);
                for (Button aBtn : btn) {
                    aBtn.setEnabled(true);
                }
            }
        }, 500);

    }

    //When clicked, button turns white for a jiffy and makes a click noise.
    @Override
    public void clickAnimate(final Button btn) {
        btn.setBackgroundColor(Color.WHITE);
        if (clickOnOff) {
            buttonClick.start();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                btn.setBackgroundColor(Color.CYAN);
            }
        }, 50);
    }

    @Override
    public void updateScore(String theScore) {
        score.setText(theScore);
    }

    @Override
    public void checkHighScore(int i, int highScore) {
        if (i > highScore) {//<-Checks if new high score is achieved
            if (showOnce) {//<-Only informs of high score once per game
                highScoreJingle.start();
                Toast.makeText(this, "New High Score!", Toast.LENGTH_SHORT).show();
                showOnce = false;
            }
        }
    }

    //Dialog for displaying main menu
    public void mainSplash() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.splash_main);

        final Button btnPlay = dialog.findViewById(R.id.splashPlay);
        final Button btnHighScores = dialog.findViewById(R.id.splashHighScores);
        final Button btnOptions = dialog.findViewById(R.id.splashOptions);

        //actions to occur when user clicks 'Play'
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickOnOff) {
                    buttonClick.start();
                }
                thePresenter.sequence(btn, highScoreViewModel/*, highScore, buttonClick*/);
                score.setVisibility(VISIBLE);//<-Shows score
                dialog.dismiss();

            }
        });

        //actions to occur when user clicks 'High Scores'
        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int tempA = thePresenter.getHighScoreA();
//                int tempB = thePresenter.getHighScoreB();
//                int tempC = thePresenter.getHighScoreC();
//                int tempD = thePresenter.getHighScoreD();
//                int tempE = thePresenter.getHighScoreE();

                //TextViews are updated to the latest high scores
                //High score TextView only shows up once player actually sets one
//                if (tempA == 0 && tempB == 0 && tempC == 0 && tempD == 0 && tempE == 0) {
//                    Toast.makeText(MainActivity.this, "There are no high scores yet",
//                            Toast.LENGTH_SHORT).show();
//                    highScoresEnabled = false;
//
//                } else {
                    dialog.dismiss();
                    highscoresSplash();

//                }

            }
        });

        //actions to occur when user clicks 'Options'
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundEffectsSplash();
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    private void highscoresSplash() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.splash_high_scores);

        if (clickOnOff) {
            buttonClick.start();
        }
        //Initializing high score TextViews
//        final TextView hsTextViewA = dialog.findViewById(R.id.highScoreTextViewA);
//        final TextView hsTextViewB = dialog.findViewById(R.id.highScoreTextViewB);
//        final TextView hsTextViewC = dialog.findViewById(R.id.highScoreTextViewC);
//        final TextView hsTextViewD = dialog.findViewById(R.id.highScoreTextViewD);
//        final TextView hsTextViewE = dialog.findViewById(R.id.highScoreTextViewE);

//        int tempA = thePresenter.getHighScoreA();
//        int tempB = thePresenter.getHighScoreB();
//        int tempC = thePresenter.getHighScoreC();
//        int tempD = thePresenter.getHighScoreD();
//        int tempE = thePresenter.getHighScoreE();

        final Button highScoresClose = dialog.findViewById(R.id.closeHighScores);

        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final HighScoreAdapter adapter = new HighScoreAdapter();
        recyclerView.setAdapter(adapter);

        highScoreViewModel = ViewModelProviders.of(this)
                .get(HighScoreViewModel.class);
        highScoreViewModel.getAllHighScores().observe(this, new Observer<List<HighScore>>() {
            @Override
            public void onChanged(@Nullable List<HighScore> highScores) {
                adapter.setHighScores(highScores);
            }
        });

        //TextViews are updated to the latest high scores
        //High score TextView only shows up once player actually sets one
//        if (tempA == 0 && tempB == 0 && tempC == 0 && tempD == 0 && tempE == 0) {
//            Toast.makeText(MainActivity.this, "There are no high scores yet",
//                    Toast.LENGTH_SHORT).show();
//            highScoresEnabled = false;
//        } else {
//
//            if (tempA != 0) {
////                hsTextViewA.setText(Integer.toString(tempA));
//                highScoresEnabled = true;
//            } else {
////                hsTextViewA.setText(" ");
//            }
//            if (tempB != 0) {
////                hsTextViewB.setText(Integer.toString(tempB));
//            } else {
////                hsTextViewB.setText(" ");
//            }
//            if (tempC != 0) {
////                hsTextViewC.setText(Integer.toString(tempC));
//            } else {
////                hsTextViewC.setText(" ");
//            }
//            if (tempD != 0) {
////                hsTextViewD.setText(Integer.toString(tempD));
//            } else {
////                hsTextViewD.setText(" ");
//            }
//            if (tempE != 0) {
////                hsTextViewE.setText(Integer.toString(tempE));
//            } else {
////                hsTextViewE.setText(" ");
//            }
//        }

//        if (highScoresEnabled) {
//            splashBack = true;
            if (clickOnOff) {
                buttonClick.start();
            }
//        }

        highScoresClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                mainSplash();
            }
        });

        dialog.show();

    }

    private void soundEffectsSplash() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.splash_sound);

        final Button splashSoundEffects = dialog.findViewById(R.id.splashSoundEffects);
        final Button splashMusic = dialog.findViewById(R.id.splashMusic);
        final Button soundClose = dialog.findViewById(R.id.soundClose);

//        splashBack = true;
        if (clickOnOff) {
            buttonClick.start();
        }

        //Turn sound effects on or off.
        if (clickOnOff) {//<-Set correct text when opening screen.
            splashSoundEffects.setText(R.string.sound_on);
        } else {
            splashSoundEffects.setText(R.string.sound_off);
        }
        splashSoundEffects.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clickOnOff) {//<-Enable or disable sound effects and set text accordingly.
                    splashSoundEffects.setText(R.string.sound_off);
                    clickOnOff = false;
                } else {
                    splashSoundEffects.setText(R.string.sound_on);
                    buttonClick.start();
                    clickOnOff = true;
                }
            }
        });

        //Turn music on or off
        splashMusic.setText(R.string.music_on);
        splashMusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clickOnOff) {
                    buttonClick.start();
                }
                if (playMusic) {
                    splashMusic.setText(R.string.music_off);
                    backgroundMusic.setVolume(0.0f, 0.0f);
                    System.out.println("Music off");
                    playMusic = false;
                } else {
                    splashMusic.setText(R.string.music_on);
                    backgroundMusic.setVolume(1.0f, 1.0f);
                    System.out.println("Music on");
                    playMusic = true;
                }
            }
        });

        soundClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                mainSplash();
            }
        });

        dialog.show();

    }

    public static MediaPlayer getButtonClick() {
        MediaPlayer x = getButtonClick();
        return x;
    }

    //When back button pressed in options splash screen the user is returned to
    // the main menu splash screen
    @Override
    public void onBackPressed() {

        /*if (splashBack) {

            mainSplash();

            splashBack = false;
        } else */if (!exitGame) {
            Toast.makeText(MainActivity.this, "Press 'back' again to exit",
                    Toast.LENGTH_LONG).show();
            exitGame = true;
        } else {
            super.onBackPressed();
        }
    }
}