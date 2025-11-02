package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class OvalStrategy implements ToolStrategy, Colorable{
    private final PaintModel model;
    private final PaintPanel panel;
    private Oval oval;
    private Color color = Color.LIGHTSEAGREEN;

    public OvalStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }
    @Override
    public String getName(){ return "Oval"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        Color chosen = (model.getCurrentColor() != null && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        oval = new Oval(p, p, color, true);
        oval.setColor(chosen);
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
            model.addShape(oval);
            oval = null;
        }
    }

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
        if (oval == null) return;
        double x = oval.getLeft();
        double y = oval.getTop();
        double width = oval.getWidth();
        double height = oval.getHeight();

        Color previewColour = (model.getCurrentColor() != null &&
                !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        g.setStroke(previewColour);
        g.setLineWidth(2);

        if (oval.getFilled()) {
            g.setFill(previewColour);
            g.fillOval(x, y, width, height);
        } else {
            g.strokeOval(x, y, width, height);
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e) {}

    @Override
    public void onMouseClicked(MouseEvent e) {}
}
