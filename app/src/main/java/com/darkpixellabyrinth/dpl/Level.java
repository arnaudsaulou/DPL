package com.darkpixellabyrinth.dpl;

import android.content.Context;

import java.util.ArrayList;

public class Level {

    private ArrayList<PathBranch> pathBranches;
    private PathBranch startPathBranch;
    private Position mapPosition;

    public Level(Context context) {
        //The map will be placed in the center by default
        int center = Constants.MAP_SIZE / 2;
        this.mapPosition = new Position(context, center, center);
    }

    public Level(ArrayList<PathBranch> pathBranches) {
        this.pathBranches = pathBranches;
    }

    public ArrayList<PathBranch> getPathBranches() {
        return pathBranches;
    }

    public void setPathBranches(ArrayList<PathBranch> pathBranches) {
        this.pathBranches = pathBranches;
    }

    public PathBranch getStartPathBranch() {
        return startPathBranch;
    }

    public void setStartPathBranch(PathBranch startPathBranch) {
        this.startPathBranch = startPathBranch;
    }

    public Position getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(Position mapPosition) {
        this.mapPosition = mapPosition;
    }

    @Override
    public String toString() {
        return "Level{" +
                "pathBranches=" + pathBranches +
                '}';
    }
}
