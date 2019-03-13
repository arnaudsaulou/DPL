package com.darkpixellabyrinth.dpl.Levels;

import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Positions.Position;

import java.util.ArrayList;

public class Level1 extends Level {

    public Level1() {
        ArrayList<PathBranch> pathBranches = new ArrayList<>();

        PathBranch p1 = new PathBranch(4, new Position(-1, 0), Direction.RIGHT);
        PathBranch p2 = new PathBranch(4, new Position(3, 0), Direction.UP);

        pathBranches.add(p1);
        pathBranches.add(p2);

        this.setPathBranches(pathBranches);
        this.setStartPathBranch(p1);
    }
}
