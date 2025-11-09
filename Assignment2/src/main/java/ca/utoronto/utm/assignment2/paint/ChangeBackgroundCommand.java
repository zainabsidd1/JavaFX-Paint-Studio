package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;

public class ChangeBackgroundCommand implements Command {
    private final PaintModel model;
    private final Color oldColor;
    private final Color newColor;

    public ChangeBackgroundCommand(PaintModel model, Color newColor) {
        this.model = model;
        this.oldColor = model.getBackgroundColor();
        this.newColor = newColor;
    }

    @Override
    public void apply(){
        model.setBackgroundColor(newColor);
    }
    @Override
    public void unapply(){
        model.setBackgroundColor(oldColor);
    }


}
