package com.darkpixellabyrinth.dpl.Levels;

import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Intersection;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Positions.Position;

import java.util.ArrayList;

public class Level1 extends Level {

    public Level1() {
        ArrayList<PathBranch> pathBranches = new ArrayList<>();

        PathBranch p3 = new PathBranch(4, new Position(-1, 0), Direction.RIGHT);

        PathBranch p5 = new PathBranch(5);
        PathBranch p6 = new PathBranch(10);
        PathBranch p7 = new PathBranch(4);

        p3.addIntersection(new Intersection(Direction.UP, p5, new Position(3, 0), p3));

        p5.addIntersection(new Intersection(Direction.LEFT, p6, new Position(3, 5), p5));

        p6.addIntersection(new Intersection(Direction.UP, p7, new Position(-5, 5), p6));

        pathBranches.add(p3);

        this.setPathBranches(pathBranches);
        this.setStartPathBranch(p3);
    }
}
