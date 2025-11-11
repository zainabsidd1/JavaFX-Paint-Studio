package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Polyline implements Shape, Colorable, Strokeable, Hittable {
    private final List<Point> pathPoints = new ArrayList<>();
    private Color color = Color.DEEPPINK; // default; will be overwritten by setColor
    private double strokeWidth = 2.0;

    public Polyline() { }

    public Polyline(Color color) {
        this.color = color;
    }

    public void addPoint(Point p) {
        pathPoints.add(p);
    }

    public List<Point> getPoints() {
        return pathPoints;
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
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Polyline copy() {
        Polyline copy = new Polyline(color);
        copy.setStrokeWidth(this.strokeWidth);
        for (Point p : this.pathPoints) {
            copy.addPoint(new Point(p.x, p.y));
        }
        return copy;
    }

    @Override
    public double getStrokeWidth() { return strokeWidth; }

    @Override
    public void setStrokeWidth(double w) { this.strokeWidth = clampStroke(w); }

    @Override
    public boolean contains(double x, double y) {
        double margin = 4.0;

        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point p1 = pathPoints.get(i);
            Point p2 = pathPoints.get(i + 1);
            double minX = Math.min(p1.x, p2.x) - margin;
            double maxX = Math.max(p1.x, p2.x) + margin;
            double minY = Math.min(p1.y, p2.y) - margin;
            double maxY = Math.max(p1.y, p2.y) + margin;

            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void translate(double dx, double dy) {
        for (Point p : pathPoints) {
            p.x += dx;
            p.y += dy;
        }
    }
}
