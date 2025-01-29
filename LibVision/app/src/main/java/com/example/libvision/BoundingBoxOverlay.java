package com.example.imagepro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxOverlay extends View {

    private List<RectF> boxes = new ArrayList<>();

    public BoundingBoxOverlay(Context context) {
        super(context);
    }

    public BoundingBoxOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBoxes(List<RectF> boxes) {
        this.boxes = boxes;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (RectF box : boxes) {
            canvas.drawRect(box, paint);
        }
    }
}
