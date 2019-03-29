package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.View;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

public class GameBoard extends View {

    private Context context;
    private PixelCharacter pixelCharacter;
    private DrawFloor drawFloor;

    public GameBoard(Context context, Level level) {
        super(context);
        this.context = context;
        initGame(level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawFloor.draw(canvas);
        this.pixelCharacter.draw(canvas);
    }

    private void initGame(Level level) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

        this.pixelCharacter = new PixelCharacter(context, new Position(
                context,
                sharedPreferences.getInt("centerX", 0),
                sharedPreferences.getInt("centerY", 0)));

        this.pixelCharacter.setCurrentFloor(level.getStartPathBranch());
        this.pixelCharacter.setCurrentPathBranch(level.getStartPathBranch());

        this.drawFloor = new DrawFloor(context, level);
    }


    //Check sub-movement//

    private boolean canMoveUpOnPathGoingDown() {
        return this.pixelCharacter.getCurrentFloor().getStartPosition().getY() + 1 <= this.pixelCharacter.getPosition().getY() &&
                this.pixelCharacter.getCurrentFloor().getEndPosition().getY() >= this.pixelCharacter.getPosition().getY();
    }

    private boolean canMoveUpOnPathGoingUp() {
        return this.pixelCharacter.getCurrentFloor().getEndPosition().getY() + 1 < this.pixelCharacter.getPosition().getY() &&
                this.pixelCharacter.getCurrentFloor().getStartPosition().getY() >= this.pixelCharacter.getPosition().getY();
    }

    private boolean canMoveDownOnPathGoingDown() {
        return this.pixelCharacter.getCurrentFloor().getEndPosition().getY() - 1 > this.pixelCharacter.getPosition().getY() &&
                this.pixelCharacter.getCurrentFloor().getStartPosition().getY() <= this.pixelCharacter.getPosition().getY();
    }

    private boolean canMoveDownOnPathGoingUp() {
        return this.pixelCharacter.getCurrentFloor().getStartPosition().getY() > this.pixelCharacter.getPosition().getY() &&
                this.pixelCharacter.getCurrentFloor().getEndPosition().getY() <= this.pixelCharacter.getPosition().getY();
    }

    private boolean canMoveRightOnPathGoingLeft() {
        return this.pixelCharacter.getCurrentFloor().getStartPosition().getX() > this.pixelCharacter.getPosition().getX() &&
                this.pixelCharacter.getCurrentFloor().getEndPosition().getX() <= this.pixelCharacter.getPosition().getX();
    }

    private boolean canMoveRightOnPathGoingRight() {
        return this.pixelCharacter.getCurrentFloor().getEndPosition().getX() - 1 > this.pixelCharacter.getPosition().getX() &&
                this.pixelCharacter.getCurrentFloor().getStartPosition().getX() <= this.pixelCharacter.getPosition().getX();
    }

    private boolean canMoveLeftOnPathGoingRight() {
        return this.pixelCharacter.getCurrentFloor().getEndPosition().getX() > this.pixelCharacter.getPosition().getX() &&
                this.pixelCharacter.getCurrentFloor().getStartPosition().getX() + 1 <= this.pixelCharacter.getPosition().getX();
    }

    private boolean canMoveLeftOnPathGoingLeft() {
        return this.pixelCharacter.getCurrentFloor().getStartPosition().getX() >= this.pixelCharacter.getPosition().getX() &&
                this.pixelCharacter.getCurrentFloor().getEndPosition().getX() < this.pixelCharacter.getPosition().getX();
    }

    //Check possible movements//

    private boolean canMoveUp() {
        return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.UP) &&
                (canMoveUpOnPathGoingUp() || canMoveUpOnPathGoingDown() || hasAnIntersectionAbove());
    }

    private boolean canMoveDown() {
        return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.DOWN) &&
                (canMoveDownOnPathGoingUp() || canMoveDownOnPathGoingDown() || hasAnIntersectionUnder());
    }

    private boolean canMoveRight() {
        return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.RIGHT) &&
                (canMoveRightOnPathGoingRight() || canMoveRightOnPathGoingLeft() || hasAnIntersectionAtRight());
    }

    private boolean canMoveLeft() {
        return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.LEFT) &&
                (canMoveLeftOnPathGoingLeft() || canMoveLeftOnPathGoingRight() || hasAnIntersectionAtLeft());
    }

    private void updateCurrentFloor(Direction direction) {
        this.pixelCharacter.updateCurrentFloor(direction);
    }

    private boolean hasAnIntersectionAbove() {
        if (this.pixelCharacter.getCurrentFloor() instanceof Intersection) {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.UP);
        } else {
            return false;
        }
    }

    private boolean hasAnIntersectionAtRight() {
        if (this.pixelCharacter.getCurrentFloor() instanceof Intersection) {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.RIGHT);
        } else {
            return false;
        }
    }

    private boolean hasAnIntersectionUnder() {
        if (this.pixelCharacter.getCurrentFloor() instanceof Intersection) {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.DOWN);
        } else {
            return false;
        }
    }

    private boolean hasAnIntersectionAtLeft() {
        if (this.pixelCharacter.getCurrentFloor() instanceof Intersection) {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.LEFT);
        } else {
            return false;
        }
    }

    //Movements//

    public void goUp() {
        if (canMoveUp()) {
            this.pixelCharacter.moveUp();
            this.drawFloor.moveUP();
            this.updateCurrentFloor(Direction.UP);
        }
    }

    public void goDown() {
        if (canMoveDown()) {
            this.pixelCharacter.moveDown();
            this.drawFloor.moveDown();
            this.updateCurrentFloor(Direction.DOWN);
        }
    }

    public void goRigth() {
        if (canMoveRight()) {
            this.pixelCharacter.moveRight();
            this.drawFloor.moveRight();
            this.updateCurrentFloor(Direction.RIGHT);
        }
    }

    public void goLeft() {
        if (canMoveLeft()) {
            this.pixelCharacter.moveLeft();
            this.drawFloor.moveLeft();
            this.updateCurrentFloor(Direction.LEFT);
        }
    }
}
