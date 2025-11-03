package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape, Fillable, Hittable {
        private final Point center;
        private double radius;
        private Color strokeColor;
        private Color fillColor;

        public Circle(Point center, double radius, Color color) {
                this.center = center;
                this.radius = radius;
                this.strokeColor = color;
                this.fillColor = color;
        }

        public void setRadius(double radius) { this.radius = radius; }
        public Point getCenter() { return center; }
        public double getRadius() { return radius; }

        @Override
        public void setFillColor(Color c) {
                if (c != null) this.fillColor = c;
        }

        @Override
        public Color getFillColor() {
                return this.fillColor;
        }

        @Override
        public boolean contains(double x, double y) {
                double dx = x - center.x;
                double dy = y - center.y;
                return dx * dx + dy * dy <= radius * radius;
        }

        @Override
        public Color getColor() { return strokeColor; }

        @Override
        public void setColor(Color c) {
                if (c != null) this.strokeColor = c;
        }

        @Override
        public void draw(GraphicsContext g) {
                double x = center.x - radius;
                double y = center.y - radius;
                double size = radius * 2;

                if (fillColor != null) {
                        g.setFill(fillColor);
                        g.fillOval(x, y, size, size);
                }

                g.setStroke(fillColor);
                g.setLineWidth(2);
                g.strokeOval(x, y, size, size);
        }
}
