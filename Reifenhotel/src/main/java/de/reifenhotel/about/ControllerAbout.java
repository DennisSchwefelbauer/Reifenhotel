package de.reifenhotel.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAbout implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private TextArea textfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textfield.setDisable(true);
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

}
