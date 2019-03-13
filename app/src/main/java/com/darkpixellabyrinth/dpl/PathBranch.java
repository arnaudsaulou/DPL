package com.darkpixellabyrinth.dpl;

import com.darkpixellabyrinth.dpl.Positions.Position;
import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

public class PathBranch {

    private int lenght;
    private ScreenPosition startScreenPosition;
    private Position startPosition;
    private Direction direction;

    public PathBranch(int lenght, ScreenPosition startScreenPosition, Position startPosition, Direction direction) {
        this.lenght = lenght;
        this.startScreenPosition = startScreenPosition;
        this.startPosition = startPosition;
        this.direction = direction;
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
