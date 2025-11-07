package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;



public class SquareStrategy implements ToolStrategy, Colorable {
    private final PaintModel model;
    private final PaintPanel panel;
    private Square square;
    private Color color = Color.CYAN;

    public SquareStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName(){return "Square";}

    @Override
    public void onMousePressed(MouseEvent e){
        Shape clicked = model.findTopmostAt(e.getX(), e.getY());
        if (clicked != null) {
            model.setSelectedShape(clicked);
            square = null;
            return;
        }

        Point p = new Point(e.getX(), e.getY());
        Color chosen = (model.getCurrentColor() != null && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        square = new Square(p, p, chosen);
        square.setStrokeWidth(model.getStrokeWidth());
        square.setColor(chosen);
        square.setFillColor(chosen);
        panel.requestRender();
    }

    @Override
    public void onMouseDragged(MouseEvent e){
        if(square != null){
            Point p2 = new Point(e.getX(), e.getY());
            double dx = Math.abs(p2.x - square.getP1().x);
            double dy = Math.abs(p2.y - square.getP1().y);
            double length = Math.min(dx, dy);

            double newX;
            double newY;
            if(p2.x >= square.getP1().x){ // mouse is right of P1 - drag right
                newX = square.getP1().x + length;
            } else{ // drag left
                newX = square.getP1().x - length;
            }
            if (p2.y >= square.getP1().y){ // mouse is below P1
                newY = square.getP1().y + length;
            } else{ // mouse is above P1
                newY = square.getP1().y - length;
            }
            square.setP2(new Point(newX, newY));
            panel.requestRender();

        }
    }

    @Override
    public void onMouseReleased(MouseEvent e){
        if(square != null){
            square.setFilled(model.isFilled());
            model.addShape(square);
            model.setStrokeWidth(model.getStrokeWidth());
            square = null;
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        if (c != null) this.color = c;
    }

    @Override
    public void drawPreview(GraphicsContext g){
        if(square==null) return;
        double x = square.getLeft();
        double y = square.getTop();
        double length = square.getLength();
        Color previewColour = (model.getCurrentColor() != null
                && !model.getCurrentColor().equals(Color.BLACK))
                ? model.getCurrentColor()
                : color;
        g.setStroke(previewColour);
        g.setLineWidth(model.getStrokeWidth());
        if(model.isFilled()){
            g.setFill(previewColour);
            g.fillRect(x, y, length, length);
        }

        g.strokeRect(x,y,length,length);
    }

    @Override
    public void onMouseMoved(MouseEvent e){/* no-op */}
    @Override
    public void onMouseClicked(MouseEvent e){
        Shape clicked = model.findTopmostAt(e.getX(), e.getY());
        model.setSelectedShape(clicked);
    }
}
