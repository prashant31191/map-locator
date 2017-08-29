package com.wallpaper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maplocator.R;
import com.utils.DrawView;
import com.utils.TouchView;

/**
 * Created by prashant.patel on 4/14/2017.
 */

public class ActFoggyGlass extends Activity {
    ImageView ivB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new TouchView(this));
        setContentView(R.layout.act_foggy_glass);
        RelativeLayout rlView = (RelativeLayout) findViewById(R.id.rlView);
        ivB = (ImageView) findViewById(R.id.ivB);
        rlView.addView(new DrawView(this));


       /* Bitmap bitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.image_b);

       *//* Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(50, 50, 10, paint);*//*


        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        punchHole(mutableBitmap,250,350,50);*/

    }



    public void punchHole(Bitmap bitmap, float cx, float cy, float radius) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
       // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(cx, cy, radius, paint);

        ivB.setImageBitmap(bitmap);
    }

}