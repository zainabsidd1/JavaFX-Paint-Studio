package ca.utoronto.utm.assignment2.paint;

import java.util.List;

public final class AddShapeCommand implements Command {
    private final List<Shape> list;
    private final Shape s;

    public AddShapeCommand(List<Shape> list, Shape s) {
        this.list = list;
        this.s = s;
    }

    @Override
    public void apply() { list.add(s); }

    @Override
    public void unapply() { list.remove(s); }
}
