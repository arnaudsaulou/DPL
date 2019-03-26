package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;

class PixelCharacter extends View {

    private Context context;
    private Position position;
    private PathBranch actualPathBranch;
    private Paint paint;
    private Rect character;

    public PixelCharacter(Context context) {
        super(context);
    }

    public PixelCharacter(Context context, Position position) {
        super(context);
        this.context = context;
        this.position = position;
        this.paint = new Paint();

        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

        this.character = new Rect(
                position.getxScreen(),
                position.getyScreen(),
                position.getxScreen() + sharedPreferences.getInt("boxSize", 0),
                position.getyScreen() + sharedPreferences.getInt("boxSize", 0));

        this.position = new Position(context, 0, 0);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void moveUp() {
        this.setPosition(new Position(this.context, this.getPosition().getX(), this.getPosition().getY() + 1));
    }

    public void moveDown() {
        this.setPosition(new Position(this.context, this.getPosition().getX(), this.getPosition().getY() - 1));
    }

    public void moveRight() {
        this.setPosition(new Position(this.context, this.getPosition().getX() + 1, this.getPosition().getY()));
    }

    public void moveLeft() {
        this.setPosition(new Position(this.context, this.getPosition().getX() - 1, this.getPosition().getY()));
    }

    public PathBranch getActualPathBranch() {
        return actualPathBranch;
    }

    public void setActualPathBranch(PathBranch actualPathBranch) {
        this.actualPathBranch = actualPathBranch;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.blue));

        canvas.drawRect(character, paint);
    }

}
