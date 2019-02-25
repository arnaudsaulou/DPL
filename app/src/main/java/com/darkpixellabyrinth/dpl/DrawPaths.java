package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.view.View;

import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

import java.util.ArrayList;

class DrawPaths extends View {

    private Paint paint;
    private ArrayList<Rect> pathView = new ArrayList<>();
    private int boxSize;
    private ScreenPosition center;
    private Level level;

    public DrawPaths(Context context, ScreenPosition center, Level level) {
        super(context);
        this.center = center;
        this.level = level;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StartMenu.getContextOfApplication());
        this.boxSize = prefs.getInt("boxSize", 0);

        this.paint = new Paint();

        for (PathBranch pathBranch : this.level.getPathBranches()) {

            if (pathBranch.getIntersectionList().size() > 0) {
                recursivePathDrawing(pathBranch);
            }

            switch (pathBranch.getDirection()) {
                case LEFT:
                    createPathLeft(pathBranch.getLenght(), pathBranch.getStartScreenPosition());
                    break;
                case UP:
                    createPathUp(pathBranch.getLenght(), pathBranch.getStartScreenPosition());
                    break;
                case RIGHT:
                    createPathRight(pathBranch.getLenght(), pathBranch.getStartScreenPosition());
                    break;
                case DOWN:
                    createPathDown(pathBranch.getLenght(), pathBranch.getStartScreenPosition());
                    break;
                default:
                    break;
            }
        }

    }


    private void recursivePathDrawing(PathBranch pathBranch) {

        for (Intersection intersection : pathBranch.getIntersectionList()) {

            switch (intersection.getIntersectionDirection()) {
                case LEFT:
                    createPathLeft(intersection.getPathBranch().getLenght(), intersection.getIntersectionScreenPosition());
                    break;
                case UP:
                    createPathUp(intersection.getPathBranch().getLenght(), intersection.getIntersectionScreenPosition());
                    break;
                case RIGHT:
                    createPathRight(intersection.getPathBranch().getLenght(), intersection.getIntersectionScreenPosition());
                    break;
                case DOWN:
                    createPathDown(intersection.getPathBranch().getLenght(), intersection.getIntersectionScreenPosition());
                    break;
                default:
                    break;
            }

            if (intersection.getPathBranch().getIntersectionList().size() > 0) {
                recursivePathDrawing(intersection.getPathBranch());
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


    private void createPathUp(int length, ScreenPosition start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getX(),
                start.getY() - length * this.boxSize,
                start.getX() + this.boxSize,
                start.getY()));

    }

    private void createPathDown(int length, ScreenPosition start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getX(),
                start.getY() + this.boxSize,
                start.getX() + this.boxSize,
                start.getY() + (length + 1) * this.boxSize));

    }

    private void createPathRight(int length, ScreenPosition start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getX() + this.boxSize,
                start.getY(),
                start.getX() + (length + 1) * this.boxSize,
                start.getY() + this.boxSize));

    }

    private void createPathLeft(int length, ScreenPosition start) {
        //start : coin haut gauche
        this.pathView.add(new Rect(
                start.getX() - length * this.boxSize,
                start.getY(),
                start.getX(),
                start.getY() + this.boxSize));

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
