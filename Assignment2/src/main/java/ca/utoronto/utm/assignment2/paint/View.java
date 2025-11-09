package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

import java.util.Locale;
import java.util.function.UnaryOperator;

public class View {

    private final PaintModel paintModel;
    private final PaintPanel paintPanel;

    public View(PaintModel model, Stage stage) {
        this.paintModel = model;
        this.paintPanel = new PaintPanel(this.paintModel);
        ShapeChooserPanel shapeChooserPanel = new ShapeChooserPanel(this);

        StrokeControls strokeControls = new StrokeControls(paintModel);


        HBox bottomBar = new HBox(strokeControls.getNode());
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(0));

        BorderPane root = new BorderPane();
        root.setBottom(bottomBar);


        Label colorLbl = new Label("🎨 Color:");
        colorLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

        Color initial = Color.web("#FF0077");
        paintModel.setCurrentColor(initial);

        TextField hexField = new TextField("#FF0077");
        hexField.setPrefWidth(80);
        hexField.setStyle("-fx-background-radius:6;-fx-border-radius:6;");

        UnaryOperator<TextFormatter.Change> hexFilter = change -> {
            String next = change.getControlNewText().toUpperCase(Locale.ROOT);
            if (!next.startsWith("#")) next = "#" + next.replace("#", "");
            String body = next.length() > 1 ? next.substring(1).replaceAll("[^0-9A-F]", "") : "";
            if (body.length() > 6) body = body.substring(0, 6);
            next = "#" + body;
            change.setText(next);
            change.setRange(0, change.getControlText().length());
            return change;
        };
        hexField.setTextFormatter(new TextFormatter<>(hexFilter));

        Rectangle preview = new Rectangle(22, 22, initial);
        preview.setStroke(Color.web("#777"));
        preview.setArcWidth(6);
        preview.setArcHeight(6);
        preview.setOnMouseEntered(e -> preview.setStroke(Color.DARKGRAY));
        preview.setOnMouseExited(e -> preview.setStroke(Color.web("#777")));

        ColorPicker picker = new ColorPicker(initial);
        picker.setStyle("-fx-color-label-visible:false;-fx-background-radius:6;-fx-border-radius:6;");

        hexField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && newV.matches("^#[0-9A-F]{6}$")) {
                try {
                    Color c = Color.web(newV);
                    paintModel.setCurrentColor(c);
                    preview.setFill(c);
                    if (!picker.getValue().equals(c)) picker.setValue(c);
                    hexField.setStyle("-fx-border-color: transparent;");
                    paintPanel.requestRender();
                } catch (Exception ignored) {
                    hexField.setStyle("-fx-border-color:#ff8080;-fx-border-width:1;");
                }
            } else {
                // soft highlight while the code is incomplete/invalid
                hexField.setStyle("-fx-border-color:#ff8080;-fx-border-width:1;");
            }
        });

        picker.setOnAction(e -> {
            try {
                Color c = picker.getValue();
                if (c != null) {
                    paintModel.setCurrentColor(c);
                    preview.setFill(c);
                    String hx = String.format("#%02X%02X%02X",
                            (int)(c.getRed()*255),
                            (int)(c.getGreen()*255),
                            (int)(c.getBlue()*255));
                    if (!hx.equalsIgnoreCase(hexField.getText())) hexField.setText(hx);
                    paintPanel.requestRender();
                }
            } catch (Exception ignored) {}
        });

        Label fillLbl = new Label("\uD83D\uDD8C\uFE0F Fill");
        fillLbl.setStyle("-fx-font-weight:bold; -fx-text-fill:#333;");
        MenuButton fillBtn = new  MenuButton("Fill Style");
        fillBtn.setStyle("-fx-background-radius:6;-fx-border-radius:6;");
        fillBtn.setTooltip(new Tooltip("Fill Style"));

        MenuItem solidItem = new MenuItem("Solid");
        MenuItem outlineItem = new MenuItem("Outline");
        paintModel.setFilled(true);
        fillBtn.setText("Solid");

        // Actions
        solidItem.setOnAction(e -> {
            paintModel.setFilled(true);
            fillBtn.setText("Solid");
        });
        outlineItem.setOnAction(e -> {
            paintModel.setFilled(false);
            fillBtn.setText("Outline");
        });
        fillBtn.getItems().addAll(solidItem, outlineItem);


        HBox colorBar = new HBox(8, colorLbl, hexField, preview, picker, fillLbl, fillBtn);
        colorBar.setPadding(new Insets(6, 10, 6, 10));
        colorBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #fcfcfc, #f2f2f2);" +
                        "-fx-border-color: #d0d0d0; -fx-border-width: 0 0 1 0;"
        );

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

        // File
        Menu file = new Menu("File");
        MenuItem newItem  = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> System.out.println("Save (not implemented)"));
        file.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        file.getItems().add(exitItem);

        // Edit
        Menu edit = new Menu("Edit");
        MenuItem cutItem   = new MenuItem("Cut");
        MenuItem copyItem  = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem undoItem  = new MenuItem("Undo");
        MenuItem redoItem  = new MenuItem("Redo");
        MenuItem moveItem  = new MenuItem("Move");

        cutItem.setOnAction(e -> paintPanel.setStrategy(new CutStrategy(paintModel)));
        copyItem.setOnAction(e -> paintPanel.setStrategy(new CopyStrategy(paintModel)));
        pasteItem.setOnAction(e -> paintPanel.setStrategy(new PasteStrategy(paintModel, paintPanel)));
        undoItem.setOnAction(e -> paintModel.undo());
        redoItem.setOnAction(e -> paintModel.redo());
        moveItem.setOnAction(e -> {paintPanel.setStrategy(new MoverStrategy(paintModel, paintPanel));});

        edit.getItems().addAll(
                cutItem, copyItem, pasteItem,
                new SeparatorMenuItem(),
                undoItem, redoItem, moveItem
        );

        menuBar.getMenus().addAll(file, edit);
        return menuBar;
    }
}
