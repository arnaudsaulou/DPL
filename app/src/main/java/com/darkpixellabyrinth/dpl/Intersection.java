package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;
import java.util.HashSet;

public class Intersection implements Floor {

    private Position intersectionPosition;
    private ArrayList<PathBranch> pathLinked;
    private HashSet<Direction> directionsEnable;
    private PathBranch pathBranchUp;
    private PathBranch pathBranchRight;
    private PathBranch pathBranchDown;
    private PathBranch pathBranchLeft;

    public Intersection(Position intersectionPosition) {
        this.intersectionPosition = intersectionPosition;
        this.pathLinked = new ArrayList<>();
        this.directionsEnable = new HashSet<>();
    }

    private void setDirectionFromPathLinked() {
        for (PathBranch path : this.pathLinked) {
            switch (path.getDirection()) {
                case DOWN:
                    this.directionsEnable.add(Direction.UP);
                    this.pathBranchDown = path;
                    break;
                case UP:
                    this.directionsEnable.add(Direction.DOWN);
                    this.pathBranchUp = path;
                    break;
                case LEFT:
                    this.directionsEnable.add(Direction.RIGHT);
                    this.pathBranchLeft = path;
                    break;
                case RIGHT:
                    this.directionsEnable.add(Direction.LEFT);
                    this.pathBranchRight = path;
                    break;
                default:
                    throw new IllegalStateException("Invalid direction");
            }

        }
    }

    public void addComplementarDirecetion(Direction direction) {
        switch (direction) {
            case UP:
                this.directionsEnable.add(Direction.UP);
                this.pathBranchUp = this.pathBranchDown;
                break;
            case RIGHT:
                this.directionsEnable.add(Direction.RIGHT);
                this.pathBranchRight = this.pathBranchLeft;
                break;
            case DOWN:
                this.directionsEnable.add(Direction.DOWN);
                this.pathBranchDown = this.pathBranchUp;
                break;
            case LEFT:
                this.directionsEnable.add(Direction.LEFT);
                this.pathBranchLeft = this.pathBranchRight;
                break;
            default:
                throw new IllegalStateException("Invalid direction");
        }
    }

    public void addLinkedPath(PathBranch linkedPath) {
        this.pathLinked.add(linkedPath);
        this.setDirectionFromPathLinked();
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

    public PathBranch getPathBranchUp() {
        return pathBranchUp;
    }

    public void setPathBranchUp(PathBranch pathBranchUp) {
        this.pathBranchUp = pathBranchUp;
    }

    public PathBranch getPathBranchRight() {
        return pathBranchRight;
    }

    public void setPathBranchRight(PathBranch pathBranchRight) {
        this.pathBranchRight = pathBranchRight;
    }

    public PathBranch getPathBranchDown() {
        return pathBranchDown;
    }

    public void setPathBranchDown(PathBranch pathBranchDown) {
        this.pathBranchDown = pathBranchDown;
    }

    public PathBranch getPathBranchLeft() {
        return pathBranchLeft;
    }

    public void setPathBranchLeft(PathBranch pathBranchLeft) {
        this.pathBranchLeft = pathBranchLeft;
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

    @Override
    public String toString() {
        return "Intersection{" +
                "intersectionPosition=" + intersectionPosition +
                ", directionsEnable=" + directionsEnable +
                '}';
    }
}
