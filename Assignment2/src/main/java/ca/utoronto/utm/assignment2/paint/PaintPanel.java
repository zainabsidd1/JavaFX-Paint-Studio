package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, PaintModelListener {

    private final PaintModel model;
    private Color currentColor = Color.LIGHTBLUE;
    private ToolStrategy activeTool;

    // Active drawing tool (Strategy)
    private ToolStrategy currentStrategy;

    public PaintPanel(PaintModel model) {
        super(300, 320);
        this.model = model;
        this.model.addListener(this);

        // Mouse event wiring
        this.addEventHandler(MouseEvent.MOUSE_PRESSED,  this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,  this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED,    this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,  this);
        // cursor updates when mouse enters canvas
        this.setOnMouseEntered(e -> {
            if (currentStrategy != null) setCursor(currentStrategy.getCursor());
        });
        // Repaint when the canvas is resized
        widthProperty().addListener((obs, o, n) -> requestRender());
        heightProperty().addListener((obs, o, n) -> requestRender());

        // Initial paint with nothing on it
        requestRender();
    }

    public void setActiveTool(ToolStrategy tool) {
        this.activeTool = tool;
        this.setCursor(tool.getCursor());
    }


    // Strategy wiring

    public void setStrategy(ToolStrategy strategy) {
        this.currentStrategy = strategy;
        requestRender(); // clear any stale preview
    }

    public ToolStrategy getStrategy() {
        return this.currentStrategy;
    }

    // set colour

    public void setColor(Color c) {
        this.currentColor = c;
        model.setCurrentColor(c);
        requestRender();
    }
    public Color getColor()       { return this.currentColor; }

    // Model → View (Observer)

    @Override
    public void modelChanged() {
        requestRender();
    }

    // Mouse → Strategy

    @Override
    public void handle(MouseEvent e) {
        if (currentStrategy == null) return;

        EventType<? extends MouseEvent> type = e.getEventType();

        if (type == MouseEvent.MOUSE_PRESSED)      currentStrategy.onMousePressed(e);
        else if (type == MouseEvent.MOUSE_DRAGGED)  currentStrategy.onMouseDragged(e);
        else if (type == MouseEvent.MOUSE_RELEASED) currentStrategy.onMouseReleased(e);
        else if (type == MouseEvent.MOUSE_MOVED)    currentStrategy.onMouseMoved(e);
        else if (type == MouseEvent.MOUSE_CLICKED)  currentStrategy.onMouseClicked(e);

    }

    // Rendering
    public void requestRender() {
        if (Platform.isFxApplicationThread()) render();
        else Platform.runLater(this::render);
    }

    private void render() {
        GraphicsContext g = getGraphicsContext2D();
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setFill(model.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());

        RenderVisitor visitor = new RenderVisitor(g); // visitor used
        for (Shape s : model.getShapes()) {
            s.accept(visitor);
        }
        Shape hud = model.getOverlay();
        if (hud != null) {hud.accept(visitor);}

        if (currentStrategy != null) {
            currentStrategy.drawPreview(g);
        }

    }
}
