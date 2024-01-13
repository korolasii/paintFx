package com.demo1.demo1;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DrawingData {
    public List<PointData> points;

    public DrawingData() {
        points = new ArrayList<>();
    }

    public List<PointData> getPoints() {
        return points;
    }

    public void addPoint(double x, double y, Color color) {
        points.add(new PointData(x, y, color));
    }

    public void clearPoints() {
        points.clear();
    }
}