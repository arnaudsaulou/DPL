package com.the_desk.c00kie.dpl_darkpixellabyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class startMenu extends AppCompatActivity {

    Button options, multi, solo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        getViews();

        setOnClick();
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
                Intent intent = new Intent(startMenu.this, soloMode.class);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        options = findViewById(R.id.options);
        multi = findViewById(R.id.multi);
        solo = findViewById(R.id.solo);
    }
}
