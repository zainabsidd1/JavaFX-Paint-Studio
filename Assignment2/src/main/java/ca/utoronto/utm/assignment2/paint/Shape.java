package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Shape extends Colorable {
    void draw(GraphicsContext g);
}

