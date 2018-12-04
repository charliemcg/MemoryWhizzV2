package com.violenthoboenterprises.memorygame;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.violenthoboenterprises.memorygame.model.HighScore;
import com.violenthoboenterprises.memorygame.model.HighScoreAdapter;
import com.violenthoboenterprises.memorygame.model.HighScoreViewModel;
import com.violenthoboenterprises.memorygame.model.ThePresenterImpl;
import com.violenthoboenterprises.memorygame.presenter.ThePresenter;
import com.violenthoboenterprises.memorygame.view.TheView;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created on 18/12/2016.
 */
//MainActivity is mainly for UI events such as getting user input and displaying UI changes
public class MainActivity extends AppCompatActivity implements TheView {

    private static String TAG = "MainActivity";

    //Presenter is the interface with the business logic methods
    private ThePresenter thePresenter;
    //ViewModel is used for data persistence
    private HighScoreViewModel highScoreViewModel;

    public SharedPreferences preferences;
    public static String MUSIC_STATE_KEY = "key";

    //Initializing interstitial ad.
    InterstitialAd interstitialAd;
    AdRequest intRequest;
    //Initializing banner ad.
    AdView bannerAd;
    AdRequest banRequest;

    //Music is playing by default
    static boolean playMusic;
    //Back button shows a warning on click but only during game play
    boolean exitGame = false;
    //Flag used on all sound effects.
    boolean clickOnOff = true;
    //Used to prevent "new high score" message from showing up more than once during a game
    boolean showOnce;

    //On click sound
    MediaPlayer backgroundMusic;
    MediaPlayer highScoreJingle;
    MediaPlayer[] buttonClick;

    //The nine buttons used for game play
    Button[] btn;
    Button splashPlay;
    Button splashHighScores;

    TextView score;
    TextView pointsEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        thePresenter = new ThePresenterImpl(MainActivity.this,
                this.getApplicationContext());
        highScoreViewModel = ViewModelProviders.of(this).get(HighScoreViewModel.class);

        preferences = this.getSharedPreferences(
                "com.violenthoboenterprises.memorygame", Context.MODE_PRIVATE);

        //Initializing interstitial ad.
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        intRequest = new AdRequest.Builder()/*.addTestDevice
                (AdRequest.DEVICE_ID_EMULATOR)*/.build();
        //Initializing banner ad.
        bannerAd = findViewById(R.id.bannerAdView);
//        banRequest = new AdRequest.Builder().build();//TODO enable this line and remove below line
        banRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        bannerAd.loadAd(banRequest);

