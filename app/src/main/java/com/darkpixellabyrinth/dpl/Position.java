package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

public class Position {

    private Context context;
    private int x;
    private int y;
    private int xScreen;
    private int yScreen;

    public Position(Context context, int x, int y) {
        this.context = context;
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

    public Position positionAbove() {
        return new Position(this.context, this.x, this.y + 1);
    }

    public Position positionUnder() {
        return new Position(this.context, this.x, this.y - 1);
    }

    public Position positionAtRight() {
        return new Position(this.context, this.x + 1, this.y);
    }

    public Position positionAtLeft() {
        return new Position(this.context, this.x - 1, this.y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return this.getX() == position.getX() && this.getY() == position.getY();
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
