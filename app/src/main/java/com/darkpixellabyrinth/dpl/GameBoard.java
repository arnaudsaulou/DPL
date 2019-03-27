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

        if (level.getStartPathBranch().getStartPosition().equals(this.pixelCharacter.getPosition())) {
            this.pixelCharacter.setCurrentFloor(level.getStartPathBranch());
            this.pixelCharacter.setCurrentPathBranch(level.getStartPathBranch());
        } else {
            throw new IllegalStateException("The character doesn't start on a coherent path");
        }

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
                this.pixelCharacter.getCurrentFloor().getEndPosition().getX() + 1 < this.pixelCharacter.getPosition().getX();
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

    //Check if character has change of floor (path or intersection)//

    private void checkCurrentFloor(Direction direction) {
        if (this.pixelCharacter.getCurrentFloor() instanceof PathBranch) {
            if (endIntersectionExist() && characterOnEndIntersection()) {
                changeCurrentFloor(this.pixelCharacter.getCurrentPathBranch().getEndIntersection());
            }
            if (startIntersectionExist() && characterOnStartIntersection()) {
                changeCurrentFloor(this.pixelCharacter.getCurrentPathBranch().getStartIntersection());
            }
        } else {
            Intersection intersection = null;

            if (endIntersectionExist() && (direction == Direction.RIGHT || direction == Direction.UP)) {
                intersection = this.pixelCharacter.getCurrentPathBranch().getEndIntersection();
            } else if (startIntersectionExist() && (direction == Direction.LEFT || direction == Direction.DOWN)) {
                intersection = this.pixelCharacter.getCurrentPathBranch().getStartIntersection();
            }

            System.out.println(intersection);


            switch (direction) {
                case UP:
                    changeCurrentFloor(intersection.getPathBranchUp());
                    break;
                case RIGHT:
                    changeCurrentFloor(intersection.getPathBranchRight());
                    break;
                case DOWN:
                    changeCurrentFloor(intersection.getPathBranchDown());
                    break;
                case LEFT:
                    changeCurrentFloor(intersection.getPathBranchLeft());
                    break;
                default:
                    throw new IllegalStateException("Invalid direction");
            }

        }
    }

    private void changeCurrentFloor(Floor newFloor) {
        if (newFloor instanceof PathBranch) {
            PathBranch p = (PathBranch) newFloor;
            this.pixelCharacter.setCurrentPathBranch(p);
        }
        this.pixelCharacter.setCurrentFloor(newFloor);
    }

    private boolean characterOnStartIntersection() {
        return this.pixelCharacter.getPosition().equals(this.pixelCharacter.getCurrentPathBranch().getStartIntersection().getStartPosition());
    }

    private boolean characterOnEndIntersection() {
        return this.pixelCharacter.getPosition().equals(this.pixelCharacter.getCurrentPathBranch().getEndIntersection().getStartPosition());
    }

    private boolean startIntersectionExist() {
        return this.pixelCharacter.getCurrentPathBranch().getStartIntersection() != null;
    }

    private boolean endIntersectionExist() {
        return this.pixelCharacter.getCurrentPathBranch().getEndIntersection() != null;
    }

    private boolean hasAnIntersectionAbove() {
        if (this.pixelCharacter.getCurrentFloor() instanceof PathBranch) {
            if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.UP) {
                return endIntersectionExist();
            } else if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.DOWN) {
                return startIntersectionExist();
            } else {
                throw new IllegalStateException("Somthing went wrong");
            }
        } else {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.UP);
        }
    }

    private boolean hasAnIntersectionAtRight() {
        if (this.pixelCharacter.getCurrentFloor() instanceof PathBranch) {
            if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.RIGHT) {
                return endIntersectionExist();
            } else if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.LEFT) {
                return startIntersectionExist();
            } else {
                throw new IllegalStateException("Somthing went wrong");
            }
        } else {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.RIGHT);
        }
    }

    private boolean hasAnIntersectionUnder() {
        if (this.pixelCharacter.getCurrentFloor() instanceof PathBranch) {
            if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.DOWN) {
                return endIntersectionExist();
            } else if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.UP) {
                return startIntersectionExist();
            } else {
                throw new IllegalStateException("Somthing went wrong");
            }
        } else {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.DOWN);
        }
    }

    private boolean hasAnIntersectionAtLeft() {
        if (this.pixelCharacter.getCurrentFloor() instanceof PathBranch) {
            if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.LEFT) {
                return endIntersectionExist();
            } else if (this.pixelCharacter.getCurrentPathBranch().getDirection() == Direction.RIGHT) {
                return startIntersectionExist();
            } else {
                throw new IllegalStateException("Somthing went wrong");
            }
        } else {
            return this.pixelCharacter.getCurrentFloor().getDirectionEnable().contains(Direction.LEFT);
        }
    }

    //Movements//

    public void goUp() {
        if (canMoveUp()) {
            this.pixelCharacter.moveUp();
            this.drawFloor.moveUP();
            this.checkCurrentFloor(Direction.UP);
        }
    }

    public void goDown() {
        if (canMoveDown()) {
            this.pixelCharacter.moveDown();
            this.drawFloor.moveDown();
            this.checkCurrentFloor(Direction.DOWN);
        }
    }

    public void goRigth() {
        if (canMoveRight()) {
            System.out.println((this.pixelCharacter.getCurrentFloor()));
            this.pixelCharacter.moveRight();
            this.drawFloor.moveRight();
            this.checkCurrentFloor(Direction.RIGHT);
        }
    }

    public void goLeft() {
        if (canMoveLeft()) {
            this.pixelCharacter.moveLeft();
            this.drawFloor.moveLeft();
            this.checkCurrentFloor(Direction.LEFT);
        }
    }
}
