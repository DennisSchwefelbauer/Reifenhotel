package de.reifenhotel.damages;

import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerDamages implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField inputName;

    @FXML
    private TableView<Damages> tableViewDamages;

    Database database = Database.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonDelete.setDisable(true);
        getDamages();

    }

    public void getDamages() {
        ArrayList<Damages> dList = database.getdList();
        dList.clear();

        try {
            database.getDamages();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableViewDamages.getColumns().clear();

        TableColumn<Damages, String> columnDamage = new TableColumn<>("Schäden");
        columnDamage.setMinWidth(439);
        columnDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));

        tableViewDamages.setItems(setDamages());
        tableViewDamages.getColumns().add(columnDamage);
        tableViewDamages.refresh();

        tableViewDamages.setRowFactory(tv -> {
            TableRow<Damages> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        inputName.setText(row.getItem().getDamage());
                        inputName.setDisable(true);
                        buttonDelete.setDisable(false);
                        buttonSave.setDisable(true);

                    }
                }

            });
            return row;
        });
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonDelete_Clicked(ActionEvent event) {
        try {
            database.deleteDamages(inputName.getText());
            getDamages();
            inputName.clear();
            buttonDelete.setDisable(true);
            buttonSave.setDisable(false);
            inputName.setDisable(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void buttonSave_Clicked(ActionEvent event) {
        if (!inputName.getText().isEmpty()) {
            try {
                database.newDamage(inputName.getText());
                getDamages();
                inputName.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            PopupWindowError.display("Feld darf nicht leer sein!");
        }
    }

    private ObservableList<Damages> setDamages() {
        ObservableList<Damages> damages = FXCollections.observableArrayList();
        ArrayList<Damages> dList = database.getdList();
        for (int i = 0; i < dList.size(); i++) {
            String damage = dList.get(i).getDamage();
            damages.add(new Damages(damage));

        }
        return damages;
    }


}