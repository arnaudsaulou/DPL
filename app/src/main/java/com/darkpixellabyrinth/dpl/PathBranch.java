package com.darkpixellabyrinth.dpl;

import android.content.Context;

import java.security.InvalidParameterException;
import java.util.HashSet;

public class PathBranch implements Floor {

    private Context context;
    private int length;
    private Position startPosition;
    private Position endPosition;
    private Position positionStartIntersection;
    private Position positionEndIntersection;
    private Direction direction;
    private HashSet<Direction> directionsEnable;
    private Intersection startIntersection;
    private Intersection endIntersection;

    public PathBranch(Context context, int lenght, Position startPosition, Direction direction) {
        this.length = lenght;
        this.startPosition = startPosition;
        this.direction = direction;
        this.context = context;
        this.directionsEnable = new HashSet<>();
        this.startIntersection = null;
        this.endIntersection = null;
        this.enableDiretion();
        this.computeEndPosition();
        this.computeIntersectionPoint();
    }

    private void computeEndPosition() {
        switch (this.direction) {
            case LEFT:
                this.endPosition = new Position(this.context, this.startPosition.getX() - this.length, this.startPosition.getY());
                break;
            case RIGHT:
                this.endPosition = new Position(this.context, this.startPosition.getX() + this.length, this.startPosition.getY());
                break;
            case UP:
                this.endPosition = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() - this.length);
                break;
            case DOWN:
                this.endPosition = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() + this.length);
                break;
            default:
                throw new InvalidParameterException("Invalid direction");
        }
    }

    private void computeIntersectionPoint() {
        switch (this.direction) {
            case LEFT:
                this.positionStartIntersection = new Position(this.context, this.startPosition.getX() + 1, this.startPosition.getY());
                this.positionEndIntersection = new Position(this.context, this.endPosition.getX() - 1, this.endPosition.getY());
                break;
            case RIGHT:
                this.positionStartIntersection = new Position(this.context, this.startPosition.getX() - 1, this.startPosition.getY());
                this.positionEndIntersection = new Position(this.context, this.endPosition.getX(), this.endPosition.getY());
                break;
            case UP:
                this.positionStartIntersection = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() + 1);
                this.positionEndIntersection = new Position(this.context, this.endPosition.getX(), this.endPosition.getY());
                break;
            case DOWN:
                this.positionStartIntersection = new Position(this.context, this.startPosition.getX(), this.startPosition.getY() - 1);
                this.positionEndIntersection = new Position(this.context, this.endPosition.getX(), this.endPosition.getY() + 1);
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public Intersection getStartIntersection() {
        return this.startIntersection;
    }

    public Intersection getEndIntersection() {
        return this.endIntersection;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addIntersection(Intersection intersection) {
        System.out.println(intersection);
        if (intersection.getStartPosition().equals(this.positionStartIntersection)) {
            this.startIntersection = intersection;
        } else if (intersection.getEndPosition().equals(this.positionEndIntersection)) {
            this.endIntersection = intersection;
        } else {
            throw new IllegalStateException("Invalid Intersection");
        }

    }

    @Override
    public HashSet<Direction> getDirectionEnable() {
        return this.directionsEnable;
    }

    @Override
    public String toString() {
        return "PathBranch{" +
                "length=" + length +
                ", startPosition=" + startPosition +
                ", directionsEnable=" + directionsEnable +
                '}';
    }
}
