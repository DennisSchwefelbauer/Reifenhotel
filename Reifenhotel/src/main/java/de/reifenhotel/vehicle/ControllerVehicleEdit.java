package de.reifenhotel.vehicle;

import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.main.ControllerMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerVehicleEdit implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField inputManufacturer;

    @FXML
    private TextField inputModel;

    @FXML
    private TextField inputPlate;

    @FXML
    private TextField inputVIN;

    Database database = Database.getInstance();

    ArrayList<Vehicle> vList = database.getvList();

    int vID = database.getvID();
    int vIDTA = database.getvIDTA();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputManufacturer.setText(vList.get(vIDTA).getManufacturer());
        inputModel.setText(vList.get(vIDTA).getModel());
        inputPlate.setText(vList.get(vIDTA).getPlate());
        inputVIN.setText(vList.get(vIDTA).getVin());
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonSave_Clicked(ActionEvent event) {
        if (!inputManufacturer.getText().isEmpty()
                && !inputModel.getText().isEmpty()
                && !inputPlate.getText().isEmpty()
                && !inputVIN.getText().isEmpty()) {
            if (inputManufacturer.getText().equals(vList.get(vIDTA).getManufacturer())
                    && inputModel.getText().equals(vList.get(vIDTA).getModel())
                    && inputPlate.getText().equals(vList.get(vIDTA).getPlate())
                    && inputVIN.getText().equals(vList.get(vIDTA).getVin())) {
                Button button = (Button) event.getSource();
                Stage primaryStage = (Stage) button.getScene().getWindow();
                primaryStage.close();
            } else {
                try {
                    database.editVehicle(vID, inputManufacturer.getText(), inputModel.getText(), inputPlate.getText(), inputVIN.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            Button button = (Button) event.getSource();
            Stage primaryStage = (Stage) button.getScene().getWindow();
            primaryStage.close();

            ControllerMain main = ControllerMain.getInstance();
            main.vehicleMethod(database.getcID());
        } else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }
    }
}
