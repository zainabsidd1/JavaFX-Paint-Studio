package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape, Fillable, Hittable {
    private Point p1;
    private Point p2;
    private Color color;
    private Color fillColor;
    private boolean filled = true;
    private boolean selected = false;

    public Rectangle(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.fillColor = color;
    }

    public void setP1(Point p1) { this.p1 = p1; }
    public void setP2(Point p2) { this.p2 = p2; }

    public double getLeft()   { return Math.min(p1.x, p2.x); }
    public double getTop()    { return Math.min(p1.y, p2.y); }
    public double getWidth()  { return Math.abs(p2.x - p1.x); }
    public double getHeight() { return Math.abs(p2.y - p1.y); }

    public void setFilled(boolean filled) { this.filled = filled; }
    public boolean getFilled() { return this.filled; }

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
        double right = left + getWidth();
        double bottom = top + getHeight();
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    @Override
    public Color getColor() { return color; }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }

    @Override
    public void applyFill(Color c){
        if(c==null) return;
        setColor(c);
        setFillColor(c);
        this.setFilled(true);
    }

    @Override
    public void draw(GraphicsContext g) {
        double x = getLeft();
        double y = getTop();
        double w = getWidth();
        double h = getHeight();
        g.setStroke(getColor());
        g.setLineWidth(2);
        if (filled) {
            g.setFill(fillColor != null ? fillColor : color);
            g.fillRect(x, y, w, h);
        }
        g.strokeRect(x, y, w, h);
    }

    @Override
    public void translate(double dx, double dy) {
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;
    }

    public Rectangle(Rectangle other) {
        this.p1 = new Point(other.p1.x, other.p1.y);
        this.p2 = new Point(other.p2.x, other.p2.y);
        this.color = other.color;
        this.fillColor = other.fillColor;
        this.filled = other.filled;
    }

    @Override
    public Rectangle copy() {
        return new Rectangle(this);
    }
}
