package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CircleStrategy implements ToolStrategy, Colorable {
    private final PaintModel model;
    private final PaintPanel panel; // used for preview repaint
    private Circle circle;
    private Color color = Color.LIGHTBLUE;

    public CircleStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override public String getName() { return "Circle"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point centre = new Point(e.getX(), e.getY());
        // lambda for if the user picks a color, use it; otherwise use the circle's default
        Color chosen = (model.getCurrentColor() != null && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        circle = new Circle(centre, 0, chosen);
        circle.setColor(chosen);
        circle.setFilled(model.isFilled());

        panel.requestRender();
    }


    @Override
    public void onMouseDragged(MouseEvent e) {
        if (circle != null) {
            double dx = e.getX() - circle.getCenter().x;
            double dy = e.getY() - circle.getCenter().y;
            circle.setRadius(Math.sqrt(dx*dx + dy*dy));
            panel.requestRender(); // live preview
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (circle != null) {
            double dx = e.getX() - circle.getCenter().x;
            double dy = e.getY() - circle.getCenter().y;
            circle.setRadius(Math.sqrt(dx*dx + dy*dy));
            circle.setFilled(model.isFilled());
            model.addShape(circle);
            circle = null; // clear preview
        }
    }

    @Override public void onMouseMoved(MouseEvent e) { }
    @Override public void onMouseClicked(MouseEvent e) { }

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
        if (circle == null) return;
        double x = circle.getCenter().x, y = circle.getCenter().y, r = circle.getRadius();
        Color previewColor = (model.getCurrentColor() != null && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;

        g.setStroke(previewColor);
        g.setLineWidth(2);
        if(model.isFilled()) {
            g.setFill(previewColor);
            g.fillOval(x - r, y - r, r * 2, r * 2);
        }

        g.strokeOval(x - r, y - r, r * 2, r * 2);

    }
}


