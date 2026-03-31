package ca.utoronto.utm.assignment2.paint;

import java.lang.reflect.Field;
import java.util.List;

/** Clears all current shapes as one undoable step. */
public final class ClearAllShapesCommand implements Command {
    private final CompositeCommand batch = new CompositeCommand();

    public ClearAllShapesCommand(PaintModel model) {
        List<Shape> internal = getInternalList(model);
        List<Shape> snapshot = List.copyOf(internal);
        for (Shape s : snapshot) {
            batch.add(new RemoveShapeCommand(internal, s));
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
