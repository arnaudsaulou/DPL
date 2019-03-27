package com.darkpixellabyrinth.dpl;

import java.util.HashSet;

public interface Floor {
    HashSet<Direction> getDirectionEnable();

    Position getStartPosition();

    Position getEndPosition();
}
