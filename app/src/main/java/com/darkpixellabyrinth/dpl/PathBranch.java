package com.darkpixellabyrinth.dpl;

import android.content.Context;

import java.security.InvalidParameterException;
import java.util.HashSet;

public class PathBranch implements Floor {

    private Context context;
    private int lenght;
    private Position startPosition;
    private Position endPosition;
    private Direction direction;
    private HashSet<Direction> directionsEnable;
    private HashSet<Intersection> setOfIntersections;

    public PathBranch(Context context, int lenght, Position startPosition, Direction direction) {
        this.lenght = lenght;
        this.startPosition = startPosition;
        this.direction = direction;
        this.context = context;
        this.directionsEnable = new HashSet<>();
        this.setOfIntersections = new HashSet<>();

        this.enableDiretion();
        this.computeEndPosition();
    }

    private void computeEndPosition() {
        switch (this.direction) {
            case LEFT:
                this.endPosition = new Position(this.context, this.startPosition.getX() - this.lenght + 1, this.startPosition.getY());
                break;
            case RIGHT:
                this.endPosition = new Position(this.context, this.startPosition.getX() + this.lenght, this.startPosition.getY());
                break;
            case UP:
                this.endPosition = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() - this.lenght);
                break;
            case DOWN:
                this.endPosition = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() + this.lenght);
                break;
            default:
                throw new InvalidParameterException("Invalid direction");
        }
    }

    private void enableDiretion() {
        if (this.direction == Direction.DOWN || this.direction == Direction.UP) {
            this.directionsEnable.add(Direction.DOWN);
            this.directionsEnable.add(Direction.UP);
        } else if (this.direction == Direction.RIGHT || this.direction == Direction.LEFT) {
            this.directionsEnable.add(Direction.RIGHT);
            this.directionsEnable.add(Direction.LEFT);
        } else {
            throw new InvalidParameterException("Invalid direction");
        }
    }

    public boolean onThePath(Position position) {
        if (this.getDirection() == Direction.DOWN || this.getDirection() == Direction.UP) {

            boolean xCorrect = this.getStartPosition().getX() == position.getX();
            if (xCorrect) {
                int yMin = this.getStartPosition().getY();
                int yMax = this.getEndPosition().getY();
                int y = position.getY();

                return ((yMin <= y) && (y <= yMax)) || ((yMax <= y) && (y <= yMin));
            } else {
                return false;
            }

        } else if (this.getDirection() == Direction.LEFT || this.getDirection() == Direction.RIGHT) {
            boolean yCorrect = this.getStartPosition().getY() == position.getY();
            if (yCorrect) {
                int xMin = this.getStartPosition().getX();
                int xMax = this.getEndPosition().getX();
                int x = position.getX();

                return ((xMin <= x) && (x <= xMax)) || ((xMax <= x) && (x <= xMin));
            } else {
                return false;
            }
        } else {
            throw new IllegalStateException("Invalid direction");
        }
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addIntersection(Intersection intersection) {
        this.setOfIntersections.add(intersection);
    }

    public HashSet<Intersection> getSetOfIntersections() {
        return setOfIntersections;
    }

    public void setSetOfIntersections(HashSet<Intersection> setOfIntersections) {
        this.setOfIntersections = setOfIntersections;
    }

    @Override
    public HashSet<Direction> getDirectionEnable() {
        return this.directionsEnable;
    }

    @Override
    public String toString() {
        return "PathBranch{" +
                "startPosition=" + startPosition +
                ", directionsEnable=" + directionsEnable +
                '}';
    }
}
