package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TriangleStrategy implements ToolStrategy, Colorable {
    private final PaintModel model;
    private final PaintPanel panel;
    private Triangle triangle;
    private Point hoverPoint;
    private Color fallback = Color.DARKRED;

    public TriangleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override public String getName() { return "Triangle"; }

    private Color chosenOrFallback() {
        return model.hasUserColor() ? model.getCurrentColor() : fallback;
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());

        if (triangle == null) {
            triangle = new Triangle();
            Color chosen = chosenOrFallback();
            triangle.setColor(chosen);
            triangle.setFillColor(chosen);
            triangle.setFilled(model.isFilled());
        }

        triangle.addVertex(p);

        if (triangle.isComplete()) {
            triangle.setFilled(model.isFilled());
            Color chosen = chosenOrFallback();
            triangle.setColor(chosen);
            triangle.setFillColor(chosen);
            model.addShape(triangle);
            triangle = null;
            hoverPoint = null;
        }

        panel.requestRender();
    }

    @Override public void onMousePressed(MouseEvent e) { /* no-op */ }
    @Override public void onMouseDragged(MouseEvent e) { /* no-op */ }
    @Override public void onMouseReleased(MouseEvent e) { /* no-op */ }

    @Override
    public void onMouseMoved(MouseEvent e) {
        if (triangle != null && !triangle.isEmpty()) {
            hoverPoint = new Point(e.getX(), e.getY());
            panel.requestRender();
        }
    }

    @Override public Color getColor() { return fallback; }
    @Override public void setColor(Color c) { if (c != null) this.fallback = c; }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (triangle == null || triangle.isEmpty()) return;

        Color preview = chosenOrFallback();
        g.setStroke(preview);
        g.setLineWidth(1.5);

        var vertices = triangle.getVertices();
        for (int i = 0; i < vertices.size() - 1; i++) {
            Point a = vertices.get(i), b = vertices.get(i + 1);
            g.strokeLine(a.x, a.y, b.x, b.y);
        }
        for (Point v : vertices) {
            g.setFill(preview);
            g.fillOval(v.x - 3, v.y - 3, 6, 6);
            g.setStroke(preview);
            g.strokeOval(v.x - 3, v.y - 3, 6, 6);
        }
        if (hoverPoint != null && vertices.size() < 3) {
            Point last = vertices.get(vertices.size() - 1);
            g.strokeLine(last.x, last.y, hoverPoint.x, hoverPoint.y);
            if (vertices.size() == 1) {
                Point first = vertices.get(0);
                g.strokeLine(first.x, first.y, hoverPoint.x, hoverPoint.y);
            }
        }
    }
}
