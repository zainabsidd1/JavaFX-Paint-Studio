package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class SquiggleStrategy implements ToolStrategy, Colorable {
    private final PaintModel model;
    private final PaintPanel panel;
    private ArrayList<Point> squiggle;
    private Color color = Color.RED;

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
        model.startNewSquiggle();
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
    @Override public void onMouseClicked(MouseEvent e) {}

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (squiggle == null || squiggle.size() < 2) {
            return;
        }
        Color chosen = (model.getCurrentColor() != null && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        EraserStrategy.colorSetBackground(g, chosen, squiggle);
    }
}

