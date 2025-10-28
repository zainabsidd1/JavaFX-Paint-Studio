package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Squiggle implements Shape {
    private final List<Point> points = new ArrayList<>();
    private final Color color = Color.RED;

    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public void draw(GraphicsContext g) {
        if (points.size() < 2) return;
        g.setStroke(color);
        g.setLineWidth(2);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
