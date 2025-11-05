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
        private Squiggle currentSquiggle;
        private Polyline polylineCurr;
        private Polyline currEraser;
        private final Deque<Object> undoStack = new ArrayDeque<>();
        private final Deque<Object> redoStack = new ArrayDeque<>();
        private Shape selectShape;
        private Shape storeShape;


    // Color

        public Color getCurrentColor() {
                return currentColor; // may be null if user never picked one
        }

        public boolean hasUserColor() {
                return currentColor != null;
        }

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
                shapes.add(s);
                undoStack.push(s);
                redoStack.clear();
                notifyListeners();
        }

        public List<Shape> getShapes() {
                return Collections.unmodifiableList(shapes);
        }

        public void clearAll() {
                shapes.clear();
                currentSquiggle = null;
                polylineCurr = null;
                undoStack.clear();
                redoStack.clear();
                notifyListeners();
        }

        // Squiggle
        public void startNewSquiggle() {
                currentSquiggle = new Squiggle();
                if (hasUserColor()) currentSquiggle.setColor(currentColor); // only override if user picked
                shapes.add(currentSquiggle);
                undoStack.push(currentSquiggle);
                notifyListeners();
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
                shapes.add(polylineCurr);
                undoStack.push(polylineCurr);
                notifyListeners();
        }

        public void addPolylinePoint(Point p) {
                if (polylineCurr == null) startNewPolyline();
                polylineCurr.addPoint(p);
                notifyListeners();
        }

        public void addToShape(Shape s) {
                shapes.add(s);
                notifyListeners();
        }

        public void startNewEraser(){
            currEraser = new Polyline();
            currEraser.setColor(Color.web("#F4F4F4"));
            shapes.add(currEraser);
            undoStack.push(currEraser);
            notifyListeners();
        }

        public void addEraserPoint(Point p){
            if (currEraser == null) startNewSquiggle();
            currEraser.addPoint(p);
            notifyListeners();
        }

        public void undo() {
                if (undoStack.isEmpty()) return;
                Object last = undoStack.pop();

                if (last instanceof Shape s) {
                        shapes.remove(s);
                        redoStack.push(s);
                } else if (last instanceof FillChange fc) {
                        fc.getTarget().setFillColor(fc.getPrev());
                        redoStack.push(new FillChange(fc.getTarget(), fc.getPrev(), fc.getNext()));
                }
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
                        f.applyFill(c);
                        f.setFillColor(c);

                        undoStack.push(new FillChange(f, prev, use));
                        redoStack.clear();


                        notifyListeners();
                        return true;
                }
                return false;
        }

        public void redo(){
            if (redoStack.isEmpty()) return;
            Object last = redoStack.pop();
            if(last instanceof Shape s){
                shapes.add(s);
                undoStack.push(s);
            } else if(last instanceof FillChange fc){
                fc.getTarget().setFillColor(fc.getNext());
                undoStack.push(new FillChange(fc.getTarget(), fc.getNext(), fc.getPrev()));
            }
            notifyListeners();
        }

        public void setSelectedShape(Shape s){
        this.selectShape = s;
        }

        public Shape getSelectedShape() {
            return selectShape;
        }

        public Shape getStoredShape() {
        return storeShape;
        }

        // Copy
        public void copyShape(){
            if(selectShape == null) return;
            Shape copied = selectShape.copy();
            storeShape = selectShape.copy();
        }

        //Paste
        public void pasteShape(){
            if (storeShape == null) return;
            Shape newShape = storeShape.copy();
            addShape(newShape);
            notifyListeners();
        }

        // Filled/Outline toggle
        private boolean filled = true;
        public boolean isFilled() {
            return filled;
        }
        public void setFilled(boolean filled) {
            this.filled = filled;
            notifyListeners();
        }
        public Shape selectTopMostAt(double x, double y, Color highlightColor) {
            Shape s = findTopmostAt(x, y);
            return s;
        }
}
