package de.reifenhotel.main;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.export.Export;
import de.reifenhotel.vehicle.Vehicle;
import de.reifenhotel.wheels.Wheels;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private Button buttonNewVehicle;

    @FXML
    private Button buttonNewWheels;

    @FXML
    private Button buttonDeleteWheels;

    @FXML
    private Button buttonDeleteVehicle;

    @FXML
    private Button buttonDeleteCustomer;

    @FXML
    private Button buttonReset;

    @FXML
    private TextField inputSearch;

    @FXML
    private TableView<Customer> tableViewCustomer;

    @FXML
    private TableView<Vehicle> tableViewVehicle;

    @FXML
    private TableView<Wheels> tableViewWheels;

    @FXML
    private CheckBox checkDeleteVehicle;

    @FXML
    private CheckBox checkDeleteWheels;

    @FXML
    private CheckBox checkDeleteCustomer;

    @FXML
    private ChoiceBox<String> searchBox;

    private static ControllerMain instance;

    private int cID;
    private int vID;
    private int wID;


    public ControllerMain() {
        super();
        synchronized (ControllerMain.class) {
            if (instance != null) throw new UnsupportedOperationException(
                    getClass() + " wurde mehr als einmal gebildet!");
            instance = this;
        }
    }

    public static ControllerMain getInstance() {
        return instance;
    }

    Database database = Database.getInstance();

    ArrayList<Customer> cList = database.getcList();
    ArrayList<Vehicle> vList = database.getvList();
    ArrayList<Wheels> wList = database.getwList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBox.getItems().addAll("Nachname", "Kennzeichen", "VIN");
        buttonReset.setDisable(true);

        database.createDatabase();

        reset();

        customerMethod();
    }


    @FXML
    void buttonSearch_Clicked(ActionEvent event) {
        if (searchBox.getValue() != null && !inputSearch.getText().isEmpty()) {
            buttonReset.setDisable(false);
            cList.clear();

            tableViewCustomer.getColumns().clear();
            tableViewVehicle.getColumns().clear();
            tableViewWheels.getColumns().clear();

            try {
                database.search(searchBox.getValue(), inputSearch.getText());

                TableColumn<Customer, String> columnName = new TableColumn<>("Name");
                columnName.setMinWidth(200);
                columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> columnSurname = new TableColumn<>("Nachname");
                columnSurname.setMinWidth(196);
                columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));

                tableViewCustomer.setItems(getCustomer());
                tableViewCustomer.getColumns().addAll(columnSurname, columnName);

                if (cList.size() == 1) {
                    vehicleMethod(cList.get(0).getId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            PopupWindowError.display("Suchbedingung und Suchfeld müssen ausgefüllt sein!");
        }
    }

    @FXML
    void buttonNewVehicle_Clicked(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/vehicle/vehicle.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Neues Fahrzeug");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void buttonNewWheels_Clicked(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/wheels/wheels.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Einlagerung");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void buttonDeleteCustomer_Clicked(ActionEvent event) {
        try {
            database.deleteCustomer(cID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerMethod();
        checkDeleteCustomer.setDisable(true);
        checkDeleteCustomer.setSelected(false);
        buttonDeleteCustomer.setDisable(true);
    }

    @FXML
    void buttonDeleteVehicle_Clicked(ActionEvent event) {
        try {
            database.deleteVehicle(vID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        vehicleMethod(cID);
        checkDeleteVehicle.setDisable(true);
        checkDeleteVehicle.setSelected(false);
        buttonDeleteVehicle.setDisable(true);
    }

    @FXML
    void buttonDeleteWheels_Clicked(ActionEvent event) {
        try {
            database.deleteWheels(wID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        wheelsMethod(vID);
        checkDeleteWheels.setDisable(true);
        checkDeleteWheels.setSelected(false);
        buttonDeleteWheels.setDisable(true);
    }

    @FXML
    void buttonNewCustomer_Clicked(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/customer/customer.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Neuer Kunde");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void buttonReset_Clicked(ActionEvent event) {
        reset();
    }

    @FXML
    void menuReset_Clicked(ActionEvent event) {
        reset();
    }

    public void reset() {
        tableViewWheels.getColumns().clear();
        tableViewVehicle.getColumns().clear();

        tableViewVehicle.setDisable(true);
        tableViewWheels.setDisable(true);

        buttonDeleteCustomer.setDisable(true);
        buttonDeleteVehicle.setDisable(true);
        buttonNewVehicle.setDisable(true);
        buttonDeleteWheels.setDisable(true);
        buttonNewWheels.setDisable(true);

        checkDeleteCustomer.setDisable(true);
        checkDeleteCustomer.setSelected(false);
        checkDeleteVehicle.setDisable(true);
        checkDeleteVehicle.setSelected(false);
        checkDeleteWheels.setDisable(true);
        checkDeleteWheels.setSelected(false);

        inputSearch.clear();

        cList.clear();
        try {
            database.getCustomer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerMethod();
        buttonReset.setDisable(true);
        searchBox.setValue("");
    }

    public void customerMethod() {
        tableViewCustomer.getColumns().clear();
        tableViewVehicle.getColumns().clear();
        tableViewWheels.getColumns().clear();
        cList.clear();
        try {
            database.getCustomer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TableColumn<Customer, String> columnName = new TableColumn<>("Name");
        columnName.setMinWidth(200);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> columnSurname = new TableColumn<>("Nachname");
        columnSurname.setMinWidth(196);
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));

        tableViewCustomer.setItems(getCustomer());
        tableViewCustomer.getColumns().addAll(columnSurname, columnName);
        tableViewCustomer.getSortOrder().add(columnSurname);

        tableViewCustomer.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        tableViewVehicle.getColumns().clear();
                        tableViewWheels.getColumns().clear();
                        tableViewWheels.setDisable(true);

                        buttonNewVehicle.setDisable(false);
                        buttonDeleteVehicle.setDisable(true);
                        buttonNewWheels.setDisable(true);

                        checkDeleteVehicle.setDisable(true);
                        checkDeleteWheels.setDisable(true);
                        checkDeleteCustomer.setDisable(false);

                        checkDeleteCustomer.selectedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                if (newValue) {
                                    buttonDeleteCustomer.setDisable(false);
                                } else {
                                    buttonDeleteCustomer.setDisable(true);
                                }
                            }
                        });

                        Customer rowData = row.getItem();
                        cID = row.getItem().getId();
                        database.setcID(cID);

                        vehicleMethod(cID);


                    }
                    if (event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                        database.setcID(row.getItem().getId());

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Stage stage = new Stage();
                        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/customer/customerEdit.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setTitle("Kunde");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    }
                }
            });
            return row;
        });
    }

    public void vehicleMethod(int cID) {
        tableViewVehicle.getColumns().clear();
        vList = database.getvList();
        vList.clear();

        try {
            database.getVehicle(cID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableViewVehicle.setDisable(false);

        TableColumn<Vehicle, String> columnManufacturer = new TableColumn<>("Hersteller");
        columnManufacturer.setMinWidth(100);
        columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn<Vehicle, String> columnModel = new TableColumn<>("Modell");
        columnModel.setMinWidth(100);
        columnModel.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, String> columnPlate = new TableColumn<>("Kennzeichen");
        columnPlate.setMinWidth(100);
        columnPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));

        TableColumn<Vehicle, String> columnVin = new TableColumn<>("Fahrgestellnummer");
        columnVin.setMinWidth(200);
        columnVin.setCellValueFactory(new PropertyValueFactory<>("vin"));

        tableViewVehicle.setItems(getVehicle(cID));
        tableViewVehicle.getColumns().addAll(columnManufacturer, columnModel, columnPlate, columnVin);

        tableViewVehicle.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Vehicle rowData = row.getItem();
                        vID = row.getItem().getVehicleId();
                        int whichVehicleTableArray = row.getIndex();
                        database.setvIDTA(whichVehicleTableArray);

                        wheelsMethod(vID);

                    }
                    if (event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                        int whichVehicleTableArray = row.getIndex();
                        vID = row.getItem().getVehicleId();
                        database.setvID(vID);
                        database.setvIDTA(whichVehicleTableArray);

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Stage stage = new Stage();
                        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/vehicle/vehicleEdit.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setTitle("Fahrzeug");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    }
                }
            });
            return row;
        });
    }

    public void wheelsMethod(int vID) {
        database.setvID(vID);

        wList = database.getwList();
        wList.clear();

        try {
            database.getWheels(vID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableViewWheels.setDisable(false);
        tableViewWheels.getColumns().clear();

        buttonDeleteCustomer.setDisable(true);
        buttonNewVehicle.setDisable(true);
        buttonNewWheels.setDisable(false);

        checkDeleteWheels.setDisable(true);
        checkDeleteVehicle.setDisable(false);
        checkDeleteVehicle.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    buttonDeleteVehicle.setDisable(false);
                } else {
                    buttonDeleteVehicle.setDisable(true);
                }
            }
        });

        TableColumn<Wheels, String> columnLocation = new TableColumn<>("Lagerort");
        columnLocation.setMinWidth(100);
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Wheels, String> columnWidth = new TableColumn<>("Breite");
        columnWidth.setMinWidth(100);
        columnWidth.setCellValueFactory(new PropertyValueFactory<>("width"));

        TableColumn<Wheels, String> columnHeight = new TableColumn<>("Höhe");
        columnHeight.setMinWidth(100);
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Wheels, String> columnDiameter = new TableColumn<>("Durchmesser");
        columnDiameter.setMinWidth(100);
        columnDiameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));

        TableColumn<Wheels, String> columnLoadIndex = new TableColumn<>("LoadIndex");
        columnLoadIndex.setMinWidth(100);
        columnLoadIndex.setCellValueFactory(new PropertyValueFactory<>("loadIndex"));

        TableColumn<Wheels, String> columnSpeedIndex = new TableColumn<>("SpeedIndex");
        columnSpeedIndex.setMinWidth(100);
        columnSpeedIndex.setCellValueFactory(new PropertyValueFactory<>("speedIndex"));

        TableColumn<Wheels, String> columnSeason = new TableColumn<>("Reifenart");
        columnSeason.setMinWidth(100);
        columnSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

        TableColumn<Wheels, String> columnDateIn = new TableColumn<>("Eingelagert");
        columnDateIn.setMinWidth(100);
        columnDateIn.setCellValueFactory(new PropertyValueFactory<>("dateIn"));

        TableColumn<Wheels, String> columnDateOut = new TableColumn<>("Ausgelagert");
        columnDateOut.setMinWidth(100);
        columnDateOut.setCellValueFactory(new PropertyValueFactory<>("dateOut"));

        tableViewWheels.setItems(getWheel(vID));
        tableViewWheels.getColumns().addAll(columnLocation, columnWidth, columnHeight, columnDiameter, columnLoadIndex, columnSpeedIndex, columnSeason, columnDateIn, columnDateOut);
        tableViewWheels.refresh();

        tableViewWheels.setRowFactory(tableViewWheels -> {
            TableRow<Wheels> row = new TableRow<Wheels>() {
                @Override
                public void updateItem(Wheels item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setStyle("");
                    } else if (item.getWheelStored() == 1) {
                        setStyle("-fx-background-color:#90EE90");
                    } else if (item.getWheelStored() == 0){
                        setStyle("-fx-background-color:#F08080");
                    }
                }
            };
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        wID = row.getItem().getWheelsId();
                        checkDeleteWheels.setDisable(false);
                        checkDeleteWheels.selectedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                if (newValue) {
                                    buttonDeleteWheels.setDisable(false);
                                } else {
                                    buttonDeleteWheels.setDisable(true);
                                }
                            }
                        });

                    }
                    if (event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                        wID = row.getItem().getWheelsId();
                        int whichWheelsTableArray = row.getIndex();
                        database.setwID(wID);
                        database.setwIDTA(whichWheelsTableArray);
                        try {
                            database.getWheels(vID);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Stage stage = new Stage();
                        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/wheels/wheelsEdit.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setTitle("Einlagerung");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    }
                }

            });
            return row;
        });
    }



    private ObservableList<Customer> getCustomer() {
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        cList = database.getcList();
        for (int i = 0; i < cList.size(); i++) {
            int id = cList.get(i).getId();
            String name = cList.get(i).getName();
            String surname = cList.get(i).getSurname();
            customer.add(new Customer(id, name, surname));
        }
        return customer;
    }

    private ObservableList<Vehicle> getVehicle(int whichCustomer) {
        ObservableList<Vehicle> vehicle = FXCollections.observableArrayList();
        vList = database.getvList();
        for (int i = 0; i < vList.size(); i++) {
            int id = vList.get(i).getCustomerId();
            int vId = vList.get(i).getVehicleId();
            String manufacturer = vList.get(i).getManufacturer();
            String model = vList.get(i).getModel();
            String plate = vList.get(i).getPlate();
            String vin = vList.get(i).getVin();

            vehicle.add(new Vehicle(id, vId, manufacturer, model, plate, vin));
        }
        return vehicle;
    }

    private ObservableList<Wheels> getWheel(int whichVehicle) {
        ObservableList<Wheels> wheel = FXCollections.observableArrayList();
        wList = database.getwList();
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

    @FXML
    void menuExit_Clicked(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void menuDamages_Clicked(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/damages.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Beschädigungen");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void menuMechanics_Clicked(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/mechanics.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Mechaniker");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void menuAbout_Clicked(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/about.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Über...");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void menuExport_Clicked(ActionEvent event) {
        Export export = new Export();
        try {
            export.writeExport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void menuFilterTire_Clicked(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/filterTire.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Reifen Filtern");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}


