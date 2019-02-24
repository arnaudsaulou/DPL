package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GameBoard extends View {

    private int width;
    private int height;
    private int nbColumns;
    private int boxSize;
    private Context mContext;
    private PixelCharacter pixelCharacter;
    private PathLabyrinth pathLabyrinth;

    public GameBoard(Context context, int width_l, int height_l, int nbColumns_l) {
        super(context);
        mContext = context;
        width = width_l;
        height = height_l;
        nbColumns = nbColumns_l;
        boxSize = calculatBoxSize();
        initGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pathLabyrinth.draw(canvas);
        pixelCharacter.draw(canvas);
    }

    private void initGame() {
        pixelCharacter = new PixelCharacter(mContext, calculatCharacterPosition(), boxSize);
        pathLabyrinth = new PathLabyrinth(mContext, boxSize, calculatCharacterPosition());
    }

    private int calculatBoxSize() {
        return width / nbColumns;
    }

    private Position calculatCenterGameBoard() {
        return new Position((width / 2), (height / 2));
    }

    private Position calculatCharacterPosition() {
        Position centerGameBoard = calculatCenterGameBoard();
        return new Position(centerGameBoard.getX() - (boxSize / 2), centerGameBoard.getY() - (boxSize / 2));
    }
}
