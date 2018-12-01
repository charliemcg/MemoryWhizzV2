package com.violenthoboenterprises.memorygame;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.violenthoboenterprises.memorygame.R.id.score;

public class MainActivity extends AppCompatActivity {

    public static int[] highScore = {1};
    public static int[] scoreArray = new int[6];
    boolean arrayFill = false;
    int count = 0;
    static boolean playMusic = true;

    boolean exitGame = false;

    boolean splashBack = false;

    boolean clickOnOff = true;//<-Flag used on all sound effects.

    boolean highScoresEnabled;

    //On click sound
    MediaPlayer buttonClick;
    MediaPlayer backgroundMusic;

    Button[] btn;

    Button splashPlay;
    Button splashHighScores;
    Button splashOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        highScoresEnabled = true;

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick);

        //Initializing splash screen
//        final Button splash = findViewById(R.id.splash);

        //Initializing play button on splash screen
//        splashPlay = findViewById(R.id.splashPlay);

        //Initializing high scores button on splash screen
//        splashHighScores = findViewById(R.id.splashHighScores);

        //Initializing options button on splash screen
//        splashOptions = findViewById(R.id.splashOptions);

        //Initializing sound effects button on splash screen
//        final Button splashSoundEffects = findViewById(R.id.splashSoundEffects);

        //Initializing music button on splash screen
//        final Button splashMusic = findViewById(R.id.splashMusic);

        /*//Goes to play mode when "Play" clicked
        splashPlay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(Options.clickOnOff == true){
                    buttonClick.start();
                }
                splashPlay.setVisibility(splashPlay.GONE);
                splashHighScores.setVisibility(splashHighScores.GONE);
                splashOptions.setVisibility(splashOptions.GONE);
                splash.setVisibility(splash.GONE);
            }
        });*/

        //Goes to high scores when "High Scores" clicked
//        splashHighScores.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                //Initializing high score TextViews
//                final TextView highScoreTextViewA = findViewById(R.id.highScoreTextViewA);
//                final TextView highScoreTextViewB = findViewById(R.id.highScoreTextViewB);
//                final TextView highScoreTextViewC = findViewById(R.id.highScoreTextViewC);
//                final TextView highScoreTextViewD = findViewById(R.id.highScoreTextViewD);
//                final TextView highScoreTextViewE = findViewById(R.id.highScoreTextViewE);
//
//                final int tempA = MainActivity.getHighScoreA();
//                final int tempB = MainActivity.getHighScoreB();
//                final int tempC = MainActivity.getHighScoreC();
//                final int tempD = MainActivity.getHighScoreD();
//                final int tempE = MainActivity.getHighScoreE();
//
//                //TextViews are updated to the latest high scores
//                //High score TextView only shows up once player actually sets one
//                if (tempA == 0 && tempB == 0 && tempC == 0 && tempD == 0 && tempE == 0) {
//                    Toast.makeText(MainActivity.this, "There are no high scores yet", Toast.LENGTH_SHORT).show();
//                    highScoresEnabled[0] = false;
//                }
//                if (tempA != 0) {
//                    highScoreTextViewA.setText(Integer.toString(tempA));
//                    highScoresEnabled[0] = true;
//                } else {
//                    highScoreTextViewA.setText(" ");
//                }
//                if (tempB != 0) {
//                    highScoreTextViewB.setText(Integer.toString(tempB));
//                } else {
//                    highScoreTextViewB.setText(" ");
//                }
//                if (tempC != 0) {
//                    highScoreTextViewC.setText(Integer.toString(tempC));
//                } else {
//                    highScoreTextViewC.setText(" ");
//                }
//                if (tempD != 0) {
//                    highScoreTextViewD.setText(Integer.toString(tempD));
//                } else {
//                    highScoreTextViewD.setText(" ");
//                }
//                if (tempE != 0) {
//                    highScoreTextViewE.setText(Integer.toString(tempE));
//                } else {
//                    highScoreTextViewE.setText(" ");
//                }
//
//                if (highScoresEnabled[0]) {
//                    splashBack = true;
//                    if (clickOnOff) {
//                        buttonClick.start();
//                    }
//                    splashPlay.setVisibility(GONE);
//                    splashHighScores.setVisibility(GONE);
//                    splashOptions.setVisibility(GONE);
//                    highScoreTextViewA.setVisibility(VISIBLE);
//                    highScoreTextViewB.setVisibility(VISIBLE);
//                    highScoreTextViewC.setVisibility(VISIBLE);
//                    highScoreTextViewD.setVisibility(VISIBLE);
//                    highScoreTextViewE.setVisibility(VISIBLE);
//                }
//            }
//        });

        //Goes to options when "Options" clicked
