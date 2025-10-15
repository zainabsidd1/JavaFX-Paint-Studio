package ca.utoronto.utm.assignment2.paint;

public class Rectangle {
    private Point p1;
    private Point p2;

    public Rectangle(Point p1, Point p2){
        this.p1 = p1; // first corner (mouse press)
        this.p2 = p2; // second corner (mouse release)
    }

    // setters and getters for p1 and p2
    public void setP1(Point p1){
        this.p1 = p1;
    }

    public Point getP1(){
        return p1;
    }

    public void setP2(Point p2){
        this.p2 = p2;
    }

    public Point getP2(){
        return p2;
    }

    // convenience for drawing
    public double getLeft()   { return Math.min(p1.x, p2.x); }
    public double getTop()    { return Math.min(p1.y, p2.y); }
    public double getWidth()  { return Math.abs(p1.x - p2.x); }
    public double getHeight() { return Math.abs(p1.y - p2.y); }
}
