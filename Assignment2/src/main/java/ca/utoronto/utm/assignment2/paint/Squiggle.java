package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Squiggle implements Shape, Colorable, Strokeable {
    private final List<Point> points = new ArrayList<>();
    private Color color = Color.RED;
    private double strokeWidth = 2.0;

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

    @Override
    public Squiggle copy() { return new Squiggle(); }

    @Override
    public double getStrokeWidth() { return strokeWidth; }
    @Override
    public void setStrokeWidth(double w) { this.strokeWidth = clampStroke(w); }
}
