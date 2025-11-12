package ca.utoronto.utm.assignment2.paint;

public interface Shape extends Colorable {

    default void translate(double dx, double dy) {
        System.out.println("This is not hittable.");
    }

    void accept(ShapeVisitor visitor); // visitor design pattern

    Shape copy();
}

