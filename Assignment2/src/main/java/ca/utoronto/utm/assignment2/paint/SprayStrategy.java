package ca.utoronto.utm.assignment2.paint;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.animation.KeyFrame;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class SprayStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private SprayCan sprayCan;
    private double cx, cy;
    private Timeline timer;
    private final int pointsPerTick = 20;     // density
    private final double tickMs = 16;        // ~60 fps run rate when the mouse click is held
    private final double nozzle = 10;

    public SprayStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() { return "Spray Can";}

    @Override
    public void onMousePressed(MouseEvent e) {
        cx = e.getX();
        cy = e.getY();
        sprayCan = new SprayCan(panel.getColor(), model.getStrokeWidth(), 1);
        model.addShape(sprayCan);
        emitPoints();
        panel.requestRender();
        startTimer();
    }


    @Override
    public void onMouseDragged(MouseEvent e) { cx = e.getX(); cy = e.getY(); }

    @Override
    public void onMouseReleased(MouseEvent e) {
        stopTimer();
        sprayCan = null;
        panel.requestRender();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        cx = e.getX(); cy = e.getY();
        panel.requestRender();
    }

    @Override
    public void onMouseClicked(MouseEvent e) {}

    @Override
    public void drawPreview(GraphicsContext g) {
        g.save();
        g.setGlobalAlpha(0.5);
        Color ring = (model.getCurrentColor() != null) ? model.getCurrentColor()
                : (sprayCan != null ? sprayCan.getColor() : Color.BLACK);
        g.setStroke(ring);
        g.strokeOval(cx - nozzle, cy - nozzle, nozzle * 2, nozzle * 2);
        g.restore();
    }


    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.millis(tickMs), ev -> emitPoints()));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) { timer.stop(); timer = null; }
    }

    private void emitPoints() {
        if (sprayCan == null) return;

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < pointsPerTick; i++) {
            double u = rnd.nextDouble(), v = rnd.nextDouble();
            double r = (sprayCan.getStrokeWidth() / 2.0) * Math.sqrt(u);
            double th = 2 * Math.PI * v;
            double x = cx + r * Math.cos(th);
            double y = cy + r * Math.sin(th);
            sprayCan.addPoint(x, y);
        }
        Color current = panel.getColor();
        if (current != null) sprayCan.setColor(current);

        panel.requestRender();
    }

}
