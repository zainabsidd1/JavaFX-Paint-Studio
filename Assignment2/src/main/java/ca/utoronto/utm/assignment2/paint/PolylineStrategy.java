package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PolylineStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private ArrayList<Point> polyline;
    private Point hoverPoint = null;   // current mouse position for the preview segment

    public PolylineStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName(){ return "Polyline"; }

    @Override
    public void onMouseClicked(MouseEvent e){
        if (e.getClickCount() == 2) {
            polyline = null;
            hoverPoint = null;
            panel.requestRender();
            return;
        }

        if (polyline == null){
            model.startNewPolyline();
            polyline = new ArrayList<>();
        } // start a new polyline in model
        Point p = new Point(e.getX(), e.getY());
        model.addPolylinePoint(p);
        polyline.add(p);
        hoverPoint = p;  // preview starts from the last committed vertex
        panel.requestRender();
    }

    @Override
    public void onMousePressed(MouseEvent e){ /* no-op */ }
    @Override
    public void onMouseReleased(MouseEvent e){ /* no-op */ }

    @Override
    public void onMouseMoved(MouseEvent e){
        if (polyline != null && !polyline.isEmpty()) {
            hoverPoint = new Point(e.getX(), e.getY());
            panel.requestRender();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e){
        if (polyline != null && !polyline.isEmpty()) {
            hoverPoint = new Point(e.getX(), e.getY());
            panel.requestRender(); // repaint to show live segment
        }
    }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (polyline == null || polyline.isEmpty()) return;

        // 1) rubber-band from last vertex to cursor
        if (hoverPoint != null) {
            Point last = polyline.getLast();
            g.setLineDashes(0);
            g.setStroke(Color.DEEPPINK);
            g.setLineWidth(2);
            g.strokeLine(last.x, last.y, hoverPoint.x, hoverPoint.y);
        }

        // 2) small dots at each committed vertex + (optionally) at the hover point
        g.setLineWidth(1);

        for (Point v : polyline) {
            polylineVertices(g, v);
        }

        if (hoverPoint != null) {
            polylineVertices(g, hoverPoint);
        }
    }

    private void polylineVertices(GraphicsContext g, Point v) {
        g.setFill(Color.DEEPPINK);
        g.fillOval(v.x - 3.0, v.y - 3.0, 2 * 3.0, 2 * 3.0);
        g.setStroke(Color.WHITE);
        g.strokeOval(v.x - 3.0, v.y - 3.0, 2 * 3.0, 2 * 3.0);
    }
}
