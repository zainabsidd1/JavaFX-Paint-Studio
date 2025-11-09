package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import java.util.function.UnaryOperator;

public class StrokeControls {
    private PaintModel model;
    private HBox root = new HBox(10);
    private Slider tslider;
    private TextField field;

    public StrokeControls(PaintModel model) {
        this.model = model;

        // Text field
        field = new TextField(String.format("%.1f", model.getStrokeWidth()));
        field.setEditable(true);
        field.setPrefWidth(70);
        field.setAlignment(Pos.CENTER);
        field.setStyle("""
            -fx-background-color: #f2f2f2;
            -fx-border-color: #ccc;
            -fx-border-radius: 4;
            -fx-background-radius: 4;""");


        // TextFormatter to block > 20 while typing
        UnaryOperator<TextFormatter.Change> max20Filter = change -> {
            String nt = change.getControlNewText();
            if (nt.isEmpty()) return change; // Allow empty while editing
            if (!nt.matches("\\d{0,2}(?:\\.\\d?)?")) return null;  // 0-2 digits + optional 1 decimal
            try {
                double v = Double.parseDouble(nt);
                return (v > 20) ? null : change;
            } catch (NumberFormatException e) { return null; }
        };

        field.setTextFormatter(new TextFormatter<>(max20Filter));
        field.setText(fmt1(model.getStrokeWidth()));

        // Keep text synced when model changes
        model.addListener(() -> {
            double w = restrictW(model.getStrokeWidth());
            if (!field.isFocused()) field.setText(fmt1(w));
        });

        // Slider
        tslider = new Slider(1,20,model.getStrokeWidth());
        tslider.setShowTickLabels(true);
        tslider.setShowTickMarks(true);
        tslider.setMajorTickUnit(2);
        tslider.setBlockIncrement(5);
        tslider.setOnMousePressed(e -> model.setStrokePreviewWidth(tslider.getValue()));
        tslider.setOnMouseReleased(e -> model.hideStrokePreview());
        tslider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double w = restrictW(newValue.doubleValue());
            model.setStrokeWidth(w);
            model.setStrokePreviewWidth(w);
            if (!field.isFocused()) field.setText(fmt1(w));
        });

        // Text field : show/update preview while typing
        field.textProperty().addListener((o, oldText, newText) -> {
            try {
                double w = restrictW(Double.parseDouble(newText));
                model.setStrokePreviewWidth(w);         // radisu <= 10
            } catch (NumberFormatException ignored) { }
        });


        field.setOnAction(e -> {
            try {
                double w = restrictW(Double.parseDouble(field.getText()));
                model.setStrokeWidth(w);
                tslider.setValue(w);                 // keep slider in sync
                field.setText(fmt1(w));
            } catch (NumberFormatException ex) {
                field.setText(fmt1(model.getStrokeWidth()));
            }
            model.hideStrokePreview();
            if (field.getScene() != null) field.getScene().getRoot().requestFocus();
        });


        // Stop displaying circle when the textfield is released
        field.focusedProperty().addListener((observer, wasFocused, isFocused) -> {
            if (!isFocused) model.hideStrokePreview();
        });

        root.getChildren().addAll(tslider, field);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(5, 10, 5, 10));
    }
    public Node getNode() { return root; }
    public Slider getSlider() { return tslider; }
    public TextField getField() { return field; }
    private static double restrictW(double w) { return Math.max(1.0, Math.min(20.0, w)); }
    private static String fmt1(double v) { return String.format("%.1f", v); }

}
