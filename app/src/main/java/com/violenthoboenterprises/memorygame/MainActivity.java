package com.violenthoboenterprises.memorygame;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import static android.R.attr.background;
import static android.R.attr.tag;
import static android.R.attr.x;
import static com.violenthoboenterprises.memorygame.R.id.score;

public class MainActivity extends AppCompatActivity {
    public static int[] highScore = {1};
    public static int[] scoreArray = new int[6];
    boolean arrayFill = false;
    int count = 0;
    //static boolean playMusic = false;

    boolean splashBack = false;

    boolean clickOnOff = true;//<-Flag used on all sound effects.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //On click sound
        final MediaPlayer buttonClick = MediaPlayer.create(this, R.raw.buttonclick);

        //Initializing splash screen
        final Button splash = (Button) findViewById(R.id.splash);

        //Initializing play button on splash screen
        final Button splashPlay = (Button) findViewById(R.id.splashPlay);

        //Initializing high scores button on splash screen
        final Button splashHighScores = (Button) findViewById(R.id.splashHighScores);

        //Initializing options button on splash screen
        final Button splashOptions = (Button) findViewById(R.id.splashOptions);

        //Initializing sound effects button on splash screen
        final Button splashSoundEffects = (Button) findViewById(R.id.splashSoundEffects);

