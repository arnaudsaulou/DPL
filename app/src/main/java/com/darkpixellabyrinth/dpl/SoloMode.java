package com.darkpixellabyrinth.dpl;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.darkpixellabyrinth.dpl.Levels.MapReader;

public class SoloMode extends AppCompatActivity {

    ImageView commandUp, commandDown, commandRight, commandLeft;
    ConstraintLayout gameBoardView;
    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solo_mode);

        getViews();

        new MapReader(getApplicationContext());

        /*gameBoard = new GameBoard(this, new MapReader(getApplicationContext()));

        gameBoardView.addView(gameBoard);

        commandUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.goUp();
                gameBoardView.removeView(gameBoard);
                gameBoardView.addView(gameBoard);
            }
        });

        commandDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.goDown();
                gameBoardView.removeView(gameBoard);
                gameBoardView.addView(gameBoard);
            }
        });

        commandRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.goRigth();
                gameBoardView.removeView(gameBoard);
                gameBoardView.addView(gameBoard);
            }
        });

        commandLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.goLeft();
                gameBoardView.removeView(gameBoard);
                gameBoardView.addView(gameBoard);
            }
        });
*/
    }

    private void getViews() {
        commandUp = findViewById(R.id.commandUp);
        commandDown = findViewById(R.id.commandDown);
        commandRight = findViewById(R.id.commandRight);
        commandLeft = findViewById(R.id.commandLeft);
        gameBoardView = findViewById(R.id.gameBoard);
    }
}
