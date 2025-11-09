package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.List;

public final class CompositeCommand implements Command {
    private final List<Command> children = new ArrayList<>();

    public CompositeCommand add(Command c) { children.add(c); return this; }
    public List<Command> children() { return children; }

    @Override public void apply() {
        for (Command c : children) c.apply();
    }
    @Override public void unapply() {
        for (int i = children.size()-1; i >= 0; i--) children.get(i).unapply();
    }
}
