package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;

public interface Shape extends Colorable {
    void draw(GraphicsContext g);

    default void translate(double dx, double dy) {
        System.out.println("This is not hittable.");
    }

    Shape copy();

}

