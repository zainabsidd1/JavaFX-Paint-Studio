package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class TriangleStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private Triangle triangle;

    public TriangleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }
    @Override
    public void onMouseClicked(MouseEvent e){
        Point p = new Point(e.getX(), e.getY());
        if (triangle == null){
            triangle = new Triangle();
        }
        triangle.addVertex(p);

        if (triangle.isComplete()) {
            model.addToShape(triangle);
            triangle = null;
        }
        panel.requestRender();

    }


    @Override
    public String getName() { return "Triangle"; }

    @Override
    public void onMousePressed(MouseEvent e){ /* no-op */}

    @Override
    public void onMouseDragged(MouseEvent e){ /* no-op */}

    @Override
    public void onMouseReleased(MouseEvent e){ /* no-op */}

    @Override
    public void onMouseMoved(MouseEvent e){ /* no-op */}
}
