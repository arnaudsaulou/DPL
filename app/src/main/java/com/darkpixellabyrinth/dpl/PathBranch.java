package com.darkpixellabyrinth.dpl;

public class PathBranch {

    private int lenght;
    private Position startPosition;
    private Direction direction;

    public PathBranch(int lenght, Position startPosition, Direction direction) {
        this.lenght = lenght;
        this.startPosition = startPosition;
        this.direction = direction;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
