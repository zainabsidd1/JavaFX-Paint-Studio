package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Polyline implements Shape{
    private final List<Point> pathPoints = new ArrayList<>();
    private final Color color = Color.PURPLE;
    public void addPoint(Point p) {
        pathPoints.add(p);
    }

    @Override
    public void draw(GraphicsContext g) {
        if (pathPoints.size() < 2) { return; }
        g.setStroke(color);
        g.setLineWidth(2);
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point p1 = pathPoints.get(i);
            Point p2 = pathPoints.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
