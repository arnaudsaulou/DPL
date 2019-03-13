package com.darkpixellabyrinth.dpl.Positions;

public class ScreenPosition extends Position {
    private int x;
    private int y;

    public ScreenPosition() {
        super(0,0);
        this.x = 0;
        this.y = 0;
    }

    public ScreenPosition(int x, int y) {
        super(x,y);
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "ScreenPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
