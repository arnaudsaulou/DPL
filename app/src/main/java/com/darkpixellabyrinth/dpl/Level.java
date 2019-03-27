package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;

public class Level {

    private ArrayList<PathBranch> pathBranches;
    private ArrayList<Intersection> intersections;
    private PathBranch startPathBranch;

    public Level() {
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

    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
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
