package com.liyi.sutils.utils.image;

import android.graphics.drawable.GradientDrawable;


public class SShapeUtil {

    public static GradientDrawable getRectShape(int fillColor, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        return shape;
    }

    public static GradientDrawable getRectShape(int fillColor, int radius, int strokeColor, int strokeWidth) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }

    public static GradientDrawable getRectShape(int fillColor, float[] radii, int strokeColor, int strokeWidth) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadii(radii);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }
}
