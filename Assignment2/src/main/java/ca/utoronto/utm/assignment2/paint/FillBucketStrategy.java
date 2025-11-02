package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FillBucketStrategy implements ToolStrategy {
    private final PaintModel model;
    private final PaintPanel panel;
    private final Cursor bucketCursor;

    public FillBucketStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;

        Image cur = new Image(getClass().getResourceAsStream("/icons/PaintBucket.png"));
        // tweak hotspot so the “pour tip” is accurate for clicks
        this.bucketCursor = new ImageCursor(cur, 2, cur.getHeight() - 4);
    }

    @Override public Cursor getCursor() { return bucketCursor; }

    @Override
    public String getName() {
        return "Fill Bucket Strategy";
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        model.fillTopmostAt(e.getX(), e.getY(), model.getCurrentColor());
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
