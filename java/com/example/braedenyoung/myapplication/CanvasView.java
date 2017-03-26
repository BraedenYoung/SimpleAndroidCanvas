package com.example.braedenyoung.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class CanvasView extends View {

    private static final int LINE_WIDTH = 10;
    private static final int SEGMENTS = 10;
    private static final int PADDING = 35;

    ArrayList<Block> blocks = new ArrayList<Block>();

    private int separator = 0;
    private int verticalLength = 0;
    private int verticalSegments = 0;

    public int width;
    public int height;

    Context context;

    private Block head;

    GestureDetector gestureDetector;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        width = getWidth();
        height = getHeight();

        gestureDetector = new GestureDetector(context, new GestureListener());

    }

    private class GestureListener extends OnSwipeListener {

        @Override
        public  boolean onSwipe(Direction direction){

            Log.d("TAP EVENT", "Direction : " + direction);

            if (direction == Direction.left) {
                if (head.col > 0) {
                    head.col -= 1;
                }
            } else if (direction == Direction.up) {
                if (head.row > 0) {
                    head.row -= 1;
                }
            } else if (direction == Direction.right) {
                if (head.col <= SEGMENTS - 2) {
                    head.col += 1;
                }
            } else if (direction == Direction.down) {
                if (head.row <= verticalSegments - 2) {
                    head.row += 1;
                }
            }
            return true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (width == 0 && height == 0) {
            width = getWidth();
            height = getHeight();
        }

        Paint paint = new Paint();

        // Clear the canvase
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        // Draw new assets
        drawGrid(canvas, paint);
        drawBlocks(canvas, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    protected void drawGrid(Canvas canvas, Paint paint) {

        if(separator == 0 || verticalLength == 0) {
            separator = (width - PADDING * 2) / SEGMENTS;

            verticalSegments = (int) Math.floor((height - PADDING * 2) / separator);
            verticalLength = verticalSegments * separator + PADDING ;
        }

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(LINE_WIDTH);

        // Vertical lines
        int w = PADDING;
        while ( w <= width - PADDING) {
            canvas.drawLine(w, PADDING, w, verticalLength, paint);
            w += separator;
        }

        // Horizontal lines
        int h = PADDING;
        while ( h <= height - PADDING) {

            canvas.drawLine(PADDING - (LINE_WIDTH/2), h,
                            width - PADDING + (LINE_WIDTH/2), h,
                            paint);
            h += separator;
        }
    }

    private void drawBlocks(Canvas canvas, Paint paint) {

        //Example Code
        if (head == null){
            head = new Block(1, 1, Color.GREEN);
        }

        blocks.add(head);

        for (Block b: blocks) {
            int col = b.col;
            int row = b.row;
            paint.setColor(b.color);

            canvas.drawRect(PADDING + LINE_WIDTH/2 + (separator*col),
                    PADDING + LINE_WIDTH/2 + (separator*row),
                    PADDING - LINE_WIDTH/2 + (separator*(col+1)),
                    PADDING - LINE_WIDTH/2 + (separator*(row+1)),
                    paint);
        }
    }
}
