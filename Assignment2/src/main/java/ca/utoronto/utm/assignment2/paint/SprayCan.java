package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class SprayCan {
    private final List<Point2D> points = new ArrayList<>();
    private Color color;
    private double strokeWidth;
    private double opacity;

    public SprayCan(Color color, double stroke, double opacity) {
        this.color = color;
        this.strokeWidth = stroke;
        this.opacity = opacity;
    }

    public void addPoint(double x, double y) {points.add(new Point2D(x, y));}

    public List<Point2D> getPoints() {return points;}

    public Color getColor() {return color;}

    public double getStrokeWidth() {return strokeWidth;}

    public double getOpacity() {return opacity;}

}
