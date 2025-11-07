package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;

public interface Shape extends Colorable {

    default void translate(double dx, double dy) {
        System.out.println("This is not hittable.");
    }

    void accept(ShapeVisitor visitor); // visitor design pattern

    Shape copy();
}

