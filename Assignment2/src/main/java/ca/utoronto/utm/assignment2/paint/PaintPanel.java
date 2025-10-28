package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.Observable;
import java.util.Observer;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer, PaintModelListener {
    private final PaintModel model;
    private Color currentColor = Color.LIGHTBLUE;
    private ToolStrategy currentStrategy;

    // Strategy wiring
    private final Map<String, ToolStrategy> tools = new HashMap<>();
    private ToolStrategy currentTool;

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model = model;
        this.model.addListener(this);


        // Register tools (constructor-inject model + this panel)
        register(new CircleStrategy(model, this));
        register(new RectangleStrategy(model, this));
        register(new SquiggleStrategy(model, this));
        register(new PolylineStrategy(model, this));
        register(new SquareStrategy(model, this));
        setTool("Circle"); // default tool

        // Mouse event wiring
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }

    public void setStrategy(ToolStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public ToolStrategy getStrategy() {
        return this.currentStrategy;
    }

    public void setColor(Color c) { currentColor = c; }
    public Color getColor() { return currentColor; }

    private void register(ToolStrategy tool) {
        tools.put(tool.getName(), tool);
    }

    /** Called by toolbar / chooser to switch tools */
    public void setTool(String name) {
        ToolStrategy t = tools.get(name);
        if (t != null) {
            this.currentTool = t;
            // clear any stale preview by repainting
            requestRender();
            System.out.println("Tool: " + name);
        } else {
            System.err.println("Unknown tool: " + name);
        }
    }

    /** Let strategies trigger a repaint for live preview
     * without mutating the model */
    public void requestRender() {
        this.update(null, null);
    }

    @Override
    public void handle(MouseEvent e) {
        if (currentStrategy == null) return;

        EventType<? extends MouseEvent> type = e.getEventType();

        if (type == MouseEvent.MOUSE_PRESSED) currentStrategy.onMousePressed(e);
        else if (type == MouseEvent.MOUSE_DRAGGED) currentStrategy.onMouseDragged(e);
        else if (type == MouseEvent.MOUSE_RELEASED) currentStrategy.onMouseReleased(e);
        else if (type == MouseEvent.MOUSE_MOVED) currentStrategy.onMouseMoved(e);
        else if (type == MouseEvent.MOUSE_CLICKED) currentStrategy.onMouseClicked(e);
    }


    @Override
    public void update(Observable o, Object arg) {
        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        // Draw all permanent shapes
        for (Shape s : model.getShapes()) {
            s.draw(g2d);
        }

        // Draw preview of the current tool
        if (currentStrategy != null) {
            currentStrategy.drawPreview(g2d);
        }
        if (currentStrategy != null) {
            currentStrategy.drawPreview(this.getGraphicsContext2D());
        }
    }



    // Getter for the list of tools.
    public Collection<ToolStrategy> getTools() {
        return tools.values();
    }

    @Override
    public void modelChanged() {
        requestRender();
    }
}
