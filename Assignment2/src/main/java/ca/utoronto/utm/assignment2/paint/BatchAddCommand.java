package ca.utoronto.utm.assignment2.paint;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Wrap many AddShapeCommand(s) into one Command.
 * AddShapeCommand mutates the actual list (without altering the undo stack).
 */
public final class BatchAddCommand implements Command {
    private final CompositeCommand batch = new CompositeCommand();

    public BatchAddCommand(PaintModel model, List<Shape> shapesToAdd) {
        List<Shape> internalList = getInternalList(model);
        for (Shape s : shapesToAdd) {
            batch.add(new AddShapeCommand(internalList, s));
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Shape> getInternalList(PaintModel model) {
        try {
            Field f = PaintModel.class.getDeclaredField("shapes");
            f.setAccessible(true);
            return (List<Shape>) f.get(model);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to access model shapes list", e);
        }
    }
    @Override public void apply()   { batch.apply(); }
    @Override public void unapply() { batch.unapply(); }
}
