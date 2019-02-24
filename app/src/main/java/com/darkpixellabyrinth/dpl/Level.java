package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;

class Level {

    private ArrayList<Position> path;

    public Level(ArrayList<Position> path) {
        this.path = path;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void setPath(ArrayList<Position> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Level{" +
                "path=" + path +
                '}';
    }
}
