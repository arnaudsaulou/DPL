package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

public class Position {

    private int x;
    private int y;
    private int xScreen;
    private int yScreen;

    public Position(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        int boxSize = sharedPreferences.getInt("boxSize", 0);
        this.xScreen = this.x * boxSize;
        this.yScreen = this.y * boxSize;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxScreen() {
        return xScreen;
    }

    public void setxScreen(int xScreen) {
        this.xScreen = xScreen;
    }

    public int getyScreen() {
        return yScreen;
    }

    public void setyScreen(int yScreen) {
        this.yScreen = yScreen;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
