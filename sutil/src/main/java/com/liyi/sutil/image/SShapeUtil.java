package com.liyi.sutil.image;

import android.graphics.drawable.GradientDrawable;

public class SShapeUtil {

    private SShapeUtil() {
        /** cannot be instantiated */
        throw new UnsupportedOperationException("SShapeUtil cannot be instantiated");
    }

    /**
     * @param fillColor
     * @param radius
     * @return
     */
    public static GradientDrawable getRectShape(int fillColor, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        return shape;
    }

    /**
     * @param fillColor
     * @param radius
     * @param strokeColor
     * @param strokeWidth
     * @return
     */
    public static GradientDrawable getRectShape(int fillColor, int radius, int strokeColor, int strokeWidth) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }

    /**
     * @param fillColor
     * @param radii
     * @param strokeColor
     * @param strokeWidth
     * @return
     */
    public static GradientDrawable getRectShape(int fillColor, float[] radii, int strokeColor, int strokeWidth) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadii(radii);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }
}
