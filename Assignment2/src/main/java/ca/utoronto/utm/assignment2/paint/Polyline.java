package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Polyline implements Shape, Colorable, Strokeable {
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
    public Polyline copy() {return new Polyline(color);}

    @Override
    public double getStrokeWidth() { return strokeWidth; }
    @Override
    public void setStrokeWidth(double w) { this.strokeWidth = clampStroke(w); }
}
