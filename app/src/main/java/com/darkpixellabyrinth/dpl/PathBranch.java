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

    public PathBranch(Context context, int lenght, Position startPosition, Direction direction) {
        this.lenght = lenght;
        this.startPosition = startPosition;
        System.out.println(direction);
        this.direction = direction;
        this.context = context;

        computeEndPosition(lenght, startPosition);

        this.directionsEnable = new HashSet<>();
        this.enableDiretion();
    }

    private void computeEndPosition(int lenght, Position startPosition) {
        switch (this.direction) {
            case LEFT:
                this.endPosition = new Position(this.context, startPosition.getX() - lenght, startPosition.getY());
                break;
            case RIGHT:
                this.endPosition = new Position(this.context, startPosition.getX() + lenght, startPosition.getY());
                break;
            case UP:
                this.endPosition = new Position(this.context, startPosition.getX(), startPosition.getY() - lenght);
                break;
            case DOWN:
                this.endPosition = new Position(this.context, startPosition.getX(), startPosition.getY() + lenght);
                break;
            default:
                throw new InvalidParameterException("Invalid direction");
        }
    }

    private void enableDiretion() {
        this.directionsEnable.clear();
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

    public void setDirection(Direction direction) {
        this.direction = direction;
        this.enableDiretion();
    }

    @Override
    public HashSet<Direction> getDirectionEnable() {
        return this.directionsEnable;
    }
}
