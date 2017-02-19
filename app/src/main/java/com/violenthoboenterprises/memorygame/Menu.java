package com.violenthoboenterprises.memorygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import static com.violenthoboenterprises.memorygame.MainActivity.*;
import static com.violenthoboenterprises.memorygame.MainActivity.getButtonClick;

/**
 * Created by Klunj on 15/12/2016.
 */

public class Menu extends MainActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final MediaPlayer buttonClick = MediaPlayer.create(this, R.raw.buttonclick);

        //Brings up main activity on click.
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(Options.clickOnOff == true){
                    buttonClick.start();
                }
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Brings up high scores on click
        Button highScores = (Button) findViewById(R.id.highScores);
        highScores.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(Options.clickOnOff == true){
                    buttonClick.start();
                }
                Intent myIntent = new Intent(v.getContext(), HighScores.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Brings up options on click
        Button options = (Button) findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(Options.clickOnOff == true){
                    buttonClick.start();
                }
                Intent myIntent = new Intent(v.getContext(), Options.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}