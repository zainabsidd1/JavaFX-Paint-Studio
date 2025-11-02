package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Objects;
import javafx.scene.image.WritableImage;
import java.net.URL;

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

    private final View view;
    private Button selectedButton;

    public ShapeChooserPanel(View view) {
        this.view = view;

        int row = 0;
        List<ToolDescriptor> tools = List.of(
                new ToolDescriptor("Circle", "/icons/Circle.png", CircleStrategy.class),
                new ToolDescriptor("Rectangle", "/icons/Rectangle.png", RectangleStrategy.class),
                new ToolDescriptor("Squiggle", "/icons/Squiggle.png", SquiggleStrategy.class),
                new ToolDescriptor("Oval", "/icons/Oval.png", OvalStrategy.class),
                new ToolDescriptor("Square", "/icons/Square.png", SquareStrategy.class),
                new ToolDescriptor("Polyline", "/icons/Polyline.png", PolylineStrategy.class),
                new ToolDescriptor("Triangle", "/icons/Triangle.png", TriangleStrategy.class)
        );
        for (ToolDescriptor td : tools) {
            ImageView iv = safeIcon(td.iconPath(), 24, 24);

            Button button = new Button();         // icon-only button
            button.setGraphic(iv);
            button.setUserData(td);
            button.setMinWidth(100);
            button.setOnAction(this);
            this.add(button, 0, row++);
        }

        // Auto-select and apply the first tool
        if (!getChildren().isEmpty()) {
            Button first = (Button) getChildren().getFirst();
            highlight(first);
            activateTool((ToolDescriptor) first.getUserData());
        }
    }

    private ImageView safeIcon(String absolutePath, double w, double h) {
        String path = absolutePath.startsWith("/") ? absolutePath : "/" + absolutePath;
        URL url = getClass().getResource(path);

        Image img;
        if (url != null) {
            img = new Image(url.toExternalForm(), w, h, true, true);
        } else {
            System.err.println("Missing icon on classpath: " + path);
            img = new WritableImage((int) Math.ceil(w), (int) Math.ceil(h));
        }
        ImageView iv = new ImageView(img);
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        iv.setPreserveRatio(true);
        return iv;
    }

    @Override
    public void handle(ActionEvent event) {
        Button btn = (Button) event.getSource();
        highlight(btn);
        ToolDescriptor td = (ToolDescriptor) btn.getUserData();
        activateTool(td);
    }

    private void activateTool(ToolDescriptor td) {
        try {
            ToolStrategy strategy = td.strategyClass()
                    .getConstructor(PaintModel.class, PaintPanel.class)
                    .newInstance(view.getPaintModel(), view.getPaintPanel());
            view.getPaintPanel().setStrategy(strategy);
            System.out.println("Tool selected: " + td.name());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to instantiate tool: " + td.name());
        }
    }

    private void highlight(Button btn) {
        if (selectedButton != null) {
            selectedButton.setStyle(""); // clear previous
        }
        btn.setStyle(
                "-fx-border-color: black;" +
                        " -fx-border-width: 2px;" +
                        " -fx-background-color:#FCF55F;"
        );
        selectedButton = btn;
    }
}
