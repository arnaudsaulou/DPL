package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class StartMenu extends AppCompatActivity {

    private static final int NB_COLUMNS = 21;


    Button options, multi, solo;
    static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        getViews();

        setOnClick();

        contextOfApplication = this;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean("firstTime", false)) {
            SharedPreferences.Editor editor = prefs.edit();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            int division = size.x / NB_COLUMNS;
            if (division % 2 == 0) {
                editor.putInt("boxSize", division);
            } else {
                editor.putInt("boxSize", division + 1);
            }

            editor.putInt("screenDimensionsX", size.x);
            editor.putInt("screenDimensionsY", size.y);
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
    }

    private void setOnClick() {
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, SoloMode.class);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        options = findViewById(R.id.options);
        multi = findViewById(R.id.multi);
        solo = findViewById(R.id.solo);
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
}
