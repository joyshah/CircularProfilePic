package com.library.circularprofileupload.circularprofilepiclibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * Created by joy.shah.433 on 3/11/17
 */

public class CircularProfile extends android.support.v7.widget.AppCompatImageView {

    private static int CIRCULAR_BORDER_WIDTH = 2;
    private static int CONCENTRIC_CIRCLE_RADIUS = 25;
    private static float CONCENTRIC_CIRCLE_DEGREE = 45;
    private static boolean HIDE_CONCENTRIC_CIRCLE = false;


    private Paint circularPaint;
    private int circularBorderWidth;


    private Paint concentrcCirclePaint;
    private int concentricCircleRadius;
    private float concentricCircleDegree;

    private int circularBorderColor;
    private int concentricCircleColor;

    private boolean hideConcentricCircle;

    private Rounder rounder;

    public CircularProfile(Context context) {
        super(context);
        init(null);
    }

    public CircularProfile(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public CircularProfile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void init(AttributeSet attrs) {
        circularPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        concentrcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rounder = new Rounder(getDrawable());

        circularBorderWidth = CIRCULAR_BORDER_WIDTH;
        concentricCircleRadius = CONCENTRIC_CIRCLE_RADIUS;
        concentricCircleDegree = CONCENTRIC_CIRCLE_DEGREE;
        circularBorderColor = ContextCompat.getColor(getContext(), R.color.circularBorderColor);
        concentricCircleColor = ContextCompat.getColor(getContext(), R.color.concentricCircleColor);
        hideConcentricCircle = false;

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CircularProfile,
                    0, 0);

            circularBorderColor = a.getColor(R.styleable.CircularProfile_circular_border_color,
                    ContextCompat.getColor(getContext(), R.color.circularBorderColor));
            concentricCircleColor = a.getColor(R.styleable.CircularProfile_circular_border_color,
                    ContextCompat.getColor(getContext(), R.color.concentricCircleColor));


            circularBorderWidth = a.getInt(R.styleable.CircularProfile_circular_border_width,
                    CIRCULAR_BORDER_WIDTH);
            concentricCircleRadius = a.getInt(R.styleable.CircularProfile_concentric_circle_radius,
                    CONCENTRIC_CIRCLE_RADIUS);
            concentricCircleDegree = a.getFloat(R.styleable.CircularProfile_concentric_circle_degree,
                    CONCENTRIC_CIRCLE_DEGREE);


            hideConcentricCircle = a.getBoolean(R.styleable.CircularProfile_concentric_circle_degree,
                    HIDE_CONCENTRIC_CIRCLE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private class Rounder {


        Rounder(Drawable drawable) {
        }
    }
}
