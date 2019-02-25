package com.darkpixellabyrinth.dpl.Positions;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.darkpixellabyrinth.dpl.StartMenu;

public class ScreenPosition extends Position {
    private int x;
    private int y;

    public ScreenPosition(int x, int y) {
        super(x,y);
        this.setUpPosition(x, y);
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

    private void setUpPosition(int x, int y) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StartMenu.getContextOfApplication());
        int boxSize = prefs.getInt("boxSize", 0);

        this.x = ((prefs.getInt("screenDimensionsX", 0) / 2) - (boxSize / 2)) + (x * boxSize);
        this.y = ((prefs.getInt("screenDimensionsY", 0) / 2) - (boxSize / 2)) - (y * boxSize);
    }

    @Override
    public String toString() {
        return "ScreenPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
