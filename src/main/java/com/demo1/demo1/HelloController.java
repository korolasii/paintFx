package com.demo1.demo1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HelloController {
    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private DrawingData drawingData;

    public Canvas getCanvas() {
        return canvas;
    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);

        loadDrawingData();
        redraw();
    }

    private void handleMousePressed(MouseEvent event) {
        gc.beginPath();
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();

        drawingData.addPoint(event.getX(), event.getY(), Color.BLACK);
        saveDrawingData();
    }

    private void handleMouseDragged(MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();

        drawingData.addPoint(event.getX(), event.getY(), Color.BLACK);
        saveDrawingData();
    }

    private void saveDrawingData() {
        try (FileWriter writer = new FileWriter("drawingData.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(drawingData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDrawingData() {
        try (FileReader reader = new FileReader("drawingData.json")) {
            Gson gson = new Gson();
            drawingData = gson.fromJson(reader, DrawingData.class);
            if (drawingData == null) {
                drawingData = new DrawingData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            drawingData = new DrawingData();
        }
    }

    private void redraw() {
        for (PointData point : drawingData.getPoints()) {
            gc.beginPath();
            gc.lineTo(point.getX(), point.getY());
            gc.setStroke(point.getColor());
            gc.stroke();
        }
    }
}