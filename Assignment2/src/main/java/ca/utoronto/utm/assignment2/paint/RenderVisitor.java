package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class RenderVisitor implements ShapeVisitor {
    private final GraphicsContext g;

    public RenderVisitor(GraphicsContext g) {
        this.g = g;
    }

    @Override
    public void visit(Circle c) {
        Point center = c.getCenter();
        double radius = c.getRadius();
        double x = center.x - radius;
        double y = center.y - radius;
        double size = radius * 2;
        // stroke & fill
        g.setStroke(c.getColor());
        g.setLineWidth(2);
        if (c.getFilled()) { // <-- use getFilled()
            g.setFill(c.getFillColor() != null ? c.getFillColor() : c.getColor());
            g.fillOval(x, y, size, size);
        }
        g.strokeOval(x, y, size, size);
    }

    @Override
    public void visit(Oval o) {
        double left   = o.getLeft();
        double top    = o.getTop();
        double width  = o.getWidth();
        double height = o.getHeight();
        // stroke & line width
        g.setStroke(o.getColor());
        g.setLineWidth(2);
        if (o.getFilled() && o.getFillColor() != null && o.getFillColor().getOpacity() > 0) {
            g.setFill(o.getFillColor());
            g.fillOval(left, top, width, height);
        }
        g.strokeOval(left, top, width, height);
    }

    @Override
    public void visit(Polyline p) {
        List<Point> pts = p.getPoints();
        if (pts == null || pts.size() < 2) return;

        g.setStroke(p.getColor());
        g.setLineWidth(2);

        // draw connected line segments
        for (int i = 0; i < pts.size() - 1; i++) {
            Point p1 = pts.get(i);
            Point p2 = pts.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void visit(Rectangle r) {
        double x = r.getLeft();
        double y = r.getTop();
        double w = r.getWidth();
        double h = r.getHeight();
        g.setStroke(r.getColor());
        g.setLineWidth(2);
        if (r.getFilled() && r.getFillColor() != null) {
            g.setFill(r.getFillColor());
            g.fillRect(x, y, w, h);
        }

        g.strokeRect(x, y, w, h);
    }

    @Override
    public void visit(Square s) {
        double x = s.getLeft();
        double y = s.getTop();
        double len = s.getLength();
        g.setStroke(s.getColor());
        g.setLineWidth(2);
        if (s.getFilled() && s.getFillColor() != null && s.getFillColor().getOpacity() > 0) {
            g.setFill(s.getFillColor());
            g.fillRect(x, y, len, len);
        }
        g.strokeRect(x, y, len, len);
    }

    @Override
    public void visit(Squiggle s) {
        List<Point> pts = s.getPoints();
        if (pts.size() < 2) return;
        g.setStroke(s.getColor());
        g.setLineWidth(2);
        for (int i = 0; i < pts.size() - 1; i++) {
            Point p1 = pts.get(i);
            Point p2 = pts.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void visit(Triangle t) {
        // need 3 points to draw
        var verts = t.getVertices();
        if (verts == null || verts.size() < 3) return;
        double[] xs = new double[3];
        double[] ys = new double[3];
        for (int i = 0; i < 3; i++) {
            xs[i] = verts.get(i).x;
            ys[i] = verts.get(i).y;
        }
        if (t.getFilled() && t.getFillColor() != null && t.getFillColor().getOpacity() > 0) {
            g.setFill(t.getFillColor());
            g.fillPolygon(xs, ys, 3);
        }
        g.setStroke(t.getColor());
        g.setLineWidth(2);
        g.strokePolygon(xs, ys, 3);
    }
}
