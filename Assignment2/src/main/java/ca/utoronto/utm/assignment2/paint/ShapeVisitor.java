package ca.utoronto.utm.assignment2.paint;

public interface ShapeVisitor {
    void visit(Circle c);
    void visit(Rectangle r);
    void visit(Squiggle s);
    void visit(Polyline p);
    void visit(Square s);
    void visit(Triangle t);
    void visit(Oval o);
}
