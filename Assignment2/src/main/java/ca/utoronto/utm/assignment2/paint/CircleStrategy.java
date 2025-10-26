package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CircleStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel; // used for preview repaint
    private Circle circle;

    public CircleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override public String getName() { return "Circle"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point centre = new Point(e.getX(), e.getY());
        circle = new Circle(centre, 0);
        panel.requestRender(); // show zero-radius preview immediately
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (circle != null) {
            double dx = e.getX() - circle.getCentre().x;
            double dy = e.getY() - circle.getCentre().y;
            circle.setRadius(Math.sqrt(dx*dx + dy*dy));
            panel.requestRender(); // live preview
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (circle != null) {
            double dx = e.getX() - circle.getCentre().x;
            double dy = e.getY() - circle.getCentre().y;
            circle.setRadius(Math.sqrt(dx*dx + dy*dy));
            model.addCircle(circle);
            circle = null;            // clear preview
        }
    }

    @Override public void onMouseMoved(MouseEvent e) { }
    @Override public void onMouseClicked(MouseEvent e) { }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (circle == null) return;
        double x = circle.getCentre().x, y = circle.getCentre().y, r = circle.getRadius();
        g.setFill(Color.LIGHTBLUE);
        g.fillOval(x - r, y - r, r * 2, r * 2);
        g.setStroke(Color.LIGHTBLUE);
        g.setLineWidth(2);
        g.strokeOval(x - r, y - r, r * 2, r * 2);
    }
}

