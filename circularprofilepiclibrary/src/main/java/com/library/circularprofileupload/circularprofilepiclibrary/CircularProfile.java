package com.library.circularprofileupload.circularprofilepiclibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joy.shah.433 on 3/11/17
 */

public class CircularProfile extends View {

    private Paint circularPaint;
    private int circularBorderWidth;


    private Paint concentrcCirclePaint;
    private int concentrcCircleRadius;
    private float concentrcCircleDegree;


    private CircularProfile(Context context) {
        super(context);
        init(null);
    }

    public CircularProfile(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircularProfile(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public CircularProfile(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        circularPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        concentrcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        circularBorderWidth = 2;
        circularBorderWidth = 45;

        if (attrs != null) {

        }
    }


}
