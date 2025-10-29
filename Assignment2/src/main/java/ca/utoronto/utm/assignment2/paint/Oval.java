package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval implements Shape {
    private Point p1, p2;
    private final Color color;
    private boolean filled;

    public Oval(Point center, double radius, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.filled = filled;
    }

    public void setPoint1(Point p1) {this.p1 = p1;}
    public void setPoint2(Point p2) {this.p2 = p2;}

    public double getLeft() { return Math.min(p1.x, p2.x); }
    public double getTop() { return Math.min(p2.x, p2.y); }
    public double getWidth() { return Math.abs(p2.x - p1.x); }
    public double getHeight() { return Math.abs(p2.y - p1.y); }


    @Override
    public void draw(GraphicsContext g) {
        double left = Math.min(p1.x, p2.x);
        double top = Math.min(p1.y, p2.y);
        double width = Math.abs(p2.x - p1.x);
        double height = Math.abs(p2.y - p1.y);

        g.setStroke(color);
        g.setFill(color);

        if (filled) {
            g.fillOval(left, top, width, height);
        }
        else{
            g.strokeOval(left, top, width, height);
        }

    }
}
