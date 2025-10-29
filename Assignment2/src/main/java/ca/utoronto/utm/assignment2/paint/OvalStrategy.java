package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class OvalStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private Oval oval;

    public OvalStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }
    @Override
    public String getName(){ return "Oval"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        oval = new Oval(p, p, Color.LIGHTSEAGREEN, true);
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
    public void drawPreview(GraphicsContext g) {
        if (oval == null) return;

        double x = oval.getLeft();
        double y = oval.getTop();
        double width = oval.getWidth();
        double height = oval.getHeight();

       g.setStroke(Color.BLACK);
       g.setLineWidth(1);
       g.setLineDashes(4);
       g.strokeRect(x, y, width, height);
       g.setLineDashes();

        g.setStroke(Color.LIGHTSEAGREEN);
        g.setLineWidth(2);

        if (oval.getFilled()) {
            g.setFill(Color.LIGHTSEAGREEN);
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
