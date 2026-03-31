package ca.utoronto.utm.assignment2.paint;

public final class MoveCommand implements Command {
    private final Shape s;
    private final double dx, dy;

    public MoveCommand(Shape s, double dx, double dy) {
        this.s = s;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void apply() { s.translate(dx, dy); }

    @Override
    public void unapply() { s.translate(-dx, -dy); }
}
