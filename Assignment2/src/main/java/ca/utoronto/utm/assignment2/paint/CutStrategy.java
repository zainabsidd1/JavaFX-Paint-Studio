package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

public class CutStrategy implements ToolStrategy {
    private final PaintModel model;
    private final ImageCursor cutCursor;

    public CutStrategy(PaintModel model) {
        this.model = model;

        Image img = new Image(
                getClass().getResourceAsStream("/icons/cut.png"), 20, 20, true, true);
        this.cutCursor = new ImageCursor(img, img.getWidth()/2, img.getHeight()/2);
    }

    @Override
    public Cursor getCursor() { return cutCursor; }

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
