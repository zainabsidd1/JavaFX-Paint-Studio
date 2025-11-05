package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class PasteStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel;

    public PasteStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() { return "Paste"; }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Shape copied = model.getStoredShape();
        if (copied == null) return;

        Shape pasted = copied.copy();
        // move to click location
        if (pasted instanceof Rectangle) {
            Rectangle r = (Rectangle) pasted;
            double dx = e.getX() - r.getLeft();
            double dy = e.getY() - r.getTop();
            r.translate(dx, dy);
        }
        else if (pasted instanceof Circle) {
            Circle c = (Circle) pasted;
            double dx = e.getX() - c.getCenter().x;
            double dy = e.getY() - c.getCenter().y;
            c.translate(dx, dy);
        }
        else if (pasted instanceof Oval) {
            Oval o = (Oval) pasted;
            double dx = e.getX() - o.getLeft();
            double dy = e.getY() - o.getTop();
            o.translate(dx, dy);
        }
        else if (pasted instanceof Square) {
            Square s = (Square) pasted;
            double dx = e.getX() - s.getLeft();
            double dy = e.getY() - s.getTop();
            s.translate(dx, dy);
        }
        else if (pasted instanceof Triangle t) {
            Point ref = t.getVertices().get(0);
            double dx = e.getX() - ref.x;
            double dy = e.getY() - ref.y;
            t.translate(dx, dy);
        }

        model.addShape(pasted);
        panel.requestRender(); // trigger redraw

        // exit paste mode after one placement
        panel.setStrategy(null);
    }

    @Override public void onMousePressed(MouseEvent e) { }
    @Override public void onMouseReleased(MouseEvent e) { }
    @Override public void onMouseDragged(MouseEvent e) { }
    @Override public void onMouseMoved(MouseEvent e) { }
}
