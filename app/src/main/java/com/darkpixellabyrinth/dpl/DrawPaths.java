package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

class DrawPaths extends View {

    private Paint paint;
    private ArrayList<Rect> pathView = new ArrayList<>();
    private int boxSize;
    private Level level;

    public DrawPaths(Context context, Level level) {
        super(context);
        this.level = level;

        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        this.boxSize = sharedPreferences.getInt("boxSize", 0);

        this.paint = new Paint();

        if (this.level != null && this.level.getPathBranches() != null) {
            for (PathBranch pathBranch : this.level.getPathBranches()) {

                switch (pathBranch.getDirection()) {
                    case LEFT:
                        createPathLeft(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case UP:
                        createPathUp(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case RIGHT:
                        createPathRight(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case DOWN:
                        createPathDown(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public void moveUP() {
        for (Rect rect : this.pathView) {
            rect.bottom = rect.bottom + this.boxSize;
            rect.top = rect.top + this.boxSize;
        }
    }

    public void moveDown() {
        for (Rect rect : this.pathView) {
            rect.bottom = rect.bottom - this.boxSize;
            rect.top = rect.top - this.boxSize;
        }
    }

    public void moveRight() {
        for (Rect rect : this.pathView) {
            rect.right = rect.right - this.boxSize;
            rect.left = rect.left - this.boxSize;
        }
    }

    public void moveLeft() {
        for (Rect rect : this.pathView) {
            rect.right = rect.right + this.boxSize;
            rect.left = rect.left + this.boxSize;
        }
    }


    private void createPathUp(int length, Position start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen() - length * this.boxSize,
                start.getxScreen() + this.boxSize,
                start.getyScreen()));

    }

    private void createPathDown(int length, Position start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen(),
                start.getxScreen() + this.boxSize,
                start.getyScreen() + length * this.boxSize));

    }

    private void createPathRight(int length, Position start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen(),
                start.getxScreen() + length * this.boxSize,
                start.getyScreen() + this.boxSize));

    }

    private void createPathLeft(int length, Position start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getxScreen() - length * this.boxSize,
                start.getyScreen(),
                start.getxScreen(),
                start.getyScreen() + this.boxSize));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.paint.setColor(getResources().getColor(R.color.lightwhite));

        for (Rect rect : this.pathView) {
            canvas.drawRect(rect, this.paint);
        }

    }
}
