package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval implements Shape, Fillable, Hittable {
    private Point p1;
    private Point p2;
    private Color strokeColor;
    private Color fillColor;
    private boolean filled=true;
    private boolean selected = false;

    public Oval(Point p1, Point p2, Color color, boolean filled) {
        this.p1 = p1;
        this.p2 = p2;
        this.strokeColor = color;
        this.fillColor = filled ? color : Color.TRANSPARENT;
        this.filled = filled;
    }



    public void setPoint1(Point p1) { this.p1 = p1; }
    public void setPoint2(Point p2) { this.p2 = p2; }
    public boolean isFilled() { return filled; }

    public double getLeft()   { return Math.min(p1.x, p2.x); }
    public double getTop()    { return Math.min(p1.y, p2.y); }
    public double getWidth()  { return Math.abs(p2.x - p1.x); }
    public double getHeight() { return Math.abs(p2.y - p1.y); }

    @Override
    public void setFillColor(Color c) {
        if (c != null) {
            this.fillColor = c;
            this.filled = true;
        }
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public boolean contains(double x, double y) {
        double rx = getWidth() / 2.0;
        double ry = getHeight() / 2.0;
        if (rx <= 0 || ry <= 0) return false;

        double cx = getLeft() + rx;
        double cy = getTop() + ry;

        double dx = (x - cx) / rx;
        double dy = (y - cy) / ry;
        return dx * dx + dy * dy <= 1.0;
    }

    @Override
    public Color getColor() { return strokeColor; }

    @Override
    public void setColor(Color c) {
        if (c != null) this.strokeColor = c;
    }

    public boolean getFilled() { return filled; }

    public void setFilled(boolean filled) { this.filled = filled; }

    @Override
    public void applyFill(Color c){
        if(c==null) return;
        setColor(c);
        setFillColor(c);
        this.setFilled(true);
    }

    @Override
    public void draw(GraphicsContext g) {
        double left = getLeft();
        double top = getTop();
        double width = getWidth();
        double height = getHeight();

        g.setStroke(fillColor);
        g.setLineWidth(2);

        if (filled && fillColor != null && fillColor.getOpacity() > 0) {
            g.setFill(fillColor);
            g.fillOval(left, top, width, height);
        }

        g.strokeOval(left, top, width, height);
    }

    @Override
    public void translate(double dx, double dy) {
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;
    }

    public Oval(Oval other) {
        this.p1 = new Point(other.p1.x, other.p1.y);
        this.p2 = new Point(other.p2.x, other.p2.y);
        this.strokeColor = other.strokeColor;
        this.fillColor = other.fillColor;
        this.filled = other.filled;
    }

    @Override
    public Oval copy(){
       return new Oval(this);
    }


}
