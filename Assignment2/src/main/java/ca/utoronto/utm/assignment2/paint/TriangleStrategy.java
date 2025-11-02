package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.SourceLocator;
import java.util.ArrayList;

public class TriangleStrategy implements ToolStrategy, Colorable{
    private final PaintModel model;
    private final PaintPanel panel;
    private Triangle triangle;
    private Point hoverPoint;
    private Color color = Color.DARKRED;

    public TriangleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }
    @Override
    public void onMouseClicked(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());

        if (triangle == null) {
            triangle = new Triangle();
        }

        triangle.addVertex(p);

        if (triangle.isComplete()) {
            Color chosen = (model.getCurrentColor() != null &&
                    !model.getCurrentColor().equals(Color.BLACK))
                    ? model.getCurrentColor()
                    : color;

            triangle.setColor(chosen);
            model.addToShape(triangle);
            triangle = null;
            hoverPoint = null;
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
    public void onMouseMoved(MouseEvent e){
        if (triangle != null && !triangle.isEmpty()) {
            hoverPoint = new Point(e.getX(), e.getY());
            panel.requestRender();
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (triangle == null || triangle.isEmpty()) return;
        Color previewColour = (model.getCurrentColor() != null &&
                !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        g.setStroke(previewColour);
        g.setLineWidth(1.5);

        var vertices = triangle.getVertices();

        for (int i = 0; i < vertices.size()-1; i++) {
            Point p = vertices.get(i);
            Point q = vertices.get(i+1);
            g.strokeLine(p.x, p.y, q.x, q.y);
        }

        for (Point v : vertices) {
            triangleVertices(g, v);
        }

        if (hoverPoint != null && vertices.size() < 3) {
            Point last = vertices.get(vertices.size()-1);
            g.strokeLine(last.x, last.y, hoverPoint.x, hoverPoint.y);
            if (hoverPoint != null && vertices.size() == 1) {
                Point first = vertices.get(0);
                g.strokeLine(first.x, first.y, hoverPoint.x, hoverPoint.y);
            }
        }


    }

    private void triangleVertices(GraphicsContext g, Point v) {
        Color chosen = (model.getCurrentColor() != null
                && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        g.setFill(chosen);
        g.fillOval(v.x - 3.0, v.y - 3.0, 2 * 3.0, 2 * 3.0);
        g.setStroke(chosen);
        g.strokeOval(v.x - 3.0, v.y - 3.0, 2 * 3.0, 2 * 3.0);
        g.setStroke(chosen);
    }
}
