package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

/** Represents a fill action so it can be undone/redone. */
public class FillChange {
    private final Fillable target;
    private final Color prev;
    private final Color next;

    public FillChange(Fillable target, Color prev, Color next) {
        this.target = target;
        this.prev = prev;
        this.next = next;
    }

    public Fillable getTarget() { return target; }
    public Color getPrev() { return prev; }
    public Color getNext() { return next; }
}