//        splashOptions.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                splashBack = true;
//                System.out.println(clickOnOff);
//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                splashPlay.setVisibility(GONE);
//                splashHighScores.setVisibility(GONE);
//                splashOptions.setVisibility(GONE);
//                splashSoundEffects.setVisibility(VISIBLE);
//                splashMusic.setVisibility(VISIBLE);
//            }
//        });

        //Turn sound effects on or off.
//        if (clickOnOff) {//<-Set correct text when opening screen.
//            splashSoundEffects.setText(R.string.sound_on);
//        } else {
//            splashSoundEffects.setText(R.string.sound_off);
//        }
//        splashSoundEffects.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (clickOnOff) {//<-Enable or disable sound effects and set text accordingly.
//                    splashSoundEffects.setText(R.string.sound_off);
//                    clickOnOff = false;
//                } else {
//                    splashSoundEffects.setText(R.string.sound_on);
//                    buttonClick.start();
//                    clickOnOff = true;
//                }
//            }
//        });

        //Initialising background music
        backgroundMusic = MediaPlayer.create(this, R.raw.demo5);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f, 1.0f);
        backgroundMusic.start();

//        splashMusic.setText(R.string.music_on);

        //Turn music on or off
//        splashMusic.setText(R.string.music_on);
//        splashMusic.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                if (playMusic) {
//                    splashMusic.setText(R.string.music_off);
//                    backgroundMusic.setVolume(0.0f, 0.0f);
//                    System.out.println("Music off");
//                    playMusic = false;
//                } else {
//                    splashMusic.setText(R.string.music_on);
//                    backgroundMusic.setVolume(1.0f, 1.0f);
//                    System.out.println("Music on");
//                    playMusic = true;
//                }
//            }
//        });

        /*if (playMusic){
            backgroundMusic.start();
            System.out.println("Start Music");
            //playMusic = false;
        } else {
            backgroundMusic.stop();
            System.out.println("Stop Music");
            //playMusic = true;
        }*/

        /*if (backgroundMusic.isPlaying() == true){
            System.out.println("Music is playing");
        }else {
            System.out.println("Music is not playing");
        }*/

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

        //Clicking 'play' starts the sequence.
