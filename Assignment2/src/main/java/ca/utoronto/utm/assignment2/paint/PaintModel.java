package ca.utoronto.utm.assignment2.paint;

import java.util.*;
import javafx.scene.paint.Color;

public class PaintModel {
    // Observer Pattern
    private Color currentColor = null;
    private final List<PaintModelListener> listeners = new ArrayList<>();
    private void notifyListeners() {
        for (PaintModelListener l : listeners) {
            l.modelChanged();
        }
    }
    public void addListener(PaintModelListener l) { listeners.add(l); }
    public void removeListener(PaintModelListener l) { listeners.remove(l); }

    // Model State
    private final List<Shape> shapes = new ArrayList<>();

    // Command-based undo/redo
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();
    private void exec(Command c){
        c.apply();
        undoStack.push(c);
        redoStack.clear();
        notifyListeners();
    }

    private Squiggle currentSquiggle;
    private Polyline polylineCurr;
    private Polyline currEraser;

    private Shape selectShape;
    private Shape storeShape;

    // Color
    public Color getCurrentColor() {
        return currentColor; // may be null if user never picked one
    }
    public boolean hasUserColor() { return currentColor != null; }
    public void setCurrentColor(Color c) {
        if (c == null) return;
        if (!Objects.equals(this.currentColor, c)) {
            this.currentColor = c;
            notifyListeners();
        }
    }

    // Shape handling
    public void addShape(Shape s) {
        if (s == null) return;
        exec(new AddShapeCommand(shapes, s));
    }
    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }

    public void clearAll() {
        shapes.clear();
        currentSquiggle = null;
        polylineCurr = null;
        currEraser = null;
        undoStack.clear();
        redoStack.clear();
        notifyListeners();
    }

    // Squiggle
    public void startNewSquiggle() {
        currentSquiggle = new Squiggle();
        if (hasUserColor()) currentSquiggle.setColor(currentColor); // only override if user picked
        exec(new AddShapeCommand(shapes, currentSquiggle));
    }
    public void addPoint(Point p) {
        if (currentSquiggle == null) startNewSquiggle();
        currentSquiggle.addPoint(p);
        notifyListeners();
    }

    // Polyline
    public void startNewPolyline() {
        polylineCurr = new Polyline();
        if (hasUserColor()) polylineCurr.setColor(currentColor); // only override if user picked
        exec(new AddShapeCommand(shapes, polylineCurr));
    }
    public void addPolylinePoint(Point p) {
        if (polylineCurr == null) startNewPolyline();
        polylineCurr.addPoint(p);
        notifyListeners();
    }

    // Eraser (as a polyline with background colour)
    public void startNewEraser(){
        currEraser = new Polyline();
        currEraser.setColor(Color.web("#F4F4F4"));
        exec(new AddShapeCommand(shapes, currEraser));
    }
    public void addEraserPoint(Point p){
        if (currEraser == null) startNewEraser(); // FIXED (was startNewSquiggle)
        currEraser.addPoint(p);
        notifyListeners();
    }

    // Undo/Redo via Command
    public void undo() {
        if (undoStack.isEmpty()) return;
        Command c = undoStack.pop();
        c.unapply();
        redoStack.push(c);
        notifyListeners();
    }
    public void redo(){
        if (redoStack.isEmpty()) return;
        Command c = redoStack.pop();
        c.apply();
        undoStack.push(c);
        notifyListeners();
    }

    /** Returns the top-most shape under (x,y), or null if none. */
    public Shape findTopmostAt(double x, double y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s instanceof Hittable && ((Hittable) s).contains(x, y)) {
                return s;
            }
        }
        return null;
    }

    /** Fill the top-most Fillable shape at (x,y) with the given color (or currentColor if null). */
    public boolean fillTopmostAt(double x, double y, Color c) {
        Shape s = findTopmostAt(x, y);
        if (s instanceof Fillable) {
            Fillable f = (Fillable) s;
            Color use = (c != null) ? c : this.currentColor;
            if (use == null) use = Color.BLACK;

            Color prev = f.getFillColor();
            if (Objects.equals(prev, use)) {
                return false;
            }
            exec(new FillCommand(f, prev, use));
            f.applyFill(use);
            return true;
        }
        return false;
    }

    public void setSelectedShape(Shape s){
        this.selectShape = s;
    }
    public Shape getSelectedShape() { return selectShape; }

    public Shape getStoredShape() { return storeShape; }

    // Copy
    public void copyShape(){
        if(selectShape == null) return;
        storeShape = selectShape.copy();
    }

    // Cut
    public void cutShape() {
        if (selectShape == null) return;
        storeShape = selectShape.copy();
        exec(new RemoveShapeCommand(shapes, selectShape));
        selectShape = null;
    }

    // Paste
    public void pasteShape(){
        if (storeShape == null) return;
        Shape newShape = storeShape.copy();
        try { newShape.translate(10, 10); } catch (Exception ignored) {}
        addShape(newShape); // goes through exec(AddShapeCmd)
    }

    // Filled/Outline toggle
    private boolean filled = true;
    public boolean isFilled() { return filled; }
    public void setFilled(boolean filled) {
        this.filled = filled;
        notifyListeners();
    }

    public Shape selectTopMostAt(double x, double y, Color highlightColor) {
        return findTopmostAt(x, y);
    }
}
