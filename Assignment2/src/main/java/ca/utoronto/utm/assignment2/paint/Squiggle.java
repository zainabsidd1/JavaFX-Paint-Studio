package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Squiggle implements Shape, Colorable, Strokeable, Hittable {
    private final List<Point> points = new ArrayList<>();
    private boolean isEraser = false;
    private Color color = Color.RED;
    private double strokeWidth = 2.0;

    public boolean isEraser() {
        return isEraser;
    }

    public void setEraser(boolean isEraser) {
        this.isEraser = isEraser;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public void setColor(Color c) { this.color = c; }

    @Override
    public Color getColor() { return color; }

    public Squiggle copy() {
        Squiggle copy = new Squiggle();
        copy.setStrokeWidth(this.strokeWidth);
        for (Point p : this.points) {
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
        if (points.size() < 2) return false;
        double tolerance = 5.0;
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            if (distanceFromSegment(p1, p2, x, y) <= tolerance)
                return true;
        }
        return false;
    }

    private double distanceFromSegment(Point p1, Point p2, double x, double y) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double len2 = dx * dx + dy * dy;
        if (len2 == 0) return Math.hypot(x - p1.x, y - p1.y);
        double t = ((x - p1.x) * dx + (y - p1.y) * dy) / len2;
        t = Math.max(0, Math.min(1, t));
        double projX = p1.x + t * dx;
        double projY = p1.y + t * dy;
        return Math.hypot(x - projX, y - projY);
    }

    @Override
    public void translate(double dx, double dy) {
        for (Point p : points) {
            p.x += dx;
            p.y += dy;
        }
    }
}
