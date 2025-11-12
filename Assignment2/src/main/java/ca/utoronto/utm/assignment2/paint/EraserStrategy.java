package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class EraserStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel;
    private ArrayList<Point> eraser;
    private Color color = Color.WHITE;

    public EraserStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        model.startNewEraser();
        model.addEraserPoint(new Point(e.getX(), e.getY()));
        eraser = new ArrayList<>();
        eraser.add(new Point(e.getX(), e.getY()));
        panel.requestRender();

    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Point p =  new Point(e.getX(), e.getY());
        model.addEraserPoint(p);
        if (eraser != null) eraser.add(p);
        panel.requestRender();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        eraser = null;
        panel.requestRender();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {}

    @Override
    public void onMouseClicked(MouseEvent e) {}

    @Override
    public void drawPreview(GraphicsContext g) {
        if (eraser == null || eraser.size() < 2) {
            return;
        }
        Color backgroundColor = model.getBackgroundColor();
        if (backgroundColor == null) backgroundColor = Color.WHITE;
        colorSetBackground(g, backgroundColor, eraser);
    }

    static void colorSetBackground(GraphicsContext g, Color backgroundColor, ArrayList<Point> eraser) {
        g.setStroke(backgroundColor);

        g.setLineDashes(0);
        g.setGlobalAlpha(1.0);
        RenderVisitor.visitHelper(eraser, g);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
