package com.violenthoboenterprises.memorygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import static com.violenthoboenterprises.memorygame.R.id.score;

/**
 * Created by Klunj on 18/12/2016.
 */

public class Options extends Menu {

    static boolean clickOnOff = true;//<-Flag used on all sound effects.

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        final MediaPlayer buttonClick = MediaPlayer.create(this, R.raw.buttonclick);

        final Button musicOnOff = (Button) findViewById(R.id.musicOnOff);
        musicOnOff.setText("Music: On");
        musicOnOff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(clickOnOff == true){
                    buttonClick.start();
                }
            }
        });

        //Turn sound effects on or off.
        final Button soundEffectsOnOff = (Button) findViewById(R.id.soundEffectsOnOff);
        if(clickOnOff == true){//<-Set correct text when opening screen.
            soundEffectsOnOff.setText("Sound FX: On");
        }else{
            soundEffectsOnOff.setText("Sound FX: Off");
        }
        soundEffectsOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(clickOnOff == true){//<-Enable or disable sound effects and set text accordingly.
                    soundEffectsOnOff.setText("Sound FX: Off");
                    clickOnOff = false;
                }else{
                    soundEffectsOnOff.setText("Sound FX: On");
                    buttonClick.start();
                    clickOnOff = true;
                }
            }
        });

    }
}