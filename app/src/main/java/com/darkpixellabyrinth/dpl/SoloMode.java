package com.darkpixellabyrinth.dpl;

import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;


public class SoloMode extends AppCompatActivity {

    private static final int NB_COLUMNS = 21;

    int[] screenDimensions;

    ImageView commandUp, commandDown, commandRight, commandLeft;
    ConstraintLayout gameBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solo_mode);

        getViews();

        screenDimensions = measureScreenDimensions();
        gameBoardView.addView(new GameBoard(this, screenDimensions[0], screenDimensions[1], NB_COLUMNS));
    }


    private void getViews() {
        commandUp = findViewById(R.id.commandUp);
        commandDown = findViewById(R.id.commandDown);
        commandRight = findViewById(R.id.commandRight);
        commandLeft = findViewById(R.id.commandLeft);
        gameBoardView = findViewById(R.id.gameBoard);
    }

    private int[] measureScreenDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return new int[]{size.x, size.y};
    }
}
