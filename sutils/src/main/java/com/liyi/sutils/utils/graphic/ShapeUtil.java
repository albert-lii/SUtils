package com.liyi.sutils.utils.graphic;

import android.graphics.drawable.GradientDrawable;


/**
 * Shape 工具类
 */
public final class ShapeUtil {

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor 图形填充色
     * @param radius    图形圆角半径
     * @return 圆角矩形
     */
    public static GradientDrawable drawRoundRect(int fillColor, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        return shape;
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radius      图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    public static GradientDrawable drawRoundRect(int fillColor, int radius, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadius(radius);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radii       图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    public static GradientDrawable drawRoundRect(int fillColor, float[] radii, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(fillColor);
        shape.setCornerRadii(radii);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }

    /**
     * 绘制圆形
     *
     * @param fillColor   图形填充色
     * @param size        图形的大小
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆形
     */
    public static GradientDrawable drawCircle(int fillColor, int size, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setSize(size, size);
        shape.setColor(fillColor);
        shape.setStroke(strokeWidth, strokeColor);
        return shape;
    }
}
