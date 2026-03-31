package ca.utoronto.utm.assignment2.paint;

public class MoveShapeCommand implements Command{
    private final Shape shape;
    private final double dx, dy;

    public MoveShapeCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void apply() {
        shape.translate(dx, dy);
    }

    @Override
    public void unapply() {
        shape.translate(-dx, -dy);
    }
}
