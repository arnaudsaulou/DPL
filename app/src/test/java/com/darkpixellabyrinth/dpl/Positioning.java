package com.darkpixellabyrinth.dpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Positioning {

    @Test
    public void setPositionOK() {
        Position position = new Position(0, 0);
        assertEquals("Position{x=0, y=0}", position.toString());
    }

    @Test
    public void startPositionCharacterOK() {
        Position position = new Position(0, 0);
        PixelCharacter pixelCharacter = new PixelCharacter(position);
        assertEquals("Position{x=0, y=0}", pixelCharacter.getPosition().toString());
    }
}