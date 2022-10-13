package de.reifenhotel.filterTire;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.damages.Damages;
import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.vehicle.Vehicle;
import de.reifenhotel.wheels.Wheels;
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

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerFilterTire implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonSearch;

    @FXML
    private ChoiceBox<String> choiceBoxSeason;

    @FXML
    private TextField inputDepth;

    @FXML
    private Label labelManufacturer;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPlate;

    @FXML
    private Label labelSurname;

    @FXML
    private Label labelVIN;

    @FXML
    private TableView<Wheels> tableViewWheels;

    Database database = Database.getInstance();

    ArrayList<Customer> cList = database.getcList();
    ArrayList<Vehicle> vList = database.getvList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelName.setText("");
        labelSurname.setText("");
        labelManufacturer.setText("");
        labelModel.setText("");
        labelVIN.setText("");
        labelPlate.setText("");

        choiceBoxSeason.getItems().addAll("Sommer", "Winter", "Allwetter");
        choiceBoxSeason.setValue("Winter");
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void buttonSearch_Clicked(ActionEvent event) {
        if (!inputDepth.getText().isEmpty()) {
            ArrayList<Wheels> wList = database.getwList();
            wList.clear(); //TODO prüfen ob das was crasht

            try {
                database.getWheelsFiltered(Double.parseDouble(inputDepth.getText().replaceAll(",", ".")), choiceBoxSeason.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tableViewWheels.getColumns().clear();

            TableColumn<Wheels, String> columnLocation = new TableColumn<>("Lagerort");
            columnLocation.setMinWidth(70);
            columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

            TableColumn<Wheels, String> columnWidth = new TableColumn<>("Breite");
            columnWidth.setMinWidth(50);
            columnWidth.setCellValueFactory(new PropertyValueFactory<>("width"));

            TableColumn<Wheels, String> columnHeight = new TableColumn<>("Höhe");
            columnHeight.setMinWidth(50);
            columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));

            TableColumn<Wheels, String> columnDiameter = new TableColumn<>("Durchmesser");
            columnDiameter.setMinWidth(50);
            columnDiameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));

            TableColumn<Wheels, String> columnLoadIndex = new TableColumn<>("LoadIndex");
            columnLoadIndex.setMinWidth(50);
            columnLoadIndex.setCellValueFactory(new PropertyValueFactory<>("loadIndex"));

            TableColumn<Wheels, String> columnSpeedIndex = new TableColumn<>("SpeedIndex");
            columnSpeedIndex.setMinWidth(50);
            columnSpeedIndex.setCellValueFactory(new PropertyValueFactory<>("speedIndex"));

            TableColumn<Wheels, String> columnSeason = new TableColumn<>("Reifenart");
            columnSeason.setMinWidth(70);
            columnSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

            TableColumn<Wheels, String> columnFl = new TableColumn<>("VL");
            columnFl.setMinWidth(40);
            columnFl.setCellValueFactory(new PropertyValueFactory<>("fl"));

            TableColumn<Wheels, String> columnRl = new TableColumn<>("HL");
            columnRl.setMinWidth(40);
            columnRl.setCellValueFactory(new PropertyValueFactory<>("rl"));

            TableColumn<Wheels, String> columnFr = new TableColumn<>("VR");
            columnFr.setMinWidth(40);
            columnFr.setCellValueFactory(new PropertyValueFactory<>("fr"));

            TableColumn<Wheels, String> columnRr = new TableColumn<>("HR");
            columnRr.setMinWidth(40);
            columnRr.setCellValueFactory(new PropertyValueFactory<>("rr"));

            TableColumn<Wheels, String> columnTyreManufacturer = new TableColumn<>("Hersteller");
            columnTyreManufacturer.setMinWidth(100);
            columnTyreManufacturer.setCellValueFactory(new PropertyValueFactory<>("tyreManufacturer"));

            TableColumn<Wheels, String> columnModel = new TableColumn<>("Modell");
            columnModel.setMinWidth(100);
            columnModel.setCellValueFactory(new PropertyValueFactory<>("tyreModel"));

            tableViewWheels.setItems(getWheel());
            tableViewWheels.getColumns().addAll(columnLocation, columnWidth, columnHeight, columnDiameter, columnLoadIndex, columnSpeedIndex, columnSeason, columnFl, columnRl, columnFr, columnRr, columnTyreManufacturer, columnModel);

            tableViewWheels.setRowFactory(tv -> {
                TableRow<Wheels> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && (!row.isEmpty())) {

                            int cID = row.getItem().getCustomerId();
                            int vID = row.getItem().getVehicleId();
                            System.out.println("ControllerFilterTire   ---   cID = " + cID + " vID = " + vID );

//                            ArrayList<Customer> cList = database.getcList();
//                            ArrayList<Vehicle> vList = database.getvList();

                            try {
                                database.getCustomerFiltered(cID);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            labelName.setText(cList.get(0).getName());
                            labelSurname.setText(cList.get(0).getSurname());
                            cList.clear();

                            try {
                                database.getVehicleFitlered(vID);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            labelManufacturer.setText(vList.get(0).getManufacturer());
                            labelModel.setText(vList.get(0).getModel());
                            labelPlate.setText(vList.get(0).getPlate());
                            labelVIN.setText(vList.get(0).getVin());
                            vList.clear();


                        }
                    }

                });
                return row;
            });

        } else {
            PopupWindowError.display("Feld Profiltiefe muss ausgefüllt sein");
        }
    }

    private ObservableList<Wheels> getWheel() {
        ObservableList<Wheels> wheel = FXCollections.observableArrayList();
        ArrayList<Wheels> wList = database.getwList();
        for (int i = 0; i < wList.size(); i++) {
            int customerId = wList.get(i).getCustomerId();
            int vehicleId = wList.get(i).getVehicleId();
            int wheelsId = wList.get(i).getWheelsId();
            String location = wList.get(i).getLocation();
            String width = wList.get(i).getWidth();
            String height = wList.get(i).getHeight();
            String diameter = wList.get(i).getDiameter();
            String loadIndex = wList.get(i).getLoadIndex();
            String speedIndex = wList.get(i).getSpeedIndex();
            String season = wList.get(i).getSeason();
            String dot = wList.get(i).getDot();
            double fl = wList.get(i).getFl();
            double rl = wList.get(i).getRl();
            double fr = wList.get(i).getFr();
            double rr = wList.get(i).getRr();
            String tyreManufacturer = wList.get(i).getTyreManufacturer();
            String tyreModel = wList.get(i).getTyreModel();
            String rim = wList.get(i).getRim();
            String bolts = wList.get(i).getBolts();
            String caps = wList.get(i).getCaps();
            LocalDate dateIn = wList.get(i).getDateIn();
            LocalDate dateOut = wList.get(i).getDateOut();
            String damage1 = wList.get(i).getDamage1();
            String damage2 = wList.get(i).getDamage2();
            String user = wList.get(i).getUser();
            int wheelStored = wList.get(i).getWheelStored();

            wheel.add(new Wheels(customerId, vehicleId, wheelsId, location, width, height, diameter, loadIndex, speedIndex, season, dot, fl, rl, fr, rr, tyreManufacturer, tyreModel, rim, bolts, caps, dateIn, dateOut, damage1, damage2, user, wheelStored));
        }
        return wheel;
    }

}
