package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

class DrawFloor extends View {

    private Paint paint;
    private ArrayList<Rect> pathView = new ArrayList<>();
    private int boxSize;

    public DrawFloor(Context context, Level level) {
        super(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        this.boxSize = sharedPreferences.getInt("boxSize", 0);

        this.paint = new Paint();

        if (level != null && level.getPathBranches() != null) {
            for (PathBranch pathBranch : level.getPathBranches()) {

                switch (pathBranch.getDirection()) {
                    case LEFT:
                        createViewPathLeft(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case UP:
                        createViewPathUp(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case RIGHT:
                        createViewPathRight(pathBranch.getLenght(), pathBranch.getStartPosition());
                        break;
                    case DOWN:
                        createViewPathDown(pathBranch.getLenght(), pathBranch.getStartPosition());
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


    private void createViewPathUp(int length, Position start) {
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen() - length * this.boxSize,
                start.getxScreen() + this.boxSize,
                start.getyScreen()));
    }

    private void createViewPathDown(int length, Position start) {
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen() - this.boxSize,
                start.getxScreen() + this.boxSize,
                start.getyScreen() + (length - 1) * this.boxSize));
    }

    private void createViewPathRight(int length, Position start) {
        this.pathView.add(new Rect(
                start.getxScreen(),
                start.getyScreen() - this.boxSize,
                start.getxScreen() + length * this.boxSize,
                start.getyScreen()));
    }

    private void createViewPathLeft(int length, Position start) {
        this.pathView.add(new Rect(
                start.getxScreen() - (length - 1) * this.boxSize,
                start.getyScreen() - this.boxSize,
                start.getxScreen() + this.boxSize,
                start.getyScreen()));
    }

    private void createViewIntersection(Position position) {
        this.pathView.add(new Rect(
                position.getxScreen(),
                position.getyScreen() - this.boxSize,
                position.getxScreen() + this.boxSize,
                position.getyScreen()));
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
