package de.reifenhotel.customer;

import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowBlank;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.main.ControllerMain;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerNewCustomer implements Initializable {

    @FXML
    private TextField inputManufacturer;

    @FXML
    private TextField inputModel;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputPlate;

    @FXML
    private TextField inputSurname;

    @FXML
    private TextField inputVIN;

    @FXML
    private CheckBox checkboxCompany;

    @FXML
    private Label labelName;

    @FXML
    private Label labelSurname;

    Database database = Database.getInstance();
    ControllerMain main = ControllerMain.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkboxCompany.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (checkboxCompany.isSelected()) {
                labelName.setText("");
                labelSurname.setText("Name");
                inputName.setText("Firma");
                inputName.setDisable(true);
            } else {
                labelName.setText("Vorname");
                labelSurname.setText("Nachname");
                inputName.clear();
                inputName.setDisable(false);
            }

        });
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonSave_Clicked(ActionEvent event) {
        String name = capitalize(inputName.getText());
        String surname = capitalize(inputSurname.getText());

        String manufacturer = capitalize(inputManufacturer.getText());
        String model = capitalize(inputModel.getText());
        String plate = inputPlate.getText().toUpperCase();
        String vin = inputVIN.getText().toUpperCase();

        if (!name.isEmpty() && !surname.isEmpty() && !manufacturer.isEmpty() && !model.isEmpty() && !plate.isEmpty() && !vin.isEmpty()) {
            try {
                database.newCustomer(name, surname);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            int cID = database.getcID();

            if (cID >= 1) {
                try {
                    database.newVehicle(cID, manufacturer, model, plate, vin);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                int vID = database.getvID();
                if (vID == 0) {
                    PopupWindowBlank.display("Fahrzeug existiert bereits!");
                } else {
                    main.customerMethod();
                    main.vehicleMethod(cID);

                    Button button = (Button) event.getSource();
                    Stage primaryStage = (Stage) button.getScene().getWindow();
                    primaryStage.close();
                }
            } else {
                PopupWindowBlank.display("Kunde existiert bereits! Bitte Fahrzeug bei Kunde hinzufügen!");
            }


        } else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
