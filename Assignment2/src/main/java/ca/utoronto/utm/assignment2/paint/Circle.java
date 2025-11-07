package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape, Fillable, Hittable, Strokeable {
        private final Point center;
        private double radius;
        private Color strokeColor;
        private Color fillColor;
        private boolean filled = true;
        private double strokeWidth = 2.0;

        public Circle(Point center, double radius, Color color) {
                this.center = center;
                this.radius = radius;
                this.strokeColor = color;
                this.fillColor = color;
        }

        public void setRadius(double radius) { this.radius = radius; }
        public Point getCenter() { return center; }
        public double getRadius() { return radius; }

        public void setFilled(boolean filled) { this.filled = filled; }
        public boolean getFilled() { return this.filled; }

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
        public void applyFill(Color c){
            if(c==null) return;
            setColor(c);
            setFillColor(c);
            this.setFilled(true);
        }

        @Override
        public void draw(GraphicsContext g) {
                double x = center.x - radius;
                double y = center.y - radius;
                double size = radius * 2;

                g.setStroke(getColor());
                g.setLineWidth(strokeWidth);

                if(filled) {
                    g.setFill(fillColor != null ? fillColor : strokeColor);
                    g.fillOval(x, y, size, size);
                }

                g.strokeOval(x, y, size, size);
        }

    @Override
    public void translate(double dx, double dy) {
        center.x += dx;
        center.y += dy;
    }

    public Circle(Circle other) {
        this.center = new Point(other.center.x, other.center.y);
        this.radius = other.radius;
        this.strokeColor = other.strokeColor;
        this.fillColor = other.fillColor;
        this.filled = other.filled;
    }


    @Override
    public Circle copy(){
        return new Circle(this);

    }

    @Override
    public double getStrokeWidth() { return strokeWidth; }
    @Override
    public void setStrokeWidth(double w) { this.strokeWidth = clampStroke(w); }

}
