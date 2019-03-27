package com.darkpixellabyrinth.dpl.Levels;

import android.content.Context;

import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Intersection;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Position;

import java.util.ArrayList;

public class Level1 extends Level {

    public Level1(Context context) {
        ArrayList<PathBranch> pathBranches = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();

        PathBranch p1 = new PathBranch(context, 5, new Position(context, 10, 19), Direction.UP);
        PathBranch p2 = new PathBranch(context, 5, new Position(context, 11, 14), Direction.RIGHT);

        Intersection i1 = new Intersection(new Position(context, 10, 14));
        i1.addLinkedPath(p1);
        i1.addLinkedPath(p2);
        intersections.add(i1);
        p1.addIntersection(i1);
        p2.addIntersection(i1);

        pathBranches.add(p1);
        pathBranches.add(p2);

        this.setPathBranches(pathBranches);
        this.setIntersections(intersections);
        this.setStartPathBranch(p1);
    }
}
