package com.darkpixellabyrinth.dpl.Levels;

import android.content.Context;

import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Position;

import java.util.ArrayList;

public class Level1 extends Level {

    public Level1(Context context) {
        ArrayList<PathBranch> pathBranches = new ArrayList<>();

        PathBranch p1 = new PathBranch(5, new Position(context, 0, 5), Direction.UP);
        PathBranch p2 = new PathBranch(5, new Position(context, 3, 0), Direction.DOWN);

        pathBranches.add(p1);
        pathBranches.add(p2);

        this.setPathBranches(pathBranches);
        this.setStartPathBranch(p1);
    }
}
