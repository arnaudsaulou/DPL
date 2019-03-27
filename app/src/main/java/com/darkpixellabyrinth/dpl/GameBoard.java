package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.View;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

public class GameBoard extends View {

    private Context context;
    private PixelCharacter pixelCharacter;
    private DrawPaths drawPaths;

    public GameBoard(Context context, Level level) {
        super(context);
        this.context = context;
        initGame(level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawPaths.draw(canvas);
        this.pixelCharacter.draw(canvas);
    }

    private void initGame(Level level) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

        this.pixelCharacter = new PixelCharacter(context, new Position(
                context,
                sharedPreferences.getInt("centerX", 0),
                sharedPreferences.getInt("centerY", 0)));

        if (level.getStartPathBranch().getStartPosition().equals(this.pixelCharacter.getPosition())) {
            this.pixelCharacter.setActualFloor(level.getStartPathBranch());
        } else {
            throw new IllegalStateException("The character doesn't start on a coherent path");
        }

        this.drawPaths = new DrawPaths(context, level);
    }

    //Check possible movements//

    private boolean canMoveUp() {
        return this.pixelCharacter.getActualFloor().getDirectionEnable().contains(Direction.UP) &&
                (this.pixelCharacter.getActualFloor().getEndPosition().getY() + 1 < this.pixelCharacter.getPosition().getY() &&
                        this.pixelCharacter.getActualFloor().getStartPosition().getY() >= this.pixelCharacter.getPosition().getY())
                ||
                (this.pixelCharacter.getActualFloor().getStartPosition().getY() + 1 <= this.pixelCharacter.getPosition().getY() &&
                        this.pixelCharacter.getActualFloor().getEndPosition().getY() >= this.pixelCharacter.getPosition().getY());
    }

    private boolean canMoveDown() {
        return this.pixelCharacter.getActualFloor().getDirectionEnable().contains(Direction.DOWN) &&
                (this.pixelCharacter.getActualFloor().getStartPosition().getY() > this.pixelCharacter.getPosition().getY() &&
                        this.pixelCharacter.getActualFloor().getEndPosition().getY() <= this.pixelCharacter.getPosition().getY())
                ||
                (this.pixelCharacter.getActualFloor().getEndPosition().getY() - 1 > this.pixelCharacter.getPosition().getY() &&
                        this.pixelCharacter.getActualFloor().getStartPosition().getY() <= this.pixelCharacter.getPosition().getY());
    }

    private boolean canMoveRight() {
        return this.pixelCharacter.getActualFloor().getDirectionEnable().contains(Direction.RIGHT) &&
                (this.pixelCharacter.getActualFloor().getEndPosition().getX() - 1 > this.pixelCharacter.getPosition().getX() &&
                        this.pixelCharacter.getActualFloor().getStartPosition().getX() <= this.pixelCharacter.getPosition().getX())
                ||
                (this.pixelCharacter.getActualFloor().getStartPosition().getX() > this.pixelCharacter.getPosition().getX() &&
                        this.pixelCharacter.getActualFloor().getEndPosition().getX() <= this.pixelCharacter.getPosition().getX());
    }

    private boolean canMoveLeft() {
        return this.pixelCharacter.getActualFloor().getDirectionEnable().contains(Direction.LEFT) &&
                (this.pixelCharacter.getActualFloor().getStartPosition().getX() >= this.pixelCharacter.getPosition().getX() &&
                        this.pixelCharacter.getActualFloor().getEndPosition().getX() + 1 < this.pixelCharacter.getPosition().getX())
                ||
                (this.pixelCharacter.getActualFloor().getEndPosition().getX() > this.pixelCharacter.getPosition().getX() &&
                        this.pixelCharacter.getActualFloor().getStartPosition().getX() + 1 <= this.pixelCharacter.getPosition().getX());
    }

    //Movements//

    public void goUp() {
        if (canMoveUp()) {
            this.pixelCharacter.moveUp();
            this.drawPaths.moveUP();
        }
    }

    public void goDown() {
        if (canMoveDown()) {
            this.pixelCharacter.moveDown();
            this.drawPaths.moveDown();
        }
    }

    public void goRigth() {
        if (canMoveRight()) {
            this.pixelCharacter.moveRight();
            this.drawPaths.moveRight();
        }
    }

    public void goLeft() {
        if (canMoveLeft()) {
            this.pixelCharacter.moveLeft();
            this.drawPaths.moveLeft();
        }
    }
}
