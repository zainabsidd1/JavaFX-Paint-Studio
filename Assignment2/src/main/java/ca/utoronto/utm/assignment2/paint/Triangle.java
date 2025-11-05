package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Triangle implements Shape, Fillable, Hittable {
    private final ArrayList<Point> vertices = new ArrayList<>();
    private Color strokeColor = Color.DARKRED;
    private Color fillColor   = Color.DARKRED;
    private double[] xvertices, yvertices;
    private boolean filled=true;

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
    public void draw(GraphicsContext g) {
        if (vertices.size() < 3) return;

        if (filled && fillColor != null && fillColor.getOpacity() > 0) {
            g.setFill(fillColor);
            g.fillPolygon(xvertices, yvertices, 3);
        }

        g.setStroke(fillColor);
        g.setLineWidth(2);
        g.strokePolygon(xvertices, yvertices, 3);
    }

    @Override
    public Triangle copy(){
        Triangle clone = new Triangle();
        clone.setColor(this.strokeColor);
        clone.setFillColor(this.fillColor);
        clone.setFilled(this.filled);

        // If it’s a complete triangle, copy all 3 vertices
        if (this.vertices.size() == 3) {
            for (Point v : this.vertices) {
                clone.addVertex(new Point(v.x + 10, v.y + 10)); // small offset
            }
        }
        return clone;
    }

    public boolean isComplete() { return vertices.size() == 3; }
    public boolean isEmpty() { return vertices.isEmpty(); }
    public ArrayList<Point> getVertices() { return vertices; }

}
