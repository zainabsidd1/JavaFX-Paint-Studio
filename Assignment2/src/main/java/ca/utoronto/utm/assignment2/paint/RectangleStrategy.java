package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RectangleStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel; // so we can ask for a repaint during preview
    private Rectangle rectangle;

    public RectangleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() { return "Rectangle"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        rectangle = new Rectangle(p, p);  // zero-sized start
        panel.requestRender();            // repaint to show preview immediately
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (rectangle != null) {
            rectangle.setP2(new Point(e.getX(), e.getY()));
            panel.requestRender();        // live preview without mutating the model
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (rectangle != null) {
            rectangle.setP2(new Point(e.getX(), e.getY()));
            model.addRectangle(rectangle); // commit to model
            rectangle = null;              // clear preview state
            // Model change will trigger observers -> panel will render
        }
    }

    @Override public void onMouseMoved(MouseEvent e) { /* no-op */ }
    @Override public void onMouseClicked(MouseEvent e) { /* no-op */ }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (rectangle == null) return;

        double x = rectangle.getLeft();
        double y = rectangle.getTop();
        double w = rectangle.getWidth();
        double h = rectangle.getHeight();

        g.setFill(Color.PINK);
        g.fillRect(x, y, w, h);
        g.setStroke(Color.PINK);
        g.setLineWidth(2);
        g.strokeRect(x, y, w, h);
    }
}
