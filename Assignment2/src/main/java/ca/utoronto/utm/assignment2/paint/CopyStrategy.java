package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

public class CopyStrategy implements ToolStrategy {
    private final PaintModel model;
    private final ImageCursor copyCursor;


    public CopyStrategy(PaintModel model) {
        this.model = model;

        Image img = new Image(
                getClass().getResourceAsStream("/icons/copy.png"), 20, 20, true, true);
        this.copyCursor = new ImageCursor(img, img.getWidth()/2, img.getHeight()/2);

    }

    @Override
    public Cursor getCursor() { return copyCursor; }

    @Override
    public String getName() {return "Copy";}

    @Override
    public void onMousePressed(MouseEvent e) {
        Shape clicked = model.findTopmostAt(e.getX(), e.getY());
        if (clicked != null) {
            model.setSelectedShape(clicked);
            model.copyShape();

        }
    }

    @Override public void onMouseDragged(MouseEvent e) { }
    @Override public void onMouseReleased(MouseEvent e) { }
    @Override public void onMouseMoved(MouseEvent e) { }
    @Override public void onMouseClicked(MouseEvent e) { }
}