package ca.utoronto.utm.assignment2.paint;

import java.util.List;

public final class LoadSceneryCommand implements Command {
    private final CompositeCommand batch = new CompositeCommand();

    public LoadSceneryCommand(PaintModel model, List<Shape> shapes) {
        batch.add(new ClearAllShapesCommand(model));
        batch.add(new BatchAddCommand(model, shapes));
    }

    @Override public void apply()   { batch.apply(); }
    @Override public void unapply() { batch.unapply(); }
}
