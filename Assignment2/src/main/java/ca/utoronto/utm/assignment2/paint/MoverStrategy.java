package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MoverStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private final Cursor moverCursor;
    private Shape selectedShape;
    private double startX, startY;
    private double lastX, lastY;

    public MoverStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;

        Image original = new Image(
                getClass().getResourceAsStream("/icons/mover.png"),
                20, 20,
                true,
                true
        );
        this.moverCursor = new ImageCursor(original, 2, original.getHeight() - 2);
    }

    @Override public Cursor getCursor() { return moverCursor; }

    @Override
    public String getName() { return "Mover Strategy"; }

    @Override
    public void onMousePressed(MouseEvent e) {
        selectedShape = model.selectTopMostAt(e.getX(), e.getY(), Color.YELLOW);
        panel.requestRender();
        startX = e.getX();
        startY = e.getY();
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (selectedShape != null) {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            selectedShape.translate(dx, dy); //move the current shape by the delta
            lastX = e.getX();
            lastY = e.getY();
            panel.requestRender();
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (selectedShape != null) {
            double dx = e.getX() - startX;
            double dy = e.getY() - startY;

            if(Math.abs(dx) > 0.5 || Math.abs(dy) > 0.5) {
                selectedShape.translate(-dx, -dy); // undo movement to avoid shifting
                model.executeCommand(new MoveCommand(selectedShape, dx, dy)); // reapply
            }
            panel.requestRender();
        }
        selectedShape = null;
    }

    @Override
    public void onMouseMoved(MouseEvent e) {}
    @Override
    public void onMouseClicked(MouseEvent e) {}
}
