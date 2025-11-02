package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape, Fillable, Hittable {
    private Point p1;
    private Point p2;
    private Color strokeColor;
    private Color fillColor;

    public Square(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.strokeColor = color;
        this.fillColor = color; 
    }

    public Point getP1() { return this.p1; }
    public Point getP2() { return this.p2; }

    public void setP1(Point p1) { this.p1 = p1; }
    public void setP2(Point p2) { this.p2 = p2; }

    public double getLeft()   { return Math.min(p1.x, p2.x); }
    public double getTop()    { return Math.min(p1.y, p2.y); }
    public double getLength() {
        double dx = Math.abs(p2.x - p1.x);
        double dy = Math.abs(p2.y - p1.y);
        return Math.min(dx, dy);
    }

    @Override
    public void setFillColor(Color c) {
        if (c != null) this.fillColor = c;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public boolean contains(double x, double y) {
        double left = getLeft();
        double top = getTop();
        double len = getLength();
        return x >= left && x <= left + len && y >= top && y <= top + len;
    }

    @Override
    public Color getColor() { return strokeColor; }

    @Override
    public void setColor(Color c) {
        if (c != null) this.strokeColor = c;
    }

    @Override
    public void draw(GraphicsContext g) {
        double x = getLeft();
        double y = getTop();
        double length = getLength();
        if (fillColor != null && fillColor.getOpacity() > 0) {
            g.setFill(fillColor);
            g.fillRect(x, y, length, length);
        }
        g.setStroke(fillColor);
        g.setLineWidth(2);
        g.strokeRect(x, y, length, length);
    }
}
