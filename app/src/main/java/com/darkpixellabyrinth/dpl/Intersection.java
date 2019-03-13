package com.darkpixellabyrinth.dpl;

import com.darkpixellabyrinth.dpl.Positions.Position;
import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

import java.util.ArrayList;

public class Intersection {

    private ScreenPosition intersectionScreenPosition;
    private Position intersectionPosition;
    private ArrayList<Intersection> listIntersection;

    public Intersection(ScreenPosition intersectionScreenPosition, Position intersectionPosition, ArrayList<Intersection> listIntersection) {
        this.intersectionScreenPosition = intersectionScreenPosition;
        this.intersectionPosition = intersectionPosition;
        this.listIntersection = listIntersection;
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

    public ArrayList<Intersection> getListIntersection() {
        return listIntersection;
    }

    public void setListIntersection(ArrayList<Intersection> listIntersection) {
        this.listIntersection = listIntersection;
    }
}
