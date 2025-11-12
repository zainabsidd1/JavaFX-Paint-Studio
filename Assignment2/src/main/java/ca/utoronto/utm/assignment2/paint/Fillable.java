package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;

public interface Fillable {
    void setFillColor(Color c);
    Color getFillColor();
    void applyFill(Color c);
    void setFilled(boolean filled);
    boolean getFilled();
}
