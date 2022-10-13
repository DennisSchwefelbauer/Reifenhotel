package de.reifenhotel.exceptionHandling;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupWindowError {
    public static void display(String message){
        Stage stage = new Stage();
        stage.setTitle("Info");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);

        Label label = new Label("Es ist ein Fehler aufgetreten!\n\n" + message);
        Button button = new Button("OK");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label,button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