        //Initializing music button on splash screen
        final Button splashMusic = (Button) findViewById(R.id.splashMusic);

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
        splashHighScores.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(clickOnOff == true){
                    buttonClick.start();
                }
                Intent myIntent = new Intent(v.getContext(), HighScores.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Goes to options when "Options" clicked
        splashOptions.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                splashBack = true;
                if(clickOnOff == true){
                    buttonClick.start();
                }
                splashPlay.setVisibility(splashPlay.GONE);
                splashHighScores.setVisibility(splashHighScores.GONE);
                splashOptions.setVisibility(splashOptions.GONE);
                splashSoundEffects.setVisibility(splashSoundEffects.VISIBLE);
                splashMusic.setVisibility(splashMusic.VISIBLE);
            }
        });

        //Turn sound effects on or off.
        if(clickOnOff == true){//<-Set correct text when opening screen.
            splashSoundEffects.setText("Sound FX: On");
        }else{
            splashSoundEffects.setText("Sound FX: Off");
        }
        splashSoundEffects.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(clickOnOff == true){//<-Enable or disable sound effects and set text accordingly.
                    splashSoundEffects.setText("Sound FX: Off");
                    clickOnOff = false;
                }else{
                    splashSoundEffects.setText("Sound FX: On");
                    buttonClick.start();
                    clickOnOff = true;
                }
            }
        });

        //Turn music on or off
        splashMusic.setText("Music: On");
        splashMusic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(clickOnOff == true){
                    buttonClick.start();
                }
            }
        });

        /*MediaPlayer backgroundMusic = MediaPlayer.create(this, R.raw.demo5);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(100, 100);

        if (playMusic == false){
            backgroundMusic.start();
            playMusic = true;
        }

        if (backgroundMusic.isPlaying() == true){
            System.out.println("Music is playing");
        }else {
            System.out.println("Music is not playing");
        }*/

        //Each button is declared and stored in an array.
        final Button buttonA = (Button) this.findViewById(R.id.buttonA);
        final Button buttonB = (Button) this.findViewById(R.id.buttonB);
        final Button buttonC = (Button) this.findViewById(R.id.buttonC);
        final Button buttonD = (Button) this.findViewById(R.id.buttonD);
        final Button buttonE = (Button) this.findViewById(R.id.buttonE);
        final Button buttonF = (Button) this.findViewById(R.id.buttonF);
        final Button buttonG = (Button) this.findViewById(R.id.buttonG);
        final Button buttonH = (Button) this.findViewById(R.id.buttonH);
        final Button buttonI = (Button) this.findViewById(R.id.buttonI);

        final Button[] btn = new Button[]{buttonA, buttonB, buttonC, buttonD, buttonE,
                                            buttonF, buttonG, buttonH, buttonI};

        //Clicking 'play' starts the sequence.
        splashPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(clickOnOff == true){
                    buttonClick.start();
                }
                splashPlay.setVisibility(splashPlay.GONE);
                splashHighScores.setVisibility(splashHighScores.GONE);
                splashOptions.setVisibility(splashOptions.GONE);
                splash.setVisibility(splash.GONE);
                sequence(btn, highScore, buttonClick);
                findViewById(score).setVisibility(findViewById(score).VISIBLE);//<-Shows score
            }
        });
    }

    //When clicked, button turns white for a jiffy and makes a click noise.
    public void clickAnimate(final Button btn, MediaPlayer click){

        btn.setBackgroundColor(Color.WHITE);
        if(clickOnOff == true){
            click.start();
        }

        new Handler().postDelayed(new Runnable(){
            public void run(){
                btn.setBackgroundColor(Color.BLACK);
            }
        }, 50);
    }

    //The sequence of buttons that the player must remember.
    public void sequence(final Button[] btn, final int highScore[], final MediaPlayer click){

        //final int[] highScore = {1};
        final boolean[] showOnce = {true};

        //An array is filled with random buttons. The order of these buttons will be the sequence.
        final Button[] btnSeq = new Button[1000];
        for(int l = 0; l < 1000; l++){
            Random random = new Random();
            int n = random.nextInt(9);
            btnSeq[l] = btn[n];
            //System.out.println(n);//<-To cheat with when solving the puzzle.
        }

        //Sequence is played by lighting up one button at a time.
        final int[] i = {0};
        final Handler handler = new Handler();

        handler.post(new Runnable(){
            @Override
            public void run(){
                seqAnimate(btnSeq[i[0]], btn, click);
            }
        });

        TextView score = new TextView(this);
        score = (TextView) findViewById(R.id.score);
        final int[] seqNum = {0};//<-Index number for sequence buttons.
        final int[] k = {1};//<-This is the number of buttons which are so far available in the sequence.
        for (int j = 0; j < 9; j++){//<-Iterates over the buttons to find which one was clicked.
        final int finalJ = j;
            final TextView finalScore = score;
            final TextView finalScore1 = score;
            btn[finalJ].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    clickAnimate(btn[finalJ], click);
                    if (btnSeq[seqNum[0]] == v){//<-Checks if correct button selected.
                            seqNum[0]++;
                            finalScore.setText(seqNum[0] + "/" + k[0]);//<-Updates the score as player progresses
                            if(k[0] == seqNum[0]){//<-This block entered once player has successfully selected all of the buttons which are so far available.
                                i[0] = 0;
                                handler.post(new Runnable(){
                                    @Override
                                    public void run(){
                                        seqAnimate(btnSeq[i[0]], btn, click);//<-Button sequence is replayed with a new button appended on the end
                                        i[0]++;
                                        if(i[0] < k[0]){
                                            seqNum[0] = 0;//<-The index number for sequence buttons is reset. The player must now start from the beginning again.
                                            handler.postDelayed(this, 500);
                                        }
                                    }
                                }); k[0]++;//<-'k' is incremented to allow the player to select one button more than previous.
                                if (k[0] > highScore[0]){//<-Checks if new high score is achieved
                                    highScore[0] = k[0];
                                    if (showOnce[0] == true){//<-Only informs of high score once per game
                                        Toast.makeText(MainActivity.this, "New High Score!", Toast.LENGTH_SHORT).show();
                                        showOnce[0] = false;
                                    }
                                }
                                finalScore.setText("0/" + k[0]);
                            }
                    }
                    else {
                        //Resets the game by showing the player's final score and returning to menu.
                        if (k[0] != 2){
                            Toast.makeText(MainActivity.this, "You remembered " + (k[0] - 1) + " buttons", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "You remembered " + (k[0] - 1) + " button", Toast.LENGTH_LONG).show();
                        }
                        for (int l = 0; l < btn.length; l++){
                                btn[l].setEnabled(false);
                        }

                        for (int j = 0; j < 6; j++){
                            if (scoreArray[j] != 0){
                                count++;
                            }
                        }

                        if (count >= 5){
                            arrayFill = true;
                        }

                        for (int i = 0; i < scoreArray.length; i++){
                            if (arrayFill == false){//<-Adds all scores to array until array is full
                                if (scoreArray[i] == 0){
                                    System.out.println("ScoreArray is " + scoreArray[i]);
                                    scoreArray[i] = k[0] - 1;
                                    System.out.println("ScoreArray is " + scoreArray[i]);
                                    break;
                                }
                            }
                            if (arrayFill == true){//<-Only adds score to array if higher than any array value
                                if (scoreArray[i] < highScore[0] - 1){
                                    scoreArray[i] = highScore[0] - 1;
                                    break;
                                }
                            }
                        }

                        Arrays.sort(scoreArray);//<-Sorts array numerically
                        System.out.println("End of high scores");
                        for (int i = 0; i <= 5; i++){
                            System.out.println("Value: " + scoreArray[i] + " and index: " + i);
                        }
                        System.out.println("Count: " + count);
                        System.out.println("Array fill: " + arrayFill);
                        //Intent myIntent = new Intent(v.getContext(), Menu.class);
                        //startActivityForResult(myIntent, 0);//<-Closes game screen and returns to menu

                        Button splash = (Button) findViewById(R.id.splash);
                        splash.setVisibility(splash.VISIBLE);

                        Button splashPlay = (Button) findViewById(R.id.splashPlay);
                        splashPlay.setVisibility(splashPlay.VISIBLE);

                        Button splashHighScores = (Button) findViewById(R.id.splashHighScores);
                        splashHighScores.setVisibility(splashHighScores.VISIBLE);

                        Button splashOptions = (Button) findViewById(R.id.splashOptions);
                        splashOptions.setVisibility(splashOptions.VISIBLE);

                        finalScore1.setText("0/1");

                    }
                }
            });
        }
    }

    //Turns button white for 400 milliseconds with click sound. See 'sequence' for more information.
    private void seqAnimate(final Button btnSeq, final Button btn[], final MediaPlayer click) {

        for (int l = 0; l < btn.length; l++){//<-Buttons are disabled while the sequence plays
            btn[l].setEnabled(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btnSeq.setBackgroundColor(Color.WHITE);
                if(clickOnOff == true){
                    click.start();
                }

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        btnSeq.setBackgroundColor(Color.BLACK);
                    }
                }, 400);
                for (int l = 0; l < btn.length; l++){
                    btn[l].setEnabled(true);
                }
            }
        }, 500);

    }

    public static int getHighScoreA(){
        int x = scoreArray[5];
        return x;
    }

    public static int getHighScoreB(){
        int x = scoreArray[4];
        return x;
    }

    public static int getHighScoreC(){
        int x = scoreArray[3];
        return x;
    }

    public static int getHighScoreD(){
        int x = scoreArray[2];
        return x;
    }

    public static int getHighScoreE(){
        int x = scoreArray[1];
        return x;
    }

    public static MediaPlayer getButtonClick(){
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
    public void onBackPressed(){
        if(splashBack){
            Button splashPlay = (Button) findViewById(R.id.splashPlay);
            splashPlay.setVisibility(splashPlay.VISIBLE);

            Button splashHighScores = (Button) findViewById(R.id.splashHighScores);
            splashHighScores.setVisibility(splashHighScores.VISIBLE);

            Button splashOptions = (Button) findViewById(R.id.splashOptions);
            splashOptions.setVisibility(splashOptions.VISIBLE);

            Button splashSoundEffects = (Button) findViewById(R.id.splashSoundEffects);
            splashSoundEffects.setVisibility(splashSoundEffects.GONE);

            Button splashMusic = (Button) findViewById(R.id.splashMusic);
            splashMusic.setVisibility(splashMusic.GONE);

            splashBack = false;
        }
        else{
            super.onBackPressed();
        }
    }

}