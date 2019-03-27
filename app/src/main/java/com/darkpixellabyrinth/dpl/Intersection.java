package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;
import java.util.HashSet;

public class Intersection implements Floor {

    private Position intersectionPosition;
    private ArrayList<PathBranch> pathLinked;
    private HashSet<Direction> directionsEnable;

    public Intersection(Position intersectionPosition, ArrayList<PathBranch> pathLinked) {
        this.intersectionPosition = intersectionPosition;
        this.pathLinked = pathLinked;
        this.directionsEnable = new HashSet<>();
        getDirectionFromPathLinked(pathLinked);
    }

    public Intersection(Position intersectionPosition) {
        this.intersectionPosition = intersectionPosition;
        this.pathLinked = new ArrayList<>();
        this.directionsEnable = new HashSet<>();
        getDirectionFromPathLinked(pathLinked);
    }

    private void getDirectionFromPathLinked(ArrayList<PathBranch> pathLinked) {
        for (PathBranch path : pathLinked) {
            this.directionsEnable.add(path.getDirection());
        }
    }

    public void addLinkedPath(PathBranch linkedPath) {
        this.pathLinked.add(linkedPath);
        this.directionsEnable.add(linkedPath.getDirection());
    }

    public Position getIntersectionPosition() {
        return intersectionPosition;
    }

    public void setIntersectionPosition(Position intersectionPosition) {
        this.intersectionPosition = intersectionPosition;
    }

    public ArrayList<PathBranch> getPathLinked() {
        return pathLinked;
    }

    public void setPathLinked(ArrayList<PathBranch> pathLinked) {
        this.pathLinked = pathLinked;
    }

    @Override
    public HashSet<Direction> getDirectionEnable() {
        return this.directionsEnable;
    }

    @Override
    public Position getStartPosition() {
        return this.intersectionPosition;
    }

    @Override
    public Position getEndPosition() {
        return this.intersectionPosition;
    }
}
