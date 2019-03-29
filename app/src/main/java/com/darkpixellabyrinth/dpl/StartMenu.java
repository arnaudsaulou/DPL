package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import static com.darkpixellabyrinth.dpl.Constants.NB_COLUMNS;
import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

public class StartMenu extends AppCompatActivity {

    private Button options, multi, solo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        getViews();

        setOnClick();

        SharedPreferences sharedPreferences = this.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //Calculation character'size
        int division = size.x / NB_COLUMNS;
        int boxSize;
        if (division % 2 == 0) {
            boxSize = division;
        } else {
            boxSize = division + 1;
        }

        //Calculation centre X of the game
        int screenX = size.x;
        int centerX = (screenX / 2) / boxSize;

        //Calculation centre Y of the game
        int screenY = size.y;
        int centerY = (screenY / 2) / boxSize;

        //Save useful parameters
        editor.putInt("boxSize", boxSize);
        editor.putInt("centerX", centerX);
        editor.putInt("centerY", centerY);
        editor.apply();
    }


    private void setOnClick() {
        this.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		Intent intent = new Intent(StartMenu.this, Options.class);
                startActivity(intent);
            }
        });

        this.multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, SoloMode.class);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        this.options = findViewById(R.id.options);
        this.multi = findViewById(R.id.multi);
        this.solo = findViewById(R.id.solo);
    }
}
