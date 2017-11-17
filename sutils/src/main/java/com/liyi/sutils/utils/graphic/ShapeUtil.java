package com.liyi.sutils.utils.graphic;

import android.graphics.drawable.GradientDrawable;


/**
 * Shape工具类
 */
public class ShapeUtil {

    /**
     * 动态绘制圆角矩形drawable
     *
     * @param fillColor 图形填充色
     * @param radius    图形圆角半径
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
     * 动态绘制圆角矩形drawable
     *
     * @param fillColor   图形填充色
     * @param radius      图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return
     */
    public static GradientDrawable getRectShape(int fillColor, int radius, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }

    /**
     * 动态绘制圆角矩形drawable
     *
     * @param fillColor   图形填充色
     * @param radii       图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return
     */
    public static GradientDrawable getRectShape(int fillColor, float[] radii, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadii(radii);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }
}
