package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;



public class SquareStrategy implements ToolStrategy{
    private final PaintModel model;
    private final PaintPanel panel;
    private Square square;

    public SquareStrategy(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName(){return "Square";}

    @Override
    public void onMousePressed(MouseEvent e){
        Point p = new Point(e.getX(), e.getY());
        square = new Square(p, p, Color.CYAN);
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
            model.addShape(square);
            square = null;
        }
    }


    @Override
    public void drawPreview(GraphicsContext g){
        // To be implemented in US2.002
    }

    @Override
    public void onMouseMoved(MouseEvent e){/* no-op */}
    @Override
    public void onMouseClicked(MouseEvent e){/* no-op */}
}
