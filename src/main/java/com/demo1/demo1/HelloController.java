package com.demo1.demo1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
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
    public Canvas canvas;

    public GraphicsContext gc;

    public DrawingData drawingData;

    public Color color = Color.BLACK;

    public int width = 12;

    public Canvas getCanvas() {
        return canvas;
    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(width);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);

        loadDrawingData();
        redraw();
    }

    public void clearCanvas() {


        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        drawingData.clearPoints();
        saveDrawingData();
    }

    public void clearButtonClicked(ActionEvent actionEvent) {
        clearCanvas();
    }

    private void handleMousePressed(MouseEvent event) {
        gc.setStroke(color);
        gc.beginPath();
        gc.setLineWidth(width);
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();

        drawingData.addPoint(event.getX(), event.getY(), color);
        saveDrawingData();
    }

    private void handleMouseDragged(MouseEvent event) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();


        drawingData.addPoint(event.getX(), event.getY(), color);
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
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (PointData point : drawingData.getPoints()) {
            gc.setStroke(point.getColor());
            gc.setLineWidth(width);
            gc.beginPath();
            gc.lineTo(point.getX(), point.getY());
            gc.stroke();
        }
    }

    public void eraserButtonClicked(ActionEvent event) {
        color = Color.WHITE;
    }

    public void blackButtonClicked(ActionEvent event) {
        color = Color.BLACK;
    }

    public void yelllowButtonClicked(ActionEvent event) {
        color = Color.YELLOW;
    }

    public void greenButtonClicked(ActionEvent event) {
        color = Color.GREEN;
    }

    public void blueButtonClicked(ActionEvent event) {
        color = Color.BLUE;
    }

    public void tenButtonClicked(ActionEvent event) {
        width = 10;
    }

    public void twelveButtonClicked(ActionEvent event) {
        width = 12;
    }

    public void fourteenButtonClicked(ActionEvent event) {
        width = 14;
    }

    public void sixteenButtonClicked(ActionEvent event) {
        width = 16;
    }
}