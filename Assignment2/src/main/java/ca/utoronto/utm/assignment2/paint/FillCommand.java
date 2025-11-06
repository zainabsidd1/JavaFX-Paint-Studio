package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

public final class FillCommand implements Command {
    private final Fillable target;
    private final Color from, to;

    public FillCommand(Fillable target, Color from, Color to) {
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    public void apply() { target.setFillColor(to); }

    @Override
    public void unapply() { target.setFillColor(from); }
}
