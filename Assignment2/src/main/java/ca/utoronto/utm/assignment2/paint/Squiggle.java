package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import static ca.utoronto.utm.assignment2.paint.Polyline.containsPoint;

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
        copy.setColor(this.color);
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
        if (isEraser) {return false;}
        return containsPoint(x, y, points);
    }

    @Override
    public void translate(double dx, double dy) {
        if (isEraser) {return;}
        for (Point p : points) {
            p.x += dx;
            p.y += dy;
        }
    }
}
