package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface ToolStrategy {
    String getName();
    void onMousePressed(MouseEvent e);
    void onMouseDragged(MouseEvent e);
    void onMouseReleased(MouseEvent e);

    default void drawPreview(GraphicsContext g) {}

    void onMouseMoved(MouseEvent e);

    void onMouseClicked(MouseEvent e);
    default Cursor getCursor() { return Cursor.DEFAULT; }
}

