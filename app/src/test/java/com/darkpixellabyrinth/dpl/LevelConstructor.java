package com.darkpixellabyrinth.dpl;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LevelConstructor {

    @Test
    public void setPathLevelOK() {
        ArrayList<Position> path = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            path.add(new Position(i, i));
        }
        Level level = new Level(path);

        assertEquals("Level{path=[Position{x=0, y=0}, Position{x=1, y=1}, Position{x=2, y=2}, Position{x=3, y=3}, Position{x=4, y=4}, Position{x=5, y=5}, Position{x=6, y=6}, Position{x=7, y=7}, Position{x=8, y=8}, Position{x=9, y=9}]}", level.toString());
    }
}
