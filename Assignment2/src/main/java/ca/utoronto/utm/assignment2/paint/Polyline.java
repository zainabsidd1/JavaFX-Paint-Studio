package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Polyline implements Shape, Colorable {
    private final List<Point> pathPoints = new ArrayList<>();
    private Color color = Color.DEEPPINK; // default; will be overwritten by setColor

    public Polyline() { }

    public Polyline(Color color) {
        this.color = color;
    }

    public void addPoint(Point p) {
        pathPoints.add(p);
    }

    @Override
    public void draw(GraphicsContext g) {
        if (pathPoints.size() < 2) return;
        g.setStroke(color);
        g.setLineWidth(2);
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point p1 = pathPoints.get(i);
            Point p2 = pathPoints.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
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
    public Polyline copy() {return new Polyline(color);}
}
