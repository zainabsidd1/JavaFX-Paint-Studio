package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private Point p1;
    private Point p2;
    private Color color;

    public Rectangle(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public double getLeft() { return Math.min(p1.x, p2.x); }
    public double getTop() { return Math.min(p1.y, p2.y); }
    public double getWidth() { return Math.abs(p2.x - p1.x); }
    public double getHeight() { return Math.abs(p2.y - p1.y); }

    @Override
    public Color getColor() { return color; }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }

    @Override
    public void draw(GraphicsContext g) {
        double x = Math.min(p1.x, p2.x);
        double y = Math.min(p1.y, p2.y);
        double w = Math.abs(p2.x - p1.x);
        double h = Math.abs(p2.y - p1.y);

        g.setFill(color);
        g.setStroke(color);
        g.setLineWidth(2);
        g.fillRect(x, y, w, h);
        g.strokeRect(x, y, w, h);
    }
}