        playMusic = preferences.getBoolean(MUSIC_STATE_KEY, true);
        showOnce = true;
        //High score sound
        highScoreJingle = MediaPlayer.create(this, R.raw.highscorejingle);
        highScoreJingle.setVolume(0.5f, 0.5f);
        //Initialising background music
        backgroundMusic = MediaPlayer.create(this, R.raw.memorygametheme);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f, 0.3f);
        backgroundMusic.start();

        score = findViewById(R.id.score);
        pointsEarned = findViewById(R.id.points);
        pointsEarned.setText("0");

        //Each button is initialised and stored in an array.
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

        //Initializing array of button sounds.
        buttonClick = new MediaPlayer[9];

        buttonClick[8] = MediaPlayer.create(this, R.raw.btn1);
        buttonClick[7] = MediaPlayer.create(this, R.raw.btn2);
        buttonClick[6] = MediaPlayer.create(this, R.raw.btn3);
        buttonClick[5] = MediaPlayer.create(this, R.raw.btn4);
        buttonClick[4] = MediaPlayer.create(this, R.raw.btn5);
        buttonClick[3] = MediaPlayer.create(this, R.raw.btn6);
        buttonClick[2] = MediaPlayer.create(this, R.raw.btn7);
        buttonClick[1] = MediaPlayer.create(this, R.raw.btn8);
        buttonClick[0] = MediaPlayer.create(this, R.raw.btn9);

        mainSplash();
    }

    //Turns button white for 400 milliseconds with click sound.
    public void seqAnimate(final Button btnSeq, final Button btn[]) {
        //Buttons are disabled while the sequence plays
        for (Button aBtn : btn) {
            aBtn.setEnabled(false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnSeq.setBackgroundColor(Color.WHITE);
                if (clickOnOff) {
                    if (btnSeq.equals(btn[0])) {
                        buttonClick[0].start();
                    } else if (btnSeq.equals(btn[1])) {
                        buttonClick[1].start();
                    } else if (btnSeq.equals(btn[2])) {
                        buttonClick[2].start();
                    } else if (btnSeq.equals(btn[3])) {
                        buttonClick[3].start();
                    } else if (btnSeq.equals(btn[4])) {
                        buttonClick[4].start();
                    } else if (btnSeq.equals(btn[5])) {
                        buttonClick[5].start();
                    } else if (btnSeq.equals(btn[6])) {
                        buttonClick[6].start();
                    } else if (btnSeq.equals(btn[7])) {
                        buttonClick[7].start();
                    } else if (btnSeq.equals(btn[8])) {
                        buttonClick[8].start();
                    }
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

    //When clicked, button turns white for a short time and makes a click noise.
    @Override
    public void clickAnimate(final Button theBtn) {
        theBtn.setBackgroundColor(Color.WHITE);
        if (clickOnOff) {
            if (theBtn.equals(btn[0])) {
                buttonClick[0].start();
            } else if (theBtn.equals(btn[1])) {
                buttonClick[1].start();
            } else if (theBtn.equals(btn[2])) {
                buttonClick[2].start();
            } else if (theBtn.equals(btn[3])) {
                buttonClick[3].start();
            } else if (theBtn.equals(btn[4])) {
                buttonClick[4].start();
            } else if (theBtn.equals(btn[5])) {
                buttonClick[5].start();
            } else if (theBtn.equals(btn[6])) {
                buttonClick[6].start();
            } else if (theBtn.equals(btn[7])) {
                buttonClick[7].start();
            } else if (theBtn.equals(btn[8])) {
                buttonClick[8].start();
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                theBtn.setBackgroundColor(Color.CYAN);
            }
        }, 50);
    }

    //Display the updated score
    @Override
    public void updateScore(String theScore) {
        score.setText("Buttons: " + theScore);
    }

    @Override
    public void updatePoints(int points) {
        pointsEarned.setText(String.valueOf("Points: " + points));
    }

    //Checks if new high score is achieved
    @Override
    public void checkHighScore(int i, int highScore) {
        if (i > highScore) {
            //Only informs of high score once per game
            if (showOnce) {
                highScoreJingle.start();
                String newScoreString = "    New High Score!    ";
                showToast(newScoreString);
                showOnce = false;
            }
            thePresenter.updateHighScore(i);
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
        final Button btnMusic = dialog.findViewById(R.id.splashMusic);

        score.setVisibility(GONE);
        pointsEarned.setVisibility(GONE);

        if (playMusic) {
            btnMusic.setText(R.string.music_on);
            backgroundMusic.setVolume(1.0f, 1.0f);
            System.out.println("Music on");
        } else {
            btnMusic.setText(R.string.music_off);
            backgroundMusic.setVolume(0.0f, 0.0f);
            System.out.println("Music off");
        }

        //Begin game play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateScore("0/1");
                updatePoints(0);
                showOnce = true;
                score.setVisibility(VISIBLE);
                pointsEarned.setVisibility(VISIBLE);

                //Gameplay business logic is handled in the presenter
                thePresenter.sequence(btn, highScoreViewModel);
            }
        });

        //show high score
        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                highscoresSplash();
            }
        });

        //show audio options
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playMusic) {
                    btnMusic.setText(R.string.music_off);
                    backgroundMusic.setVolume(0.0f, 0.0f);
                    System.out.println("Music off");
                    preferences.edit().putBoolean(MainActivity.MUSIC_STATE_KEY, false).apply();
                    playMusic = false;
                } else {
                    btnMusic.setText(R.string.music_on);
                    backgroundMusic.setVolume(1.0f, 1.0f);
                    System.out.println("Music on");
                    preferences.edit().putBoolean(MainActivity.MUSIC_STATE_KEY, true).apply();
                    playMusic = true;
                }

            }
        });
        dialog.show();
    }

    @Override
    public void showToast(String buttonsRememberedString) {
        Toast toast = Toast.makeText(MainActivity.this, "    " + buttonsRememberedString + "    ",
                Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastMessage = toastView.findViewById(android.R.id.message);
        //Toast customization
        toastMessage.setTextColor(Color.BLACK);
        toastView.setBackgroundColor(Color.CYAN);
        toast.show();
    }

    @Override
    public void toggleBanner(boolean showBanner) {
        //Banner ad is disabled during game play.
        if(!showBanner) {
            bannerAd.setVisibility(GONE);
        }else{
            bannerAd.setVisibility(VISIBLE);
        }
    }

    private void highscoresSplash() {

        //inflate the dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.splash_high_scores);

        final Button highScoresClose = dialog.findViewById(R.id.closeHighScores);

        //display highscores in recycler view
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final HighScoreAdapter adapter = new HighScoreAdapter();
        recyclerView.setAdapter(adapter);

        //Getting high scores from the viewmodel for data persistence reasons
        highScoreViewModel = ViewModelProviders.of(this)
                .get(HighScoreViewModel.class);
        highScoreViewModel.getAllHighScores().observe(this, new Observer<List<HighScore>>() {
            @Override
            public void onChanged(@Nullable List<HighScore> highScores) {
                adapter.setHighScores(highScores);
            }
        });
        //return to main screen
        highScoresClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                mainSplash();
            }
        });
        dialog.show();
    }

    //When back button pressed in options splash screen the user is returned to
    // the main menu splash screen
    @Override
    public void onBackPressed() {
        if (!exitGame) {
            String pressBackString = "    Press 'back' again to exit    ";
            showToast(pressBackString);
            exitGame = true;
        } else {
            super.onBackPressed();
        }
    }
}