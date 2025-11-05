package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape, Fillable, Hittable {
    private Point p1;
    private Point p2;
    private Color strokeColor;
    private Color fillColor;
    private boolean filled=true;

    public Square(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.strokeColor = color;
        this.fillColor = color; 
    }
    public void setFilled(boolean filled) { this.filled = filled; }
    public boolean getFilled() { return this.filled; }

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
        double length = getLength();

        g.setStroke(fillColor);
        g.setLineWidth(2);
        if (filled && fillColor != null && fillColor.getOpacity() > 0) {
            g.setFill(fillColor);
            g.fillRect(x, y, length, length);
        }
        g.strokeRect(x, y, length, length);
    }

    public Square(Square other) {
        this.p1 = new Point(other.p1.x, other.p1.y);
        this.p2 = new Point(other.p2.x, other.p2.y);
        this.strokeColor = other.strokeColor;
        this.fillColor = other.fillColor;
        this.filled = other.filled;
    }

    @Override
    public Square copy(){
        Square s = new Square(this);
        s.p1 = new Point(this.p1.x + 10, this.p1.y + 10);
        s.p2 = new Point(this.p2.x + 10, this.p2.y + 10);
        return s;
    }
}
