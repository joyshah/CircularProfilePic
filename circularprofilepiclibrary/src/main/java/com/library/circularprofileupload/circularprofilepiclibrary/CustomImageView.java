package com.library.circularprofileupload.circularprofilepiclibrary;

import android.content.Context;
import android.content.res.Resources;
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
    public static final float add_btn_angle = 45;

    Context context;
    Paint paint = new Paint();
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap icon1;
    Drawable drawable;

    public CustomImageView(Context context) {
        super(context);
        this.context = context;
        icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_black_24dp);
        drawable = getDrawable();

    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        Drawable drawable1 = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_black_24dp);
        icon1 = drawableToBitmap(drawable1);
        drawable = getDrawable();

    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_bl_24dp);
        drawable = getDrawable();

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

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius, Resources rr) {

        /*if(bitmap.getHeight()>radius){}

        DisplayMetrics dm = rr.getDisplayMetrics();
        float aspectRatio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
        int ImageWidht = radius;
        int ImageHeight = Math.round(aspectRatio*ImageWidht);

        Log.i("hot", dm.widthPixels + " " + dm.heightPixels);*/
        bitmap = resize(bitmap, radius, radius);
        Bitmap finalBitmap = bitmap;


        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Config.ARGB_8888);
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
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);


        return output;
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
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
            //Log.i("hot",bmp.si)
            return bmp;
        } else {
            return image;
        }
    }

    protected void onDraw(Canvas canvas) {

        if (drawable != null && getWidth() != 0 && getHeight() != 0) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(Config.ARGB_8888, true);
            int w = getWidth();
            int h = getHeight();
          /*  Bitmap vv = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
            Bitmap v = getRoundedCroppedBitmap(vv, w);*/

            canvas.drawBitmap(getRoundedCroppedBitmap(bitmap, w - 15, getResources()), 7.5f, 7.5f, null);
            this.paint.setColor(Color.BLUE);
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth(15.0f);

            //Log.i("Hot", "w " + w + " h " + h + " bw" + v.getWidth() + " bh " + v.getHeight());

            paint1.setStyle(Style.FILL);
            paint1.setColor(Color.BLUE);

            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) ((w / 2) - 13), this.paint);
            canvas.drawCircle((float) ((int) (((double) ((getWidth() / 2))) + (((double) (w / 2)) * Math.cos(Math.toRadians(add_btn_angle))) - 7.5f))
                    , (float) ((int) (((double) ((getWidth() / 2))) + (((double) (w / 2)) * Math.sin(Math.toRadians(add_btn_angle))) - 7.5f)),
                    30,
                    this.paint1
            );
            canvas.drawBitmap(icon1,
                    (float) ((int) (((double) ((getWidth() / 2) - (icon1.getWidth() / 2))) + (((double) (w / 2)) * Math.cos(Math.toRadians(add_btn_angle))) - 7.5f)),
                    (float) ((int) (((double) ((getHeight() / 2) - (icon1.getHeight() / 2))) + (((double) (w / 2)) * Math.sin(Math.toRadians(add_btn_angle))) - 7.5f)),
                    null);
        }

    }

}