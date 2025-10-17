package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;

        // BUG1.001 To do: change the buttons to shape icons by downloading images of each shape
        public ShapeChooserPanel(View view) {

                this.view = view;

                String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline" };

                int row = 0;
                for (String label : buttonLabels) {
                        Image icon = new Image("file:Assignment2/src/main/resources/icons/" + label+".png");
                        ImageView viewIcon =  new ImageView(icon);
                        viewIcon.setFitHeight(30);
                        viewIcon.setFitWidth(40);

                        Button button = new Button();
                        button.setGraphic(viewIcon);

                        button.setMinWidth(100);
                        this.add(button, 0, row);
                        row++;
                        button.setOnAction(this);
                }
        }

        @Override
        public void handle(ActionEvent event) {
                String command = ((Button) event.getSource()).getText();
                view.setMode(command);
                System.out.println(command);
        }
}


