package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;

public class Level {

    private ArrayList<PathBranch> pathBranches;
    private PathBranch startPathBranch;

    public Level() {}

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

    @Override
    public String toString() {
        return "Level{" +
                "pathBranches=" + pathBranches +
                '}';
    }
}
