package ca.utoronto.utm.assignment2.paint;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class SprayStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private SprayCan sprayCan;
    private double cx, cy;
    private Timeline timer;
    private final int pointsPerTick = 8;     // density
    private final double tickMs = 16;        // ~60 fps
    private final double nozzle = 18;        // radius px

    public SprayStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() { return "Spray Can";}

    @Override
    public void onMousePressed(MouseEvent e) {
        cx = e.getX(); cy = e.getY();
        model.startNewSpray();
        startTimer();
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
            double u = rnd.nextDouble();
            double v = rnd.nextDouble();
            double r = (sprayCan.getStrokeWidth()/2.0) * Math.sqrt(u);
            double theta = 2 * Math.PI * v;
            double x = cx + r * Math.cos(theta);
            double y = cy + r * Math.sin(theta);
            sprayCan.addPoint(x, y);
        }
        panel.requestRender();
    }
}
