package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class SquiggleStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private ArrayList<Point> squiggle; // transient points for preview

    public SquiggleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Squiggle";
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        model.startNewSqiuggle();                     // begin a new squiggle in model
        model.addPoint(new Point(e.getX(), e.getY()));
        squiggle = new ArrayList<>();
        squiggle.add(new Point(e.getX(), e.getY()));
        panel.requestRender();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        model.addPoint(new Point(e.getX(), e.getY()));
        if (squiggle != null) {
            squiggle.add(new Point(e.getX(), e.getY()));
        }
        panel.requestRender();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        // model already holds the final squiggle
        squiggle = null;
        panel.requestRender();
    }

    @Override public void onMouseMoved(MouseEvent e) { /* no-op */ }
    @Override public void onMouseClicked(MouseEvent e) { /* no-op */ }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (squiggle == null || squiggle.size() < 2) {
            return;
        }
        g.setStroke(Color.RED);
        g.setLineDashes(0);
        g.setGlobalAlpha(1.0);
        for (int i=0; i<squiggle.size()-1; i++) {
            Point p1 = squiggle.get(i);
            Point p2 = squiggle.get(i+1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}

