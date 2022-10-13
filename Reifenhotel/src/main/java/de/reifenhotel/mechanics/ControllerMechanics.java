package de.reifenhotel.mechanics;

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

public class ControllerMechanics implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField inputName;

    @FXML
    private TableView<Mechanics> tableViewMechanics;

    Database database = Database.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonDelete.setDisable(true);
        getMechanics();
    }

    public void getMechanics() {
        ArrayList<Mechanics> mList = database.getmList();
        mList.clear();

        try {
            database.getMechanics();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableViewMechanics.getColumns().clear();

        TableColumn<Mechanics, String> columnName = new TableColumn<>("Mechaniker");
        columnName.setMinWidth(227);
        columnName.setCellValueFactory(new PropertyValueFactory<>("mechanic"));

        tableViewMechanics.setItems(setMechanics());
        tableViewMechanics.getColumns().add(columnName);
        tableViewMechanics.refresh();

        tableViewMechanics.setRowFactory(tv -> {
            TableRow<Mechanics> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        inputName.setText(row.getItem().getMechanic());
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
    void buttonSave_Clicked(ActionEvent event) {
        if (!inputName.getText().isEmpty()) {
            try {
                database.newMechanic(inputName.getText());
                getMechanics();
                inputName.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            PopupWindowError.display("Feld darf nicht leer sein!");
        }
    }

    @FXML
    void buttonDelete_Clicked(ActionEvent event) {
        try {
            database.deleteMechanic(inputName.getText());
            getMechanics();
            inputName.clear();
            buttonDelete.setDisable(true);
            buttonSave.setDisable(false);
            inputName.setDisable(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<Mechanics> setMechanics() {
        ObservableList<Mechanics> mechanics = FXCollections.observableArrayList();
        ArrayList<Mechanics> mList = database.getmList();
        for (int i = 0; i < mList.size(); i++) {
            String mechanic = mList.get(i).getMechanic();
            mechanics.add(new Mechanics(mechanic));
        }
        return mechanics;
    }

}
