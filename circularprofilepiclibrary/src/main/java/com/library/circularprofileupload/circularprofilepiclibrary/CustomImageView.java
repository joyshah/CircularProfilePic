package com.library.circularprofileupload.circularprofilepiclibrary;

/**
 * Created by joy.shah.433 on 3/11/17
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CustomImageView extends AppCompatImageView {
    Context context;
    Paint paint = new Paint();
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap icon1;

    public CustomImageView(Context context) {
        super(context);
        this.context = context;
        icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_black_24dp);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_black_24dp);
        icon1 = drawableToBitmap(drawable);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_black_24dp);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && getWidth() != 0 && getHeight() != 0) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(Config.ARGB_8888, true);
            int w = getWidth();
            int h = getHeight();
            canvas.drawBitmap(getRoundedCroppedBitmap(bitmap, w), 0.0f, 0.0f, null);
            this.paint.setColor(Color.BLUE);
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth(6.0f);

            paint1.setStyle(Style.FILL);
            paint1.setColor(Color.BLUE);
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) ((w / 2) - 3), this.paint);
            canvas.drawCircle((float) ((int) (((double) ((getWidth() / 2))) + (((double) (w / 2)) * Math.cos(0.785d))))
                    , (float) ((int) (((double) ((getWidth() / 2))) + (((double) (w / 2)) * Math.cos(0.785d)))),
                    90,
                    this.paint1
            );
            // Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_black_24dp);
            Canvas canvas2 = canvas;
            canvas2.drawBitmap(icon1,
                    (float) ((int) (((double) ((getWidth() / 2) - (icon1.getWidth() / 2))) + (((double) (w / 2)) * Math.cos(0.785d)))),
                    (float) ((int) (((double) ((getHeight() / 2) - (icon1.getHeight() / 2))) + (((double) (w / 2)) * Math.sin(0.785d)))),
                    null);
        }
    }

    public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() == radius && bitmap.getHeight() == radius) {
            finalBitmap = bitmap;
        } else {
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        }
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle((float) (finalBitmap.getWidth() / 2), (float) (finalBitmap.getHeight() / 2), (float) (finalBitmap.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;
    }
}