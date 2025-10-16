package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<ArrayList<Point>> squiggles=new ArrayList<>(); // stores distinct squiggles
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();


        public void addPoint(Point p){
                if(squiggles.size()==0) {startNewSqiuggle();}
                squiggles.get(squiggles.size()-1).add(p);
                this.setChanged();
                this.notifyObservers();
        }
        public ArrayList<Point> getPoints(){
                return points;
        }

        public ArrayList<ArrayList<Point>> getSquiggles(){return squiggles;}

        public void startNewSqiuggle(){
            squiggles.add(new ArrayList<Point>());
        }

        public void addCircle(Circle c){
                this.circles.add(c);
                this.setChanged();
                this.notifyObservers();
        }
        public ArrayList<Circle> getCircles(){
                return circles;
        }

        public void addRectangle(Rectangle r){
                this.rectangles.add(r);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Rectangle> getRectangles(){
                return rectangles;
        }
}
