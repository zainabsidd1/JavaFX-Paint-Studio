package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {

        private final PaintModel paintModel;
        private final PaintPanel paintPanel;

    public View(PaintModel model, Stage stage) {
                this.paintModel = model;
                this.paintPanel = new PaintPanel(this.paintModel);
        ShapeChooserPanel shapeChooserPanel = new ShapeChooserPanel(this);
                BorderPane root = new BorderPane();
                root.setTop(createMenuBar());
                root.setCenter(this.paintPanel);
                root.setLeft(shapeChooserPanel);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Paint");
                stage.show();
        }

        public PaintModel getPaintModel() { return this.paintModel; }
        public PaintPanel getPaintPanel() { return this.paintPanel; }

        // setMode(String) removed now handled via ShapeChooserPanel.

        private MenuBar createMenuBar() {
                MenuBar menuBar = new MenuBar();

                // File
                Menu file = new Menu("File");
                MenuItem newItem  = new MenuItem("New");
                MenuItem openItem = new MenuItem("Open");
                MenuItem saveItem = new MenuItem("Save");
                file.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem());

                MenuItem exitItem = new MenuItem("Exit");
                exitItem.setOnAction(e -> Platform.exit());
                file.getItems().add(exitItem);

                Menu edit = new Menu("Edit");
                MenuItem cutItem   = new MenuItem("Cut");
                MenuItem copyItem  = new MenuItem("Copy");
                MenuItem pasteItem = new MenuItem("Paste");
                MenuItem undoItem  = new MenuItem("Undo");
                MenuItem redoItem  = new MenuItem("Redo");

                // replace when implemented
                cutItem.setOnAction(e -> System.out.println("Cut (not implemented)"));
                copyItem.setOnAction(e -> System.out.println("Copy (not implemented)"));
                pasteItem.setOnAction(e -> System.out.println("Paste (not implemented)"));
                undoItem.setOnAction(e -> System.out.println("Undo (not implemented)"));
                redoItem.setOnAction(e -> System.out.println("Redo (not implemented)"));
                edit.getItems().addAll(cutItem, copyItem, pasteItem, new SeparatorMenuItem(),
                        undoItem, redoItem);

                menuBar.getMenus().addAll(file, edit);
                return menuBar;
        }
}
