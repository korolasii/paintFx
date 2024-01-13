package com.demo1.demo1;

import javafx.scene.paint.Color;

public class PointData {
    public double x;
    public double y;
    public double red;
    public double green;
    public double blue;

    public PointData(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return Color.color(red, green, blue);
    }
}