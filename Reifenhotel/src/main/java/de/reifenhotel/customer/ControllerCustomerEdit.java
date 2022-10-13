package de.reifenhotel.customer;

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

public class ControllerCustomerEdit implements Initializable {

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputSurname;

    @FXML
    private Label labelName;

    @FXML
    private Label labelSurname;

    Database database = Database.getInstance();
    ControllerMain main = ControllerMain.getInstance();

    ArrayList<Customer> cList = database.getcList();
    int cID = database.getcID();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (cList.size() == 1) {
            if (cList.get(0).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(0).getSurname());
            } else {
                inputName.setText(cList.get(0).getName());
                inputSurname.setText(cList.get(0).getSurname());
            }
        } else if (cList.size() == 2) {
            if (cList.get(1).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(1).getSurname());
            } else {
                inputName.setText(cList.get(1).getName());
                inputSurname.setText(cList.get(1).getSurname());
            }
        }else if (cList.size() == 3) {
            if (cList.get(2).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(2).getSurname());
            } else {
                inputName.setText(cList.get(2).getName());
                inputSurname.setText(cList.get(2).getSurname());
            }
        }else if (cList.size() == 4) {
            if (cList.get(3).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(3).getSurname());
            } else {
                inputName.setText(cList.get(3).getName());
                inputSurname.setText(cList.get(3).getSurname());
            }
        }else if (cList.size() == 5) {
            if (cList.get(4).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(4).getSurname());
            } else {
                inputName.setText(cList.get(4).getName());
                inputSurname.setText(cList.get(4).getSurname());
            }
        }else if (cList.size() == 6) {
            if (cList.get(5).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(5).getSurname());
            } else {
                inputName.setText(cList.get(5).getName());
                inputSurname.setText(cList.get(5).getSurname());
            }
        }else {
            if (cList.get(cID - 1).getName().equals("Firma")) {
                labelName.setText("");
                inputName.setText("Firma");
                inputName.setDisable(true);
                labelSurname.setText("Name");
                inputSurname.setText(cList.get(cID - 1).getSurname());
            } else {
                inputName.setText(cList.get(cID - 1).getName());
                inputSurname.setText(cList.get(cID - 1).getSurname());
            }
        }
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonSave_Clicked(ActionEvent event) {
        if (!inputName.getText().isEmpty()
                && !inputSurname.getText().isEmpty()) {
            if (inputName.getText().equals(cList.get(cID - 1).getName()) && inputSurname.getText().equals(cList.get(cID - 1).getSurname())) {
                Button button = (Button) event.getSource();
                Stage primaryStage = (Stage) button.getScene().getWindow();
                primaryStage.close();
            } else {
                try {
                    database.editCustomer(cID, inputName.getText(), inputSurname.getText());
                    main.customerMethod();

                    Button button = (Button) event.getSource();
                    Stage primaryStage = (Stage) button.getScene().getWindow();
                    primaryStage.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }
    }
}