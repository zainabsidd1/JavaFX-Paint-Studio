package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Triangle implements Shape, Fillable, Hittable, Strokeable {
    private final ArrayList<Point> vertices = new ArrayList<>();
    private Color strokeColor = Color.DARKRED;
    private Color fillColor = Color.DARKRED;
    private double[] xvertices, yvertices;
    private boolean filled = true;
    private double strokeWidth = 2.0;

    public void addVertex(Point p) {
        if (vertices.size() < 3) {
            vertices.add(p);
        }
        if (vertices.size() == 3) {
            xvertices = new double[3];
            yvertices = new double[3];
            for (int i = 0; i < 3; i++) {
                xvertices[i] = vertices.get(i).x;
                yvertices[i] = vertices.get(i).y;
            }
        }
    }

    @Override
    public void setFillColor(Color c) {
        if (c != null) this.fillColor = c;
    }

    @Override
    public Color getFillColor() { return fillColor; }

    public void setFilled(boolean filled) { this.filled = filled; }
    public boolean getFilled() { return this.filled; }

    @Override
    public void applyFill(Color c){
        if(c==null) return;
        setColor(c);
        setFillColor(c);
        this.setFilled(true);
    }

    @Override
    public boolean contains(double x, double y) {
        if (vertices.size() < 3) return false;

        double x1 = xvertices[0], y1 = yvertices[0];
        double x2 = xvertices[1], y2 = yvertices[1];
        double x3 = xvertices[2], y3 = yvertices[2];

        double denom = ((y2 - y3) * (x1 - x3)) + ((x3 - x2) * (y1 - y3));
        if (denom == 0) return false;
        double a = (((y2 - y3) * (x - x3)) + ((x3 - x2) * (y - y3)))/denom;
        double b = (((y3 - y1) * (x - x3)) + ((x1 - x3) * (y - y3)))/denom;
        double c = 1 - a - b;

        return a >= 0 && b >= 0 && c >= 0;
    }

    @Override
    public Color getColor() { return strokeColor; }

    @Override
    public void setColor(Color c) { if (c != null) this.strokeColor = c; }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isComplete() {
        return vertices.size() == 3;
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public ArrayList<Point> getVertices() {
        return vertices;
    }

    @Override
    public void translate(double dx, double dy) {
        for (Point p : vertices) {
            p.setX(p.getX() + dx);
            p.setY(p.getY() + dy);
        }

        for (int i = 0; i < 3; i++) {
            xvertices[i] = vertices.get(i).getX();
            yvertices[i] = vertices.get(i).getY();
        }
    }

    @Override
    public Triangle copy(){
        Triangle t = new Triangle();
        t.setColor(this.strokeColor);
        t.setFillColor(this.fillColor);
        t.setFilled(this.filled);

        if (this.vertices.size() == 3) {
            for (Point v : this.vertices) {
                t.addVertex(new Point(v.x + 10, v.y + 10)); // small offset
            }
        }
        return t;
    }
    @Override
    public double getStrokeWidth() { return strokeWidth; }
    @Override
    public void setStrokeWidth(double w) { this.strokeWidth = clampStroke(w); }
}

