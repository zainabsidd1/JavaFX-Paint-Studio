package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode="Circle";
    private PaintModel model;

    public Circle circle; // This is VERY UGLY, should somehow fix this!!
    private Rectangle rectangle;

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model=model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }
    /**
     *  Controller aspect of this
     */
    public void setMode(String mode){
        this.mode=mode;
        System.out.println(this.mode);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        // "Circle", "Rectangle", "Square", "Squiggle", "Polyline"
        switch(this.mode){
            case "Circle":
                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Circle");
                     Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
                        this.circle=new Circle(centre, 0);
                        update(null, null);
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) { // using pythagorean theorem
                    double x = mouseEvent.getX() - circle.getCentre().x;
                    double y = mouseEvent.getY() - circle.getCentre().y;
                    double r = Math.sqrt((x * x) + (y * y)); // calculate radius
                    circle.setRadius(r);
                    update(null, null); // live preview
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {

                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if(this.circle!=null){
                                this.model.addCircle(this.circle);
                                System.out.println("Added Circle");
                                this.circle=null;
                                update(null, null);
                        }
                }

                break;
            case "Rectangle":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Rectangle");
                    Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
                    this.rectangle = new Rectangle(p, p); // zero sized rect started at press
                    update(null, null);
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    if (this.rectangle != null) {
                        this.rectangle.setP2(new Point(mouseEvent.getX(), mouseEvent.getY())); // live preview or rect growing
                        // redraw(); when implemented
                        update(null, null); // live previews every mouse move
                    }
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();
                    // System.out.printf("Mouse at", x, y); // need to change prints x, y
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if (this.rectangle != null) {
                        this.rectangle.setP2(new Point(mouseEvent.getX(), mouseEvent.getY()));
                        this.model.addRectangle(this.rectangle);
                        System.out.println("Added Rectangle");
                        this.rectangle=null; // clear all preview
                    }
                }
                break;
            case "Square": break;
            case "Squiggle": //please be a new branch
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) { // every new press should be a new squiggle
                    this.model.startNewSqiuggle();
                    this.model.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
                if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    this.model.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
                break;
            case "Polyline": break;
            default: break;
        }
    }
    @Override
    public void update(Observable o, Object arg) {

                GraphicsContext g2d = this.getGraphicsContext2D();
                g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
                // Draw Lines
                g2d.setGlobalAlpha(1.0);
                g2d.setLineDashes(0);        // seperate rectangle colour from squiggle
                g2d.setStroke(Color.RED);
                for(ArrayList<Point> squiggle : this.model.getSquiggles()){

                        for(int i=0;i<squiggle.size()-1;i++) { //draw all the points in each squiggle
                                Point p1 = squiggle.get(i);
                                Point p2 = squiggle.get(i + 1);
                                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
                        }
                }


                // Draw Circles
                ArrayList<Circle> circles = this.model.getCircles();

                g2d.setFill(Color.LIGHTBLUE); // colour circle
                for(Circle c: this.model.getCircles()){
                        double x = c.getCentre().x;
                        double y = c.getCentre().y;
                        double radius = c.getRadius();
                        g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2); // draw circle
                }
                if(this.circle!=null && "Circle".equals(this.mode)){ // check before drawing "current" circle
                    double x = circle.getCentre().x;
                    double y = circle.getCentre().y;
                    double radius = circle.getRadius();
                    g2d.setFill(Color.LIGHTBLUE);
                    g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                    g2d.setStroke(Color.LIGHTBLUE);
                    g2d.setLineWidth(2);
                    g2d.strokeOval(x - radius, y - radius, radius * 2, radius * 2); // outline circle
                }

                // Draw Rectangles
                ArrayList<Rectangle> rectangles = this.model.getRectangles();
                g2d.setFill(Color.PINK); // fill colour of rect
                for (Rectangle r: this.model.getRectangles()) {
                    g2d.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
                } // draw all rects
                if (this.rectangle != null && "Rectangle".equals(this.mode)) {
                    double x = rectangle.getLeft();
                    double y = rectangle.getTop();
                    double w = rectangle.getWidth();
                    double h = rectangle.getHeight();
                    g2d.setFill(Color.PINK);
                    g2d.fillRect(x, y, w, h); // rect fill
                    g2d.setStroke(Color.PINK);
                    g2d.setLineWidth(2);
                    g2d.strokeRect(x, y, w, h);
                }
    }
}
