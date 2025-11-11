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

    //@Override
    //public Polyline copy() {return new Polyline(color);}
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
        if (pathPoints.size() < 2) return false;

        double tolerance = 5.0; // click distance tolerance
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point p1 = pathPoints.get(i);
            Point p2 = pathPoints.get(i + 1);
            if (distanceFromSegment(p1, p2, x, y) <= tolerance) {
                return true;
            }
        }
        return false;
    }

    private double distanceFromSegment(Point p1, Point p2, double x, double y) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double lengthSquared = dx * dx + dy * dy;
        if (lengthSquared == 0) {
            // degenerate segment (single point)
            return Math.hypot(x - p1.x, y - p1.y);
        }
        double t = ((x - p1.x) * dx + (y - p1.y) * dy) / lengthSquared;
        t = Math.max(0, Math.min(1, t));
        double projX = p1.x + t * dx;
        double projY = p1.y + t * dy;
        return Math.hypot(x - projX, y - projY);
    }

    @Override
    public void translate(double dx, double dy) {
        for (Point p : pathPoints) {
            p.x += dx;
            p.y += dy;
        }
    }
}
