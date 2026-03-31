package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;

public class FillBucketStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel;
    private final Cursor bucketCursor;

    public FillBucketStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;

        Image original = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/icons/PaintBucket.png")),
                20, 20,
                true,
                true
        );
        this.bucketCursor = new ImageCursor(original, 2, original.getHeight() - 2);
    }

    @Override public Cursor getCursor() { return bucketCursor; }

    @Override
    public String getName() {
        return "Fill Bucket Strategy";
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Color color = model.getCurrentColor();

        boolean shapeFill = model.fillTopmostAt(x, y, color);

        if(!shapeFill) {
            model.executeCommand(new ChangeBackgroundCommand(model, color));
            //model.setBackgroundColor(color!=null ? color : Color.WHITE);
        }
        panel.requestRender();
    }


    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void onMouseMoved(MouseEvent e) {

    }

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override public String toString() { return "Fill"; }
}