//        splashPlay.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                splashPlay.setVisibility(GONE);
//                splashHighScores.setVisibility(GONE);
//                splashOptions.setVisibility(GONE);
//                splash.setVisibility(GONE);
//                sequence(btn, highScore, buttonClick);
//                findViewById(score).setVisibility(VISIBLE);//<-Shows score
//            }
//        });
        mainSplash();
    }

    //When clicked, button turns white for a jiffy and makes a click noise.
    public void clickAnimate(final Button btn, MediaPlayer click) {

        btn.setBackgroundColor(Color.WHITE);
        if (clickOnOff) {
            click.start();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                btn.setBackgroundColor(Color.CYAN);
            }
        }, 50);
    }

    //The sequence of buttons that the player must remember.
    public void sequence(final Button[] btn, final int highScore[], final MediaPlayer click) {

        //High score sound
        final MediaPlayer highScoreJingle = MediaPlayer.create(this, R.raw.highscorejingle);

        //final int[] highScore = {1};
        final boolean[] showOnce = {true};

        //An array is filled with random buttons. The order of these buttons will be the sequence.
        final Button[] btnSeq = new Button[1000];
        for (int l = 0; l < 1000; l++) {
            Random random = new Random();
            int n = random.nextInt(9);
            btnSeq[l] = btn[n];
            //System.out.println(n);//<-To cheat with when solving the puzzle.
        }

        //Sequence is played by lighting up one button at a time.
        final int[] i = {0};
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                seqAnimate(btnSeq[i[0]], btn, click);
            }
        });

        //TextView score = new TextView(this);
        TextView score = (TextView) findViewById(R.id.score);
        final int[] seqNum = {0};//<-Index number for sequence buttons.
        final int[] k = {1};//<-This is the number of buttons which are so far available in the sequence.
        final String zero = getResources().getString(R.string.zero);
        for (int j = 0; j < 9; j++) {//<-Iterates over the buttons to find which one was clicked.
            final int finalJ = j;
            final TextView finalScore = score;
            final TextView finalScore1 = score;
            btn[finalJ].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickAnimate(btn[finalJ], click);
                    if (btnSeq[seqNum[0]] == v) {//<-Checks if correct button selected.
                        seqNum[0]++;
                        finalScore.setText(seqNum[0] + "/" + k[0]);//<-Updates the score as player progresses
                        if (k[0] == seqNum[0]) {//<-This block entered once player has successfully selected all of the buttons which are so far available.
                            i[0] = 0;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    seqAnimate(btnSeq[i[0]], btn, click);//<-Button sequence is replayed with a new button appended on the end
                                    i[0]++;
                                    if (i[0] < k[0]) {
                                        seqNum[0] = 0;//<-The index number for sequence buttons is reset. The player must now start from the beginning again.
                                        handler.postDelayed(this, 500);
                                    }
                                }
                            });
                            k[0]++;//<-'k' is incremented to allow the player to select one button more than previous.
                            if (k[0] > highScore[0]) {//<-Checks if new high score is achieved
                                highScore[0] = k[0];
                                if (showOnce[0]) {//<-Only informs of high score once per game
                                    highScoreJingle.start();
                                    Toast.makeText(MainActivity.this, "New High Score!", Toast.LENGTH_SHORT).show();
                                    showOnce[0] = false;
                                }
                            }
                            String scoreReset = zero + k[0];
                            finalScore.setText(scoreReset);
                        }
                    } else {
                        //Resets the game by showing the player's final score and returning to menu.
                        if (k[0] != 2) {
                            Toast.makeText(MainActivity.this, "You remembered " + (k[0] - 1) + " buttons", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "You remembered " + (k[0] - 1) + " button", Toast.LENGTH_LONG).show();
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
                                if (scoreArray[i] < highScore[0] - 1) {
                                    scoreArray[i] = highScore[0] - 1;
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
                        //Intent myIntent = new Intent(v.getContext(), Menu.class);
                        //startActivityForResult(myIntent, 0);//<-Closes game screen and returns to menu

//                        Button splash = findViewById(R.id.splash);
//                        splash.setVisibility(VISIBLE);

//                        Button splashPlay = findViewById(R.id.splashPlay);
//                        splashPlay.setVisibility(VISIBLE);

//                        Button splashHighScores = findViewById(R.id.splashHighScores);
//                        splashHighScores.setVisibility(VISIBLE);

//                        Button splashOptions = findViewById(R.id.splashOptions);
//                        splashOptions.setVisibility(VISIBLE);

                        finalScore1.setText("0/1");

                        mainSplash();

                    }
                }
            });
        }
    }


    //Turns button white for 400 milliseconds with click sound. See 'sequence' for more information.
    private void seqAnimate(final Button btnSeq, final Button btn[], final MediaPlayer click) {

        for (Button aBtn : btn) {//<-Buttons are disabled while the sequence plays
            aBtn.setEnabled(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btnSeq.setBackgroundColor(Color.WHITE);
                if (clickOnOff) {
                    click.start();
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

    //Dialog for displaying main menu
    private void mainSplash() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.splash_main);

        final Button btnPlay = dialog.findViewById(R.id.splashPlay);
        final Button btnHighScores = dialog.findViewById(R.id.splashHighScores);
        final Button btnOptions = dialog.findViewById(R.id.splashOptions);
//        final LinearLayout highScoresLayout = findViewById(R.id.highScoresLayout);

        //actions to occur when user clicks 'Play'
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickOnOff) {
                    buttonClick.start();
                }
                sequence(btn, highScore, buttonClick);
                findViewById(score).setVisibility(VISIBLE);//<-Shows score
                dialog.dismiss();

            }
        });

        //actions to occur when user clicks 'High Scores'
        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                //Initializing high score TextViews
//                final TextView hsTextViewA = findViewById(R.id.highScoreTextViewA);
//                final TextView hsTextViewB = findViewById(R.id.highScoreTextViewB);
//                final TextView hsTextViewC = findViewById(R.id.highScoreTextViewC);
//                final TextView hsTextViewD = findViewById(R.id.highScoreTextViewD);
//                final TextView hsTextViewE = findViewById(R.id.highScoreTextViewE);
//
                final int tempA = MainActivity.getHighScoreA();
                final int tempB = MainActivity.getHighScoreB();
                final int tempC = MainActivity.getHighScoreC();
                final int tempD = MainActivity.getHighScoreD();
                final int tempE = MainActivity.getHighScoreE();
//
//                //TextViews are updated to the latest high scores
//                //High score TextView only shows up once player actually sets one
                if (tempA == 0 && tempB == 0 && tempC == 0 && tempD == 0 && tempE == 0) {
                    Toast.makeText(MainActivity.this, "There are no high scores yet",
                            Toast.LENGTH_SHORT).show();
                    highScoresEnabled = false;

//                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    highscoresSplash();
//
//                    if (tempA != 0) {
//                        hsTextViewA.setText(Integer.toString(tempA));
//                        highScoresEnabled = true;
//                    } else {
//                        hsTextViewA.setText(" ");
//                    }
//                    if (tempB != 0) {
//                        hsTextViewB.setText(Integer.toString(tempB));
//                    } else {
//                        hsTextViewB.setText(" ");
//                    }
//                    if (tempC != 0) {
//                        hsTextViewC.setText(Integer.toString(tempC));
//                    } else {
//                        hsTextViewC.setText(" ");
//                    }
//                    if (tempD != 0) {
//                        hsTextViewD.setText(Integer.toString(tempD));
//                    } else {
//                        hsTextViewD.setText(" ");
//                    }
//                    if (tempE != 0) {
//                        hsTextViewE.setText(Integer.toString(tempE));
//                    } else {
//                        hsTextViewE.setText(" ");
//                    }
//                }
//
//                if (highScoresEnabled) {
//                    splashBack = true;
//                    if (clickOnOff) {
//                        buttonClick.start();
//                    }
//
//                    btnPlay.setVisibility(GONE);
//                    btnHighScores.setVisibility(GONE);
//                    btnOptions.setVisibility(GONE);
////                    splashPlay.setVisibility(GONE);
////                    splashHighScores.setVisibility(GONE);
////                    splashOptions.setVisibility(GONE);
////                    highScoreTextViewA.setVisibility(VISIBLE);
////                    highScoreTextViewB.setVisibility(VISIBLE);
////                    highScoreTextViewC.setVisibility(VISIBLE);
////                    highScoreTextViewD.setVisibility(VISIBLE);
////                    highScoreTextViewE.setVisibility(VISIBLE);
                }

            }
        });

        //actions to occur when user clicks 'Options'
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                splashBack = true;
//                System.out.println(clickOnOff);
//                if (clickOnOff) {
//                    buttonClick.start();
//                }
//                splashPlay.setVisibility(GONE);
//                splashHighScores.setVisibility(GONE);
//                splashOptions.setVisibility(GONE);
//                splashSoundEffects.setVisibility(VISIBLE);
//                splashMusic.setVisibility(VISIBLE);
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
        final TextView hsTextViewA = dialog.findViewById(R.id.highScoreTextViewA);
        final TextView hsTextViewB = dialog.findViewById(R.id.highScoreTextViewB);
        final TextView hsTextViewC = dialog.findViewById(R.id.highScoreTextViewC);
        final TextView hsTextViewD = dialog.findViewById(R.id.highScoreTextViewD);
        final TextView hsTextViewE = dialog.findViewById(R.id.highScoreTextViewE);

        final int tempA = MainActivity.getHighScoreA();
        final int tempB = MainActivity.getHighScoreB();
        final int tempC = MainActivity.getHighScoreC();
        final int tempD = MainActivity.getHighScoreD();
        final int tempE = MainActivity.getHighScoreE();

        final Button highScoresClose = dialog.findViewById(R.id.closeHighScores);

        //TextViews are updated to the latest high scores
        //High score TextView only shows up once player actually sets one
        if (tempA == 0 && tempB == 0 && tempC == 0 && tempD == 0 && tempE == 0) {
            Toast.makeText(MainActivity.this, "There are no high scores yet",
                    Toast.LENGTH_SHORT).show();
            highScoresEnabled = false;
        } else {

            if (tempA != 0) {
                hsTextViewA.setText(Integer.toString(tempA));
                highScoresEnabled = true;
            } else {
                hsTextViewA.setText(" ");
            }
            if (tempB != 0) {
                hsTextViewB.setText(Integer.toString(tempB));
            } else {
                hsTextViewB.setText(" ");
            }
            if (tempC != 0) {
                hsTextViewC.setText(Integer.toString(tempC));
            } else {
                hsTextViewC.setText(" ");
            }
            if (tempD != 0) {
                hsTextViewD.setText(Integer.toString(tempD));
            } else {
                hsTextViewD.setText(" ");
            }
            if (tempE != 0) {
                hsTextViewE.setText(Integer.toString(tempE));
            } else {
                hsTextViewE.setText(" ");
            }
        }

        if (highScoresEnabled) {
            splashBack = true;
            if (clickOnOff) {
                buttonClick.start();
            }
        }

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

        splashBack = true;
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

    public static int getHighScoreA() {
        return scoreArray[5];
    }

    public static int getHighScoreB() {
        return scoreArray[4];
    }

    public static int getHighScoreC() {
        return scoreArray[3];
    }

    public static int getHighScoreD() {
        return scoreArray[2];
    }

    public static int getHighScoreE() {
        return scoreArray[1];
    }

    public static MediaPlayer getButtonClick() {
        MediaPlayer x = getButtonClick();
        return x;
    }

    /*public static MediaPlayer getBackgroundMusic(){
        MediaPlayer x = getBackgroundMusic();
        return x;
    }*/

    //When back button pressed in options splash screen the user is returned to
    // the main menu splash screen
    @Override
    public void onBackPressed() {

        if (splashBack) {

//            Button splashPlay = findViewById(R.id.splashPlay);
//            splashPlay.setVisibility(VISIBLE);

//            Button splashHighScores = findViewById(R.id.splashHighScores);
//            splashHighScores.setVisibility(VISIBLE);

//            Button splashOptions = findViewById(R.id.splashOptions);
//            splashOptions.setVisibility(VISIBLE);

//            Button splashSoundEffects = findViewById(R.id.splashSoundEffects);
//            splashSoundEffects.setVisibility(GONE);

//            Button splashMusic = findViewById(R.id.splashMusic);
//            splashMusic.setVisibility(GONE);

//            TextView highScoreTextViewA = findViewById(R.id.highScoreTextViewA);
//            highScoreTextViewA.setVisibility(GONE);
//
//            TextView highScoreTextViewB = findViewById(R.id.highScoreTextViewB);
//            highScoreTextViewB.setVisibility(GONE);
//
//            TextView highScoreTextViewC = findViewById(R.id.highScoreTextViewC);
//            highScoreTextViewC.setVisibility(GONE);
//
//            TextView highScoreTextViewD = findViewById(R.id.highScoreTextViewD);
//            highScoreTextViewD.setVisibility(GONE);
//
//            TextView highScoreTextViewE = findViewById(R.id.highScoreTextViewE);
//            highScoreTextViewE.setVisibility(GONE);

            mainSplash();

            splashBack = false;
        } else if (!exitGame) {
            Toast.makeText(MainActivity.this, "Press 'back' again to exit", Toast.LENGTH_LONG).show();
            exitGame = true;
        } else {
            super.onBackPressed();
        }
    }
}