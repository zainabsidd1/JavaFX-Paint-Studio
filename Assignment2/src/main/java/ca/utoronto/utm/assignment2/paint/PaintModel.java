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
        private final Deque<Shape> undoStack = new ArrayDeque<>();
        private final Deque<Shape> redoStack = new ArrayDeque<>();


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
                Shape last = undoStack.pop();
                redoStack.push(last);
                shapes.remove(last);
                notifyListeners();
        }

        public void redo(){
            if (redoStack.isEmpty()) return;
            Shape last = redoStack.pop();
            undoStack.push(last);
            shapes.add(last);
            notifyListeners();
        }
}
