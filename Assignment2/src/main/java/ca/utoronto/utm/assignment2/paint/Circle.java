package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {
        private final Point center;
        private double radius;
        private final Color color;

        public Circle(Point center, double radius, Color color) {
                this.center = center;
                this.radius = radius;
                this.color = color;
        }
        public void setRadius(double radius) { this.radius = radius; }
        public Point getCenter() { return center; }
        public double getRadius() { return radius; }

        @Override
        public void draw(GraphicsContext g) {
                g.setFill(color);
                g.setStroke(color);
                g.setLineWidth(2);
                g.fillOval(center.x - radius, center.y - radius,
                        radius * 2, radius * 2);
                g.strokeOval(center.x - radius, center.y - radius,
                        radius * 2, radius * 2);
        }
}
