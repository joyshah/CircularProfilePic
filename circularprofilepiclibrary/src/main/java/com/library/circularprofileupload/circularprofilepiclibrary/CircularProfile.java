package com.library.circularprofileupload.circularprofilepiclibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
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

    private Bitmap inputBitmap;
    /* private float xBias = 0, yBias = 0;*/
    private Paint mCircularBorderPaint;
    private Paint mConcentricCirclePaint;
    private int mCircularBorderWidth;
    private int mConcentricCircleRadius;
    private float mConcentricCircleDegree;
    private int mCircularBorderRadius;
    private int mInputCircularBorderRadius;
    private boolean mHideConcentricCircle;
    private Bitmap mConcentricCircleImage;
    private int mHeight = 0, mWidth = 0;
    private float concentricCircleCentreX = 0, concentricCircleCentreY = 0;
    private CircularProfileClickListener mCircularProfileClickListener;

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
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getRoundBitmap(Bitmap bmp, int radius) {
        Bitmap sBmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sBmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
        } else {
            sBmp = bmp;
        }

        Bitmap bitmap = sBmp;
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius / 2, paint);

        return sBmp;
   /*     Bitmap output = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(radius / 2 + 0.7f,
                radius / 2 + 0.7f, radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sBmp, rect, rect, paint);*/

    }

    private void init(AttributeSet attrs) {
        int circularBorderColor;
        int concentricCircleColor;
        mCircularBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mConcentricCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        inputBitmap = ((BitmapDrawable) getDrawable()).getBitmap().copy(Bitmap.Config.ARGB_8888, true);


        mCircularBorderWidth = CIRCULAR_BORDER_WIDTH;
        mConcentricCircleRadius = CONCENTRIC_CIRCLE_RADIUS;
        mConcentricCircleDegree = CONCENTRIC_CIRCLE_DEGREE;
        mHideConcentricCircle = HIDE_CONCENTRIC_CIRCLE;
        circularBorderColor = ContextCompat.getColor(getContext(), R.color.circularBorderColor);
        concentricCircleColor = ContextCompat.getColor(getContext(), R.color.concentricCircleColor);
        mCircularBorderRadius = CIRCULAR_BORDER_RADIUS;
        mInputCircularBorderRadius = CIRCULAR_BORDER_RADIUS;
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_black_24dp);
        mConcentricCircleImage = drawableToBitmap(drawable);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CircularProfile,
                    0, 0);

            circularBorderColor = a.getColor(R.styleable.CircularProfile_circular_border_color,
                    ContextCompat.getColor(getContext(), R.color.circularBorderColor));
            concentricCircleColor = a.getColor(R.styleable.CircularProfile_concentric_circle_color,
                    ContextCompat.getColor(getContext(), R.color.concentricCircleColor));


            mCircularBorderWidth = a.getInt(R.styleable.CircularProfile_circular_border_width,
                    CIRCULAR_BORDER_WIDTH);
            mConcentricCircleRadius = a.getInt(R.styleable.CircularProfile_concentric_circle_radius,
                    CONCENTRIC_CIRCLE_RADIUS);
            mConcentricCircleDegree = a.getFloat(R.styleable.CircularProfile_concentric_circle_degree,
                    CONCENTRIC_CIRCLE_DEGREE);
            mInputCircularBorderRadius = a.getInt(R.styleable.CircularProfile_circular_border_radius,
                    CIRCULAR_BORDER_RADIUS);


            mHideConcentricCircle = a.getBoolean(R.styleable.CircularProfile_concentric_circle_degree,
                    HIDE_CONCENTRIC_CIRCLE);
        }
        mCircularBorderPaint.setColor(circularBorderColor);
        mCircularBorderPaint.setStyle(Paint.Style.STROKE);
        mCircularBorderPaint.setStrokeWidth(mCircularBorderWidth);
        mConcentricCirclePaint.setColor(concentricCircleColor);
        mConcentricCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
       /* calculateBias();*/
        setCircularBorderRadius();
        drawCircularImage(canvas);
        drawCircularCircle(canvas);
        if (!mHideConcentricCircle) {
            drawConcentricCircle(canvas);
        }
        System.gc();
    }

    private void drawCircularImage(Canvas canvas) {
        //getRoundedCroppedBitmap(mCircularBorderRadius + mCircularBorderRadius);
        canvas.drawBitmap(getRoundBitmap(inputBitmap, mCircularBorderRadius + mCircularBorderRadius), mWidth / 2 - mCircularBorderRadius, mHeight / 2 - mCircularBorderRadius, null);
    }

    private void drawCircularCircle(Canvas canvas) {

        canvas.drawCircle((float) (mWidth / 2), (float) (mHeight / 2), (float) mCircularBorderRadius, mCircularBorderPaint);
    }

    private void drawConcentricCircle(Canvas canvas) {

        concentricCircleCentreX = (float) ((int) (((double) ((mWidth / 2))) + (((double) (mCircularBorderRadius)) * Math.cos(Math.toRadians(mConcentricCircleDegree)))));
        concentricCircleCentreY = (float) ((int) (((double) ((mHeight / 2))) + (((double) (mCircularBorderRadius)) * Math.sin(Math.toRadians(mConcentricCircleDegree)))));
        Log.i("Tes", "" + (concentricCircleCentreX));
        Log.i("Tes", "" + (concentricCircleCentreY));
        canvas.drawCircle(concentricCircleCentreX, concentricCircleCentreY, mConcentricCircleRadius, mConcentricCirclePaint);

        canvas.drawBitmap(mConcentricCircleImage,
                (float) ((int) (((double) ((mWidth / 2) - (mConcentricCircleImage.getWidth() / 2))) + (((double) (mCircularBorderRadius)) * Math.cos(Math.toRadians(mConcentricCircleDegree))))),
                (float) ((int) (((double) ((mHeight / 2) - (mConcentricCircleImage.getHeight() / 2))) + (((double) (mCircularBorderRadius)) * Math.sin(Math.toRadians(mConcentricCircleDegree))))),
                null);
    }

    private void setCircularBorderRadius() {
        if (mInputCircularBorderRadius == -1 || !((mInputCircularBorderRadius > 0 && (mInputCircularBorderRadius < mWidth / 2 || mInputCircularBorderRadius < mHeight / 2)))) {
            setDefaultCircularBorderRadius();
        } else {
            mCircularBorderRadius = mInputCircularBorderRadius;
        }
    }

    private void setDefaultCircularBorderRadius() {
        if (mWidth < mHeight) {
            mCircularBorderRadius = mWidth / 2 - mCircularBorderWidth / 2;
        } else {
            mCircularBorderRadius = mHeight / 2 - mCircularBorderWidth / 2;
        }
    }

    /* private void calculateBias() {
         xBias = (float) mCircularBorderWidth / 2;
         yBias = xBias;
         if (mConcentricCircleDegree >= 0 && mConcentricCircleDegree < 90) {
             xBias = xBias * -1;
             yBias = yBias * -1;
         } else if (mConcentricCircleDegree >= 90 && mConcentricCircleDegree < 180) {
             xBias = xBias * 1;
             yBias = yBias * -1;
         } else if (mConcentricCircleDegree >= 180 && mConcentricCircleDegree < 270) {
             xBias = xBias * 1;
             yBias = yBias * 1;
         } else {
             xBias = xBias * -1;
             yBias = yBias * 1;
         }
     }
 */
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
            Bitmap scaled_centered = Bitmap.createBitmap(maxWidth, maxHeight, conf);
            Canvas c = new Canvas(scaled_centered);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.parseColor("#FFFFFFFF"));
            c.drawRect(0, 0, scaled_centered.getWidth(), scaled_centered.getHeight(), p);
            int c_diff_x = (int) ((float) maxWidth / 2 - (float) image.getWidth() / 2);
            int c_diff_y = (int) ((float) maxHeight / 2 - (float) image.getHeight() / 2);
            Rect src_rect = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect dest_rect = new Rect(c_diff_x, c_diff_y, c_diff_x + image.getWidth(), c_diff_y + image.getHeight());
            c.drawBitmap(image, src_rect, dest_rect, null);

            return scaled_centered;
        } else {
            return image;
        }
    }

    public void getRoundedCroppedBitmap(int diameter) {
        //inputBitmap = resize(inputBitmap, diameter, diameter);

        Bitmap output = Bitmap.createBitmap(inputBitmap.getWidth(),
                inputBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#BAB399"));

        final Rect rect = new Rect(0, 0, inputBitmap.getWidth(), inputBitmap.getHeight());
        final Rect rect1 = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(inputBitmap.getWidth() / 2, inputBitmap.getHeight() / 2, inputBitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inputBitmap, rect, rect1, paint);
        inputBitmap = output;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int[] loc = new int[2];

        float newx = event.getX();
        float newy = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("x", "x:" + newx);
                Log.i("y", "y:" + newy);
                if (newx < concentricCircleCentreX + mConcentricCircleRadius && newx > concentricCircleCentreX - mConcentricCircleRadius
                        && newy < concentricCircleCentreY + mConcentricCircleRadius && newy > concentricCircleCentreY - mConcentricCircleRadius) {
                    if (mCircularProfileClickListener != null) {
                        mCircularProfileClickListener.onConcentricCircleClick();
                    }
                } else {
                    if (mCircularProfileClickListener != null)
                        mCircularProfileClickListener.onClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public void setCircularProfileClickListener(CircularProfileClickListener mCircularProfileClickListener) {
        this.mCircularProfileClickListener = mCircularProfileClickListener;
    }

    public void setCircularBorderWidth(int width) {
        this.mCircularBorderWidth = width;
        mCircularBorderPaint.setStrokeWidth(mCircularBorderWidth);
        postInvalidate();
    }

    public void setCircularBorderColor(int color) {
        mCircularBorderPaint.setColor(color);
        postInvalidate();
    }

    public void setConcentricCircleRadius(int radius) {
        this.mConcentricCircleRadius = radius;
        postInvalidate();
    }

    public void setConcentricCircleDegree(float degree) {
        this.mConcentricCircleDegree = degree;
        postInvalidate();
    }

    public void setConcentricCircleColor(int color) {
        this.mConcentricCirclePaint.setColor(color);
        postInvalidate();
    }

    public void setHideConcentricCircle(boolean hideConcentricCircle) {
        this.mHideConcentricCircle = hideConcentricCircle;
        postInvalidate();
    }

    public void setConcentricCircleImage(int drawable) {
        this.mConcentricCircleImage = drawableToBitmap(ContextCompat.getDrawable(getContext(), drawable));
        postInvalidate();
    }

    public void setCircularBorderRadius(int radius) {
        this.mInputCircularBorderRadius = radius;
        postInvalidate();
    }
}
