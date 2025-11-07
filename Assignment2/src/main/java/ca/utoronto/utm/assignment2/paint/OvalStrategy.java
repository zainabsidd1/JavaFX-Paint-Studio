package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class OvalStrategy implements ToolStrategy, Colorable {
    private final PaintModel model;
    private final PaintPanel panel;
    private Oval oval;
    private Color fallback = Color.LIGHTSEAGREEN;

    public OvalStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override public String getName() { return "Oval"; }

    private Color chosenOrFallback() {
        return model.hasUserColor() ? model.getCurrentColor() : fallback;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        Color chosen = chosenOrFallback();
        oval = new Oval(p, p, chosen, true);
        oval.setColor(chosen);
        oval.setFillColor(chosen);
        oval.setStrokeWidth(model.getStrokeWidth());
        panel.requestRender();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (oval != null) {
            oval.setPoint2(new Point(e.getX(), e.getY()));
            panel.requestRender();
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (oval != null) {
            oval.setPoint2(new Point(e.getX(), e.getY()));
            Color finalC = chosenOrFallback();
            oval.setColor(finalC);
            oval.setFillColor(finalC);
            oval.setFilled(model.isFilled());

            model.addShape(oval);
            oval = null;
        }
    }

    @Override public Color getColor() { return fallback; }
    @Override public void setColor(Color c) { if (c != null) this.fallback = c; }

    @Override
    public void drawPreview(GraphicsContext g) {
        if (oval == null) return;

        double x = oval.getLeft();
        double y = oval.getTop();
        double w = oval.getWidth();
        double h = oval.getHeight();

        Color preview = chosenOrFallback();

        g.setLineWidth(model.getStrokeWidth());
        if(model.isFilled()){
            g.setFill(preview);
            g.fillOval(x, y, w, h);
        }
        g.setStroke(preview);
        g.strokeOval(x, y, w, h);
    }

    @Override public void onMouseMoved(MouseEvent e) {}
    @Override public void onMouseClicked(MouseEvent e) {
        Shape clicked = model.findTopmostAt(e.getX(), e.getY());
        model.setSelectedShape(clicked);
    }
}
