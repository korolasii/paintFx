package com.demo1.demo1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
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

    public void colorButtonClicked(ActionEvent event) {
        if (event.getSource() instanceof MenuItem) {
            MenuItem clickedMenuItem = (MenuItem) event.getSource();
            String menuItemText = clickedMenuItem.getText();
            color = Color.valueOf(menuItemText);
        }
    }

    public void widthButtonClicked(ActionEvent event) {
        if (event.getSource() instanceof MenuItem) {
            MenuItem clickedMenuItem = (MenuItem) event.getSource();
            String menuItemText = clickedMenuItem.getText();
            width = Integer.parseInt(menuItemText.substring(0, menuItemText.indexOf("p")));
        }
    }
}