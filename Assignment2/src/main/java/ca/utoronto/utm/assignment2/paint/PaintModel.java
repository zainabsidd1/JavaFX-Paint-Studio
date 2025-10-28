package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaintModel {
        // Observer Pattern
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

        public void addShape(Shape s) {
                if (s == null) return;
                shapes.add(s);
                notifyListeners();
        }

        public List<Shape> getShapes() {
                return Collections.unmodifiableList(shapes);
        }

        public void clearAll() {
                shapes.clear();
                currentSquiggle = null;
                notifyListeners();
        }

        // Squiggle Convenience
        public void startNewSquiggle() {
                currentSquiggle = new Squiggle();
                shapes.add(currentSquiggle);
                notifyListeners();
        }

        public void addPoint(Point p) {
                if (currentSquiggle == null) startNewSquiggle();
                currentSquiggle.addPoint(p);
                notifyListeners();
        }

        // Polyline convenience
        public void startNewPolyline(){
            polylineCurr = new Polyline();
            shapes.add(polylineCurr);
            notifyListeners();
        }

        public void addPolylinePoint(Point p) {
            if (polylineCurr == null) startNewSquiggle();
            polylineCurr.addPoint(p);
            notifyListeners();
        }


        public void addToShape(Shape s) {
            shapes.add(s);
            notifyListeners();
        }
}
