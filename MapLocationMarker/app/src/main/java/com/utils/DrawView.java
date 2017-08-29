package com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.maplocator.R;

/**
 * Created by test on 4/17/2017.
 */

public class DrawView extends View implements View.OnTouchListener {

    private int x = 0;
    private int y = 0;

    Bitmap bitmap,bp;
    Path circlePath;
    Paint circlePaint;

    Canvas bitmapCanvas = null;

    private final Paint paint = new Paint();
    private final Paint eraserPaint = new Paint();

    Context mContext;


    public DrawView(Context context) {
        super(context);

        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

        // Set background
     //   this.setBackgroundColor(Color.CYAN);
        bp = BitmapFactory.decodeResource(getResources(), R.drawable.image_b);

        // Set bitmap
      //  bitmap = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
        bitmap =  Bitmap.createBitmap(594, 996, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas();
        bitmapCanvas.setBitmap(bitmap);
        bitmapCanvas.drawColor(Color.TRANSPARENT);
        bitmapCanvas.drawBitmap(bp, 0, 0, null);

        circlePath = new Path();
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);

        // Set eraser paint properties
        eraserPaint.setAlpha(0x55ffffff);
        eraserPaint.setStrokeJoin(Paint.Join.ROUND);
        eraserPaint.setStrokeCap(Paint.Cap.ROUND);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //paint.setAlpha(0x55ffffff);//transperent color
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//clear the draw
        eraserPaint.setAntiAlias(true);

    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmapCanvas.drawCircle(x, y, 30, eraserPaint);

        canvas.drawPath(circlePath, circlePaint);
    }

    public boolean onTouch(View view, MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();

        bitmapCanvas.drawCircle(x, y, 30, eraserPaint);

        circlePath.reset();
        circlePath.addCircle(x, y, 30, Path.Direction.CW);

        int ac = event.getAction();
        switch (ac) {
            case MotionEvent.ACTION_UP:
                Toast.makeText(mContext , String.valueOf(x), Toast.LENGTH_SHORT).show();
                circlePath.reset();
                break;
        }
        invalidate();
        return true;
    }
}

