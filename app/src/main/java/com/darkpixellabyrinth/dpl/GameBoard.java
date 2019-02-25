package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

public class GameBoard extends View {

    private Context mContext;
    private PixelCharacter pixelCharacter;
    private DrawPaths drawPaths;
    private Level level;

    public GameBoard(Context context, Level level) {
        super(context);
        this.mContext = context;
        this.level = level;
        initGame(level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPaths.draw(canvas);
        pixelCharacter.draw(canvas);
    }

    private void initGame(Level level) {
        pixelCharacter = new PixelCharacter(mContext, new ScreenPosition(0, 0));
        pixelCharacter.setActualPathBranch(level.getStartPathBranch());
        drawPaths = new DrawPaths(mContext, new ScreenPosition(0, 0), level);
    }

    public void goUp() {
        if (this.pixelCharacter.getActualPathBranch().getPositionMax().getY() > this.pixelCharacter.getPosition().getY()) {
            Intersection nextIntersection = this.pixelCharacter.getActualPathBranch().getNextIntersectionUp(this.pixelCharacter.getPosition());

            Log.d("nextIntersection", String.valueOf(nextIntersection));

            this.pixelCharacter.moveUp();
            this.drawPaths.moveUP();

            if (nextIntersection != null) {
                if (this.pixelCharacter.getPosition().getY() == nextIntersection.getIntersectionPosition().getY()) {
                    this.pixelCharacter.setActualPathBranch(nextIntersection.getPathBranch());
                    Log.d("ActualPathBranch", String.valueOf(this.pixelCharacter.getActualPathBranch()));
                }
            }
        }
    }

    public void goDown() {
        if (this.pixelCharacter.getActualPathBranch().getPositionMin().getY() < this.pixelCharacter.getPosition().getY()) {
            Intersection nextIntersection = this.pixelCharacter.getActualPathBranch().getNextIntersectionDown(this.pixelCharacter.getPosition());

            Log.d("nextIntersection", String.valueOf(nextIntersection));

            this.pixelCharacter.moveDown();
            this.drawPaths.moveDown();

            if (nextIntersection != null) {
                if (this.pixelCharacter.getPosition().getY() == nextIntersection.getIntersectionPosition().getY()) {
                    this.pixelCharacter.setActualPathBranch(nextIntersection.getPathBranch());
                    Log.d("ActualPathBranch", String.valueOf(this.pixelCharacter.getActualPathBranch()));
                }
            }
        }
    }

    public void goRigth() {
        if (this.pixelCharacter.getActualPathBranch().getPositionMax().getX() > this.pixelCharacter.getPosition().getX()) {

            Intersection nextIntersection = this.pixelCharacter.getActualPathBranch().getNextIntersectionRight(this.pixelCharacter.getPosition());

            Log.d("nextIntersection", String.valueOf(nextIntersection));

            this.pixelCharacter.moveRight();
            this.drawPaths.moveRight();

            if (nextIntersection != null) {
                if (this.pixelCharacter.getPosition().getX() == nextIntersection.getIntersectionPosition().getX()) {
                    this.pixelCharacter.setActualPathBranch(nextIntersection.getPathBranch());
                    Log.d("ActualPathBranch", String.valueOf(this.pixelCharacter.getActualPathBranch()));
                }
            }

        }
    }

    public void goLeft() {
        if (this.pixelCharacter.getActualPathBranch().getPositionMin().getX() < this.pixelCharacter.getPosition().getX()) {
            Intersection nextIntersection = this.pixelCharacter.getActualPathBranch().getNextIntersectionLeft(this.pixelCharacter.getPosition());

            Log.d("nextIntersection", String.valueOf(nextIntersection));

            this.pixelCharacter.moveLeft();
            this.drawPaths.moveLeft();

            if (nextIntersection != null) {
                if (this.pixelCharacter.getPosition().getX() == nextIntersection.getIntersectionPosition().getX()) {
                    this.pixelCharacter.setActualPathBranch(nextIntersection.getPathBranch());
                    Log.d("ActualPathBranch", String.valueOf(this.pixelCharacter.getActualPathBranch()));

                }
            }
        }
    }
}
