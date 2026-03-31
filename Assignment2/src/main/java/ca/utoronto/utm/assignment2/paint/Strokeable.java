package ca.utoronto.utm.assignment2.paint;

public interface Strokeable {
    double getStrokeWidth();
    void setStrokeWidth(double w);

    default double clampStroke(double w) {
        return Math.max(0.5, w);
    }
}
