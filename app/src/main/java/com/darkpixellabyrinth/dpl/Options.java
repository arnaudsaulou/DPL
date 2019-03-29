package com.darkpixellabyrinth.dpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.IOException;

public class Options extends AppCompatActivity {



    private SharedPreferences pref;
    private SharedPreferences.Editor ed;
    private boolean musicOn;
    private Intent intent;
    private CheckBox muteCheckBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        Button retour = (Button) findViewById(R.id.retour);
        muteCheckBox = (CheckBox) findViewById(R.id.checkBox);

        intent = new Intent(Options.this, MusicService.class);


        pref = getSharedPreferences("settings", Context.MODE_PRIVATE);

        musicOn = pref.getBoolean("musicOn", false);

        ed = pref.edit();



        muteCheckBox.setChecked(musicOn);


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this, StartMenu.class);
                startActivity(intent);
            }
        });


        muteCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(muteCheckBox .isChecked()){
                    ed.putBoolean("musicOn", true);

                    startService(intent);
                }else{
                    ed.putBoolean("musicOn", false);

                    stopService(intent);

                }
                ed.apply();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(intent);

        Log.d("service", "service killed");
    }
}
