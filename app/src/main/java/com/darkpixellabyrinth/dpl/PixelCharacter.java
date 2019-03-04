package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.view.View;

import com.darkpixellabyrinth.dpl.Positions.Position;
import com.darkpixellabyrinth.dpl.Positions.ScreenPosition;

class PixelCharacter extends View {

    private ScreenPosition screenPosition;
    private Position position;
    private PathBranch actualPathBranch;
    private Paint paint;
    private Rect character;

    public PixelCharacter(Context context){
        super(context);
    }

    public PixelCharacter(Context context, ScreenPosition screenPosition) {
        super(context);
        this.screenPosition = screenPosition;
        this.paint = new Paint();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StartMenu.getContextOfApplication());
        this.character = new Rect(
                screenPosition.getX(),
                screenPosition.getY(),
                screenPosition.getX() + prefs.getInt("boxSize", 0),
                screenPosition.getY() + prefs.getInt("boxSize", 0));

        this.position = new Position(0, 0);
    }

    public ScreenPosition getScreenPosition() {
        return screenPosition;
    }

    public void setScreenPosition(ScreenPosition screenPosition) {
        this.screenPosition = screenPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void moveUp() {
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY() + 1));
    }

    public void moveDown() {
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY() - 1));
    }

    public void moveRight() {
        this.setPosition(new Position(this.getPosition().getX() + 1, this.getPosition().getY()));
    }

    public void moveLeft() {
        this.setPosition(new Position(this.getPosition().getX() - 1, this.getPosition().getY()));
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

    @Override
    public String toString() {
        return "PixelCharacter{" +
                "screenPosition=" + screenPosition +
                '}';
    }
}
