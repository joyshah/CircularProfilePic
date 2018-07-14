package com.library.circularprofileupload.circularprofilepiclibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by joy.shah on 3/11/17
 */

public class CircularProfile extends AppCompatImageView {

    private final static int CIRCULAR_BORDER_WIDTH = 5;
    private final static int CONCENTRIC_CIRCLE_RADIUS = 30;
    private final static float CONCENTRIC_CIRCLE_DEGREE = 45;
    private final static boolean HIDE_CONCENTRIC_CIRCLE = false;
    private final static int CIRCULAR_BORDER_RADIUS = -1;
    Bitmap inputBitmap;
    float xBias = 0, yBias = 0;
    private Paint circularPaint;
    private int circularBorderWidth;
    private Paint concentricCirclePaint;
    private int concentricCircleRadius;
    private float concentricCircleDegree;
    private int circularBorderRadius;
    private int circularBorderColor;
    private int concentricCircleColor;
    private boolean hideConcentricCircle;
    private Bitmap concentricCircleImage;
    private int mHeight = 0, mWidth = 0;

    public CircularProfile(Context context) {
        super(context);
        init(null);
    }

    public CircularProfile(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircularProfile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
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
        concentricCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        inputBitmap = ((BitmapDrawable) getDrawable()).getBitmap().copy(Bitmap.Config.ARGB_8888, true);


        circularBorderWidth = CIRCULAR_BORDER_WIDTH;
        concentricCircleRadius = CONCENTRIC_CIRCLE_RADIUS;
        concentricCircleDegree = CONCENTRIC_CIRCLE_DEGREE;
        hideConcentricCircle = HIDE_CONCENTRIC_CIRCLE;
        circularBorderColor = ContextCompat.getColor(getContext(), R.color.circularBorderColor);
        concentricCircleColor = ContextCompat.getColor(getContext(), R.color.concentricCircleColor);
        circularBorderRadius = CIRCULAR_BORDER_RADIUS;

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_black_24dp);
        concentricCircleImage = drawableToBitmap(drawable);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CircularProfile,
                    0, 0);

            circularBorderColor = a.getColor(R.styleable.CircularProfile_circular_border_color,
                    ContextCompat.getColor(getContext(), R.color.circularBorderColor));
            concentricCircleColor = a.getColor(R.styleable.CircularProfile_concentric_circle_color,
                    ContextCompat.getColor(getContext(), R.color.concentricCircleColor));


            circularBorderWidth = a.getInt(R.styleable.CircularProfile_circular_border_width,
                    CIRCULAR_BORDER_WIDTH);
            concentricCircleRadius = a.getInt(R.styleable.CircularProfile_concentric_circle_radius,
                    CONCENTRIC_CIRCLE_RADIUS);
            concentricCircleDegree = a.getFloat(R.styleable.CircularProfile_concentric_circle_degree,
                    CONCENTRIC_CIRCLE_DEGREE);
            circularBorderRadius = a.getInt(R.styleable.CircularProfile_circular_border_radius,
                    CIRCULAR_BORDER_RADIUS);


            hideConcentricCircle = a.getBoolean(R.styleable.CircularProfile_concentric_circle_degree,
                    HIDE_CONCENTRIC_CIRCLE);
        }
        circularPaint.setColor(circularBorderColor);
        circularPaint.setStyle(Paint.Style.STROKE);
        circularPaint.setStrokeWidth(circularBorderWidth);
        concentricCirclePaint.setColor(concentricCircleColor);
        concentricCirclePaint.setStyle(Paint.Style.FILL);
        calculateBias();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
        setCircularBorderRadius();
        drawCircularImage(canvas);
        drawCircularCircle(canvas);
        if (!hideConcentricCircle)
            drawConcentricCircle(canvas);
    }

    private void drawCircularImage(Canvas canvas) {
        canvas.drawBitmap(getRoundedCroppedBitmap(inputBitmap, circularBorderRadius + circularBorderRadius), mWidth / 2 - circularBorderRadius, mHeight / 2 - circularBorderRadius, null);
    }

    private void drawCircularCircle(Canvas canvas) {

        canvas.drawCircle((float) (mWidth / 2), (float) (mHeight / 2), (float) circularBorderRadius, circularPaint);
    }

    private void drawConcentricCircle(Canvas canvas) {

        canvas.drawCircle((float) ((int) (((double) ((mWidth / 2))) + (((double) (circularBorderRadius + circularBorderWidth / 2)) * Math.cos(Math.toRadians(concentricCircleDegree))) + xBias))
                , (float) ((int) (((double) ((mHeight / 2))) + (((double) (circularBorderRadius + circularBorderWidth / 2)) * Math.sin(Math.toRadians(concentricCircleDegree))) + yBias)),
                concentricCircleRadius,
                concentricCirclePaint
        );

        canvas.drawBitmap(concentricCircleImage,
                (float) ((int) (((double) ((mWidth / 2) - (concentricCircleImage.getWidth() / 2))) + (((double) (circularBorderRadius + circularBorderWidth / 2)) * Math.cos(Math.toRadians(concentricCircleDegree))) + xBias)),
                (float) ((int) (((double) ((mHeight / 2) - (concentricCircleImage.getHeight() / 2))) + (((double) (circularBorderRadius + circularBorderWidth / 2)) * Math.sin(Math.toRadians(concentricCircleDegree))) + yBias)),
                null);
    }


    private void setCircularBorderRadius() {
        if (circularBorderRadius == -1) {
            setDefaultCircularBorderRadius();
        } else {
            if (!(circularBorderRadius > 0 && (circularBorderRadius < mWidth / 2 || circularBorderRadius < mHeight / 2))) {
                setDefaultCircularBorderRadius();
            }
        }
    }

    private void setDefaultCircularBorderRadius() {
        if (mWidth < mHeight) {
            circularBorderRadius = mWidth / 2 - circularBorderWidth;
        } else {
            circularBorderRadius = mHeight / 2 - circularBorderWidth;
        }
    }

    private void calculateBias() {
        xBias = (float) circularBorderWidth / 2;
        yBias = xBias;
        if (concentricCircleDegree >= 0 && concentricCircleDegree < 90) {
            xBias = xBias * -1;
            yBias = yBias * -1;
        } else if (concentricCircleDegree >= 90 && concentricCircleDegree < 180) {
            xBias = xBias * 1;
            yBias = yBias * -1;
        } else if (concentricCircleDegree >= 180 && concentricCircleDegree < 270) {
            xBias = xBias * 1;
            yBias = yBias * 1;
        } else {
            xBias = xBias * -1;
            yBias = yBias * 1;
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;
            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }

            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);


            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(maxWidth, maxHeight, conf);
            Canvas c = new Canvas(bmp);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.parseColor("#FFFFFFFF"));
            c.drawRect(0, 0, bmp.getWidth(), bmp.getHeight(), p);
            Rect r_src = new Rect(0, 0, image.getWidth(), image.getHeight());
            int c_main_x = maxWidth / 2;
            int c_main_y = maxHeight / 2;
            int c_dest_x = image.getWidth() / 2;
            int c_dest_y = image.getHeight() / 2;
            int c_diff_x = c_main_x - c_dest_x;
            int c_diff_y = c_main_y - c_dest_y;
            int final_x = c_diff_x;
            int final_y = c_diff_y;
            Rect r_dest = new Rect(final_x, final_y, final_x + image.getWidth(), final_y + image.getHeight());
            c.drawBitmap(image, r_src, r_dest, null);

            return bmp;
        } else {
            return image;
        }
    }

    public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        bitmap = resize(bitmap, radius, radius);
        Bitmap finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f, finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
