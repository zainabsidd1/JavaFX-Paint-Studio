package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Triangle implements Shape{
    private final ArrayList<Point> vertices = new ArrayList<>();
    private Color color = Color.DARKRED;
    private double[] xvertices, yvertices;

    public void addVertex(Point p) {
        if (vertices.size() < 3) { vertices.add(p); }
        if (vertices.size() == 3) { // only compute when list is size 3
            xvertices = new double[3];
            yvertices = new double[3];

            for (int i=0; i<3; i++){
                xvertices[i] = vertices.get(i).x;
                yvertices[i] = vertices.get(i).y;
            }
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        if (vertices.size() < 3) { return; }
        g.setStroke(color);
        g.setLineWidth(2);
        g.setFill(color);
        g.fillPolygon(xvertices, yvertices, 3);
        g.strokePolygon(xvertices, yvertices, 3);
    }

    public boolean isComplete() {
        return vertices.size() == 3;
    }

    public boolean isEmpty() { return vertices.isEmpty(); }

    public ArrayList<Point> getVertices() { return vertices; }

    @Override
    public Color getColor() { return color; }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }
}
