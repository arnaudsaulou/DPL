package com.darkpixellabyrinth.dpl;

import java.util.ArrayList;

public class Intersection {

    private Position intersectionPosition;
    private ArrayList<Intersection> listIntersection;

    public Intersection(Position intersectionPosition, ArrayList<Intersection> listIntersection) {
        this.intersectionPosition = intersectionPosition;
        this.listIntersection = listIntersection;
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
