package com.darkpixellabyrinth.dpl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

class PathLabyrinth extends View {

    private Paint paint;
    private ArrayList<Rect> path = new ArrayList<>();
    private int boxSize;
    private Position center;

    public PathLabyrinth(Context context, int boxSize, Position center) {
        super(context);
        this.paint = new Paint();
        this.boxSize = boxSize;
        this.center = center;

        this.path.add(new Rect(center.getX(), 0, center.getX() + this.boxSize, center.getY()));
        this.path.add(new Rect(center.getX() + this.boxSize, 5 * this.boxSize, center.getX() + 9 * this.boxSize, 6 * this.boxSize));
        this.path.add(new Rect(center.getX() - 11 * this.boxSize, 15 * this.boxSize, center.getX(), 16 * this.boxSize));

    }

/*    private Bitmap createBitmap(){
        Bitmap bitmap = Bitmap.createBitmap();

        return bitmap;
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.paint.setColor(getResources().getColor(R.color.lightwhite));

        for (Rect rect : this.path) {
            canvas.drawRect(rect, this.paint);

        }
    }
}
