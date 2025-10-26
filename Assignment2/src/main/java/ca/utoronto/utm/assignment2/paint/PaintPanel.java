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

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private final PaintModel model;

    // Strategy wiring
    private final Map<String, ToolStrategy> tools = new HashMap<>();
    private ToolStrategy currentTool;

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model = model;
        this.model.addObserver(this);

        // Register tools (constructor-inject model + this panel)
        register(new CircleStrategy(model, this));
        register(new RectangleStrategy(model, this));
        register(new SquiggleStrategy(model, this));
        // You can add more: register(new SquareStrategy(model, this)), etc.

        setTool("Circle"); // default tool

        // Mouse event wiring
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }

    private void register(ToolStrategy tool) {
        tools.put(tool.getName(), tool);
    }

    /** Called by toolbar / chooser to switch tools */
    public void setTool(String name) {
        ToolStrategy t = tools.get(name);
        if (t != null) {
            this.currentTool = t;
            // Optional: clear any stale preview by repainting
            requestRender();
            System.out.println("Tool: " + name);
        } else {
            System.err.println("Unknown tool: " + name);
        }
    }

    /** Let strategies trigger a repaint for live preview without mutating the model */
    public void requestRender() {
        this.update(null, null);
    }

    @Override
    public void handle(MouseEvent e) {
        if (currentTool == null) return;

        EventType<? extends MouseEvent> type = e.getEventType();
        if (type == MouseEvent.MOUSE_PRESSED)      currentTool.onMousePressed(e);
        else if (type == MouseEvent.MOUSE_DRAGGED)  currentTool.onMouseDragged(e);
        else if (type == MouseEvent.MOUSE_RELEASED) currentTool.onMouseReleased(e);
        else if (type == MouseEvent.MOUSE_MOVED)    currentTool.onMouseMoved(e);
        else if (type == MouseEvent.MOUSE_CLICKED)  currentTool.onMouseClicked(e);
    }

    @Override
    public void update(Observable o, Object arg) {
        GraphicsContext g = this.getGraphicsContext2D();
        g.clearRect(0, 0, getWidth(), getHeight());

        // Draw existing shapes from the model

        // Squiggles
        g.setGlobalAlpha(1.0);
        g.setLineDashes(0);
        g.setStroke(Color.RED);
        for (ArrayList<Point> squiggle : model.getSquiggles()) {
            for (int i = 0; i < squiggle.size() - 1; i++) {
                Point p1 = squiggle.get(i);
                Point p2 = squiggle.get(i + 1);
                g.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Circles
        g.setFill(Color.LIGHTBLUE);
        for (Circle c : model.getCircles()) {
            double x = c.getCentre().x, y = c.getCentre().y, r = c.getRadius();
            g.fillOval(x - r, y - r, r * 2, r * 2);
        }

        // Rectangles
        g.setFill(Color.PINK);
        for (Rectangle r : model.getRectangles()) {
            g.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
        }

        if (currentTool != null) currentTool.drawPreview(g);
    }
}
