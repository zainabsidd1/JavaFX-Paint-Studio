package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class View {

    private final PaintModel paintModel;
    private final PaintPanel paintPanel;

    public View(PaintModel model, Stage stage) {
        this.paintModel = model;
        this.paintPanel = new PaintPanel(this.paintModel);
        ShapeChooserPanel shapeChooserPanel = new ShapeChooserPanel(this);

        BorderPane root = new BorderPane();

        // --- Simple hex color selector bar ---
        Label hexLabel = new Label("Hex Color:");
        TextField hexField = new TextField("#FF0077"); // default
        Rectangle preview = new Rectangle(26, 26, Color.web("#FF0077"));
        preview.setStroke(Color.GRAY);
        preview.setArcWidth(6);
        preview.setArcHeight(6);

        // listener: only apply when a full 6-character hex is valid
        hexField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) return;

            // always ensure the # prefix
            if (!newV.startsWith("#")) {
                newV = "#" + newV.replaceAll("#", "");
                hexField.setText(newV);
                return;
            }

            // accept pattern like #AABBCC only (6 hex digits)
            if (newV.matches("^#[0-9A-Fa-f]{6}$")) {
                try {
                    Color parsed = Color.web(newV);
                    paintModel.setCurrentColor(parsed);
                    preview.setFill(parsed);
                    paintPanel.requestRender();
                } catch (Exception ignored) { }
            }
            // optional: visually mark invalid input
            else {
                hexField.setStyle("-fx-border-color: lightcoral; -fx-border-width: 1;");
            }
        });

        // little hover effect for preview
        preview.setOnMouseEntered(e -> preview.setStroke(Color.DARKGRAY));
        preview.setOnMouseExited(e -> preview.setStroke(Color.GRAY));

        HBox colorBar = new HBox(10, hexLabel, hexField, preview);
        colorBar.setPadding(new Insets(6, 10, 6, 10));
        colorBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #f8f8f8, #eaeaea);" +
                        "-fx-border-color: #ccc; -fx-border-width: 0 0 1 0;"
        );

        // combine menu + color bar
        VBox topBar = new VBox(createMenuBar(), colorBar);
        root.setTop(topBar);

        root.setCenter(this.paintPanel);
        root.setLeft(shapeChooserPanel);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
    }

    public PaintModel getPaintModel() { return this.paintModel; }
    public PaintPanel getPaintPanel() { return this.paintPanel; }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu file = new Menu("File");
        MenuItem newItem  = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        file.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        file.getItems().add(exitItem);

        // Edit menu
        Menu edit = new Menu("Edit");
        MenuItem cutItem   = new MenuItem("Cut");
        MenuItem copyItem  = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem undoItem  = new MenuItem("Undo");
        MenuItem redoItem  = new MenuItem("Redo");

        cutItem.setOnAction(e -> System.out.println("Cut (not implemented)"));
        copyItem.setOnAction(e -> System.out.println("Copy (not implemented)"));
        pasteItem.setOnAction(e -> System.out.println("Paste (not implemented)"));
        undoItem.setOnAction(e -> paintModel.undo());
        redoItem.setOnAction(e -> System.out.println("Redo (not implemented)"));
        edit.getItems().addAll(
                cutItem, copyItem, pasteItem,
                new SeparatorMenuItem(),
                undoItem, redoItem
        );

        menuBar.getMenus().addAll(file, edit);
        return menuBar;
    }
}
