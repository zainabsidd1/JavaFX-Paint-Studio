package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

/**
 * Represents a fill action so it can be undone/redone.
 */
public record FillChange(Fillable target, Color prev, Color next) {
}
