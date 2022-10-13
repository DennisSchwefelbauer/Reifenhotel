package de.reifenhotel.vehicle;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.main.ControllerMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerNewVehicle implements Initializable {

    @FXML
    private TextField inputManufacturer;

    @FXML
    private TextField inputModel;

    @FXML
    private TextField inputPlate;

    @FXML
    private TextField inputVIN;

    @FXML
    private Label labelCustomer;

    Database database = Database.getInstance();
    ControllerMain main = ControllerMain.getInstance();

    ArrayList<Customer> cList = database.getcList();

    int cID = database.getcID();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            database.getCustomer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        labelCustomer.setText(cList.get(cID - 1).getName() + " " + cList.get(cID - 1).getSurname());
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonSave_Clicked(ActionEvent event) {
        if (!inputManufacturer.getText().isEmpty() && !inputModel.getText().isEmpty() && !inputPlate.getText().isEmpty() && !inputVIN.getText().isEmpty()) {
            try {
                database.newVehicle(cID, inputManufacturer.getText(), inputModel.getText(), inputPlate.getText(), inputVIN.getText());
                main.vehicleMethod(cID);

                Button button = (Button) event.getSource();
                Stage primaryStage = (Stage) button.getScene().getWindow();
                primaryStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }
    }
}
