package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;
        private Button thisButton;

        // BUG1.001 To do: change the buttons to shape icons by downloading images of each shape
        public ShapeChooserPanel(View view) {

                this.view = view;

                String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline" };

                int row = 0;
                for (String label : buttonLabels) {
                        Image icon = null;
                        // get resource stream to ensure file paths with slight deviations works
                        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/" + label + ".png")));
                        ImageView viewIcon = new ImageView(icon);
                        viewIcon.setFitWidth(24);
                        viewIcon.setFitHeight(24);
                        viewIcon.setPreserveRatio(true);
                        Button button = new Button();          // icon-only button
                        button.setGraphic(viewIcon);
                        // store the mode on the button (since getText() is empty with icons)
                        button.setUserData(label);
                        button.setMinWidth(100);
                        this.add(button, 0, row++);
                        button.setOnAction(this);
                }
        }

        @Override
        public void handle(ActionEvent event) {
                Button btn = (Button) event.getSource();
                // Read the mode from userData instead of getText()
                if (thisButton != null) {
                    thisButton.setStyle("");
                }
                // Style the selected button
                btn.setStyle("-fx-border-color: black;" +
                        " -fx-border-width: 2px;" +
                        "-fx-background-color:#FCF55F;");
                thisButton = btn;
                String command = (String) btn.getUserData();
                view.setMode(command);
                System.out.println(command);
        }
}


