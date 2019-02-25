package com.darkpixellabyrinth.dpl;

import com.darkpixellabyrinth.dpl.Positions.Position;
import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

public class Intersection {

    private Direction intersectionDirection;
    private PathBranch pathBranch;
    private ScreenPosition intersectionScreenPosition;
    private Position intersectionPosition;
    private PathBranch parentBranch;

    public Intersection(Direction intersectionDirection, PathBranch pathBranch, Position intersectionPosition, PathBranch parentBranch) {
        this.intersectionDirection = intersectionDirection;
        this.pathBranch = pathBranch;
        this.intersectionScreenPosition = new ScreenPosition(intersectionPosition.getX(), intersectionPosition.getY());
        this.pathBranch.setDirection(intersectionDirection);
        this.pathBranch.setStartScreenPosition(this.intersectionScreenPosition);
        this.pathBranch.setPosition(intersectionPosition);
        this.intersectionPosition = intersectionPosition;
        this.parentBranch = parentBranch;
    }

    public Direction getIntersectionDirection() {
        return intersectionDirection;
    }

    public void setIntersectionDirection(Direction intersectionDirection) {
        this.intersectionDirection = intersectionDirection;
    }

    public PathBranch getPathBranch() {
        return pathBranch;
    }

    public void setPathBranch(PathBranch pathBranch) {
        this.pathBranch = pathBranch;
    }

    public ScreenPosition getIntersectionScreenPosition() {
        return intersectionScreenPosition;
    }

    public void setIntersectionScreenPosition(ScreenPosition intersectionScreenPosition) {
        this.intersectionScreenPosition = intersectionScreenPosition;
    }

    public Position getIntersectionPosition() {
        return intersectionPosition;
    }

    public void setIntersectionPosition(Position intersectionPosition) {
        this.intersectionPosition = intersectionPosition;
    }

    public PathBranch getParentBranch() {
        return parentBranch;
    }

    public void setParentBranch(PathBranch parentBranch) {
        this.parentBranch = parentBranch;
    }

    @Override
    public String toString() {
        return "Intersection{" + this.intersectionPosition + '}';
    }
}
