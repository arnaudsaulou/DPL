package com.darkpixellabyrinth.dpl;

import com.darkpixellabyrinth.dpl.Positions.Position;
import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

import java.util.ArrayList;

public class PathBranch {

    private ArrayList<Intersection> intersectionList;
    private int lenght;
    private Position positionMax;
    private Position positionMin;
    private ScreenPosition startScreenPosition;
    private Direction direction;
    private PathBranch parentBranch;

    public PathBranch(int lenght, Position startPosition, Direction direction) {
        this.intersectionList = new ArrayList<>();
        this.lenght = lenght;
        this.startScreenPosition = new ScreenPosition(startPosition.getX(), startPosition.getY());
        this.direction = direction;
        this.positionMin = startPosition;
        this.setPositionMax();
    }

    public PathBranch(int lenght) {
        this.intersectionList = new ArrayList<>();
        this.lenght = lenght;
        this.startScreenPosition = null;
        this.direction = null;
    }

    public ArrayList<Intersection> getIntersectionList() {
        return intersectionList;
    }

    public void setIntersectionList(ArrayList<Intersection> intersectionList) {
        this.intersectionList = intersectionList;
    }

    public void addIntersection(Intersection newIntersection) {
        this.intersectionList.add(newIntersection);
        this.parentBranch = newIntersection.getParentBranch();
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public ScreenPosition getStartScreenPosition() {
        return startScreenPosition;
    }

    public void setStartScreenPosition(ScreenPosition startScreenPosition) {
        this.startScreenPosition = startScreenPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPositionMax() {

        switch (this.direction) {
            case UP:
                this.positionMax = new Position(this.positionMin.getX(), this.positionMin.getY() + this.lenght);
                break;
            case RIGHT:
                this.positionMax = new Position(this.positionMin.getX() + this.lenght, this.positionMin.getY());
                break;
            default:
                break;
        }

    }

    public void setPositionMin() {

        switch (this.direction) {
            case LEFT:
                this.positionMin = new Position(this.positionMax.getX() - this.lenght, this.positionMax.getY());
                break;
            case DOWN:
                this.positionMin = new Position(this.positionMax.getX(), this.positionMax.getY() - this.lenght);
                break;
            default:
                break;
        }

    }

    public Position getPositionMax() {
        return positionMax;
    }

    public Position getPositionMin() {
        return positionMin;
    }

    public void setPosition(Position position) {
        if (this.direction == Direction.RIGHT || this.direction == Direction.UP) {
            this.positionMin = position;
            this.setPositionMax();
        } else {
            this.positionMax = position;
            this.setPositionMin();
        }
    }

    public Intersection getNextIntersectionUp(Position position) {
        Intersection nextIntersection = null;
        int yMin = position.getY();

        if (this.intersectionList.size() > 0) {
            for (Intersection intersection : this.intersectionList) {
                if (nextIntersection == null) {
                    if (yMin < intersection.getIntersectionPosition().getY()) {
                        nextIntersection = intersection;
                    }
                } else {
                    if (nextIntersection.getIntersectionPosition().getY() > intersection.getIntersectionPosition().getY()) {
                        nextIntersection = intersection;
                    }
                }
            }
        }

        return nextIntersection;
    }

    public Intersection getNextIntersectionRight(Position position) {
        Intersection nextIntersection = null;
        int xMin = position.getX();

        if (this.intersectionList.size() > 0) {
            for (Intersection intersection : this.intersectionList) {
                if (nextIntersection == null) {
                    if (xMin < intersection.getIntersectionPosition().getX()) {
                        nextIntersection = intersection;
                    }
                } else {
                    if (nextIntersection.getIntersectionPosition().getX() > intersection.getIntersectionPosition().getX()) {
                        nextIntersection = intersection;
                    }
                }
            }
        }

        return nextIntersection;
    }

    public Intersection getNextIntersectionDown(Position position) {
        Intersection nextIntersection = null;
        int yMin = position.getY();

        if (this.intersectionList.size() > 0) {
            for (Intersection intersection : this.intersectionList) {
                if (nextIntersection == null) {
                    if (yMin > intersection.getIntersectionPosition().getY()) {
                        nextIntersection = intersection;
                    }
                } else {
                    if (nextIntersection.getIntersectionPosition().getY() < intersection.getIntersectionPosition().getY()) {
                        nextIntersection = intersection;
                    }
                }
            }
        }

        return nextIntersection;
    }

    public Intersection getNextIntersectionLeft(Position position) {
        Intersection nextIntersection = null;
        int xMin = position.getX();

        if (this.intersectionList.size() > 0) {
            for (Intersection intersection : this.intersectionList) {
                if (nextIntersection == null) {
                    if (xMin > intersection.getIntersectionPosition().getX()) {
                        nextIntersection = intersection;
                    }
                } else {
                    if (nextIntersection.getIntersectionPosition().getX() < intersection.getIntersectionPosition().getX()) {
                        nextIntersection = intersection;
                    }
                }
            }
        }

        return nextIntersection;
    }

}
