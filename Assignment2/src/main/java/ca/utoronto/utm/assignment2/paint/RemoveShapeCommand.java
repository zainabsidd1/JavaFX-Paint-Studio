package ca.utoronto.utm.assignment2.paint;

import java.util.List;

public final class RemoveShapeCommand implements Command {
    private final List<Shape> list;
    private final Shape s;
    private int index = -1;

    public RemoveShapeCommand(List<Shape> list, Shape s) {
        this.list = list;
        this.s = s;
    }

    @Override
    public void apply() {
        index = list.indexOf(s);
        if (index >= 0) list.remove(index);
    }

    @Override
    public void unapply() {
        if (index < 0) index = 0;
        list.add(index, s);
    }
}
