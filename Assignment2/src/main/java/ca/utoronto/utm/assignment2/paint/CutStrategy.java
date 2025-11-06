package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class CutStrategy implements ToolStrategy {
    private final PaintModel model;

    public CutStrategy(PaintModel model) {
        this.model = model;
    }

    @Override
    public String getName() {return "Cut";}

    @Override
    public void onMousePressed(MouseEvent e) {
        Shape clicked = model.findTopmostAt(e.getX(), e.getY());
        if (clicked != null) {
            model.setSelectedShape(clicked);
            model.cutShape();
        }
    }

    @Override public void onMouseDragged(MouseEvent e) { }
    @Override public void onMouseReleased(MouseEvent e) { }
    @Override public void onMouseMoved(MouseEvent e) { }
    @Override public void onMouseClicked(MouseEvent e) { }
}
