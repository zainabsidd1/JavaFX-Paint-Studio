package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PolylineStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private ArrayList<Point> polyline;

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
            panel.requestRender();
            return;
        }

        if (polyline == null){
            model.startNewPolyline();
            polyline = new ArrayList<>();
        } // start a new polyline in model
        model.addPolylinePoint(new Point(e.getX(), e.getY()));
        polyline.add(new Point(e.getX(), e.getY()));
        panel.requestRender();
    };

    @Override
    public void onMousePressed(MouseEvent e){ /* no-op */ }

    @Override
    public void onMouseDragged(MouseEvent e){ /* no-op */ };

    @Override
    public void onMouseReleased(MouseEvent e){ /* no-op */ };

    @Override
    public void onMouseMoved(MouseEvent e){ /* no-op */ };

}
