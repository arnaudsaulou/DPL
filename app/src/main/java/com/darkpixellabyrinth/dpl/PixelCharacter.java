package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

class PixelCharacter extends View {

    private Position position;
    private Paint paint;
    private Rect character;
    private int characterSize;

    public PixelCharacter(Context context, Position position, int characterSize) {
        super(context);
        this.position = position;
        this.paint = new Paint();
        this.characterSize = characterSize;
        this.character = new Rect(
                position.getX(),
                position.getY(),
                position.getX() + characterSize,
                position.getY() + characterSize);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
                "position=" + position +
                '}';
    }
}
