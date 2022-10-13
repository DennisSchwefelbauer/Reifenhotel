package de.reifenhotel.wheels;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.damages.Damages;
import de.reifenhotel.database.Database;
import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.main.ControllerMain;
import de.reifenhotel.mechanics.Mechanics;
import de.reifenhotel.output.PrintContainer;
import de.reifenhotel.output.PrintProtocol;
import de.reifenhotel.vehicle.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerNewWheels implements Initializable {

    private static ControllerNewWheels instance;
    public ControllerNewWheels() {
        super();
        synchronized (ControllerNewWheels.class) {
            instance = this;
        }
    }

    public static ControllerNewWheels getInstance() {
        return instance;
    }
    @FXML
    private Button buttonBack;

    @FXML
    private TextField inputDot;

    @FXML
    private ChoiceBox<String> choiceboxUser;

    @FXML
    private ChoiceBox<String> inputBolts;

    @FXML
    private ChoiceBox<String> choiceboxInOut;

    @FXML
    private ChoiceBox<String> inputCaps;

    @FXML
    private ChoiceBox<String> inputDamage1;

    @FXML
    private ChoiceBox<String> inputDamage2;

    @FXML
    private TextField inputDiameter;

    @FXML
    private TextField inputHL;

    @FXML
    private TextField inputHR;

    @FXML
    private TextField inputHeight;

    @FXML
    private TextField inputLocation;

    @FXML
    private TextField inputLoadIndex;

    @FXML
    private ChoiceBox<String> inputRim;

    @FXML
    private ChoiceBox<String> inputSeason;

    @FXML
    private TextField inputSpeedIndex;

    @FXML
    private TextField inputTyreManufacturer;

    @FXML
    private TextField inputTyreModel;

    @FXML
    private TextField inputVL;

    @FXML
    private TextField inputVR;

    @FXML
    private TextField inputWidth;

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
    private Button saveButton;

    private String name;
    private String surname;

    Database database = Database.getInstance();
    ControllerMain main = ControllerMain.getInstance();

    ArrayList<Customer> cList = database.getcList();
    ArrayList<Vehicle> vList = database.getvList();

    int vID = database.getvID();
    int whichVehicleTableArray = database.getvIDTA();
    int cID = database.getcID();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            database.getCustomer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        labelName.setText(cList.get(cID - 1).getName());
        labelSurname.setText(cList.get(cID - 1).getSurname());
        labelManufacturer.setText(vList.get(whichVehicleTableArray).getManufacturer());
        labelModel.setText(vList.get(whichVehicleTableArray).getModel());
        labelPlate.setText(vList.get(whichVehicleTableArray).getPlate());
        labelVIN.setText(vList.get(whichVehicleTableArray).getVin());

        inputSeason.getItems().addAll("Sommer", "Winter", "Allwetter");
        inputRim.getItems().addAll("Stahl", "Alu Original", "Alu Zubehör", "lose Reifen");
        inputCaps.getItems().addAll("Ja", "Ja - in Fzg", "Nein");
        inputBolts.getItems().addAll("Ja", "Ja - in Fzg", "Nein");
        choiceboxInOut.getItems().addAll("Eingelagert", "Ausgelagert");
        inputDamage1.getItems().add("");
        inputDamage2.getItems().add("");
        inputDamage1.setValue("");
        inputDamage2.setValue("");

        try {
            ArrayList<Damages> dList = database.getdList();
            dList.clear();
            database.getDamages();
            for (int i = 0; i < dList.size(); i++) {
                inputDamage1.getItems().add(dList.get(i).getDamage());
                inputDamage2.getItems().add(dList.get(i).getDamage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ArrayList<Mechanics> mList = database.getmList();
            mList.clear();
            database.getMechanics();
            for (int i = 0; i < mList.size(); i++) {
                choiceboxUser.getItems().add(mList.get(i).getMechanic());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void SaveButton_Clicked(ActionEvent event) throws IOException {
        if (checkIfFilled()) {
            setDataWheels();
            main.wheelsMethod(vID);

            Stage stageProtocol = new Stage();
            FXMLLoader fxmlLoaderProtocol = new FXMLLoader();
            fxmlLoaderProtocol.setLocation(getClass().getResource("/de/reifenhotel/print/printProtocol.fxml"));
            Scene sceneProtocol = new Scene(fxmlLoaderProtocol.load());
            stageProtocol.setScene(sceneProtocol);

            PrintProtocol printProtocol = PrintProtocol.getInstance();
            printProtocol.createProtocolNewWheels();
            stageProtocol.showAndWait();
            printProtocol.printOut();

            Stage stageContainer = new Stage();
            FXMLLoader fxmlLoaderContainer = new FXMLLoader();
            fxmlLoaderContainer.setLocation(getClass().getResource("/de/reifenhotel/print/printContainer.fxml"));
            Scene sceneContainer = new Scene(fxmlLoaderContainer.load());
            stageContainer.setScene(sceneContainer);

            PrintContainer printContainer = PrintContainer.getInstance();
            printContainer.createContainerNewWheels();
            printContainer.printOut();

//            stageContainer.showAndWait();

            Button button = (Button) event.getSource();
            Stage primaryStage = (Stage) button.getScene().getWindow();
            primaryStage.close();
        } else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }

    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
    }

    private boolean checkIfFilled() {
        boolean isTrue;
        if (!inputLocation.getText().isEmpty()
                && !inputHeight.getText().isEmpty()
                && !inputWidth.getText().isEmpty()
                && !inputDiameter.getText().isEmpty()
                && !inputLoadIndex.getText().isEmpty()
                && !inputSpeedIndex.getText().isEmpty()
                && !inputSeason.getItems().isEmpty()
                && !inputDot.getText().isEmpty()
                && !inputVL.getText().isEmpty()
                && !inputVR.getText().isEmpty()
                && !inputHL.getText().isEmpty()
                && !inputHR.getText().isEmpty()
                && !inputTyreManufacturer.getText().isEmpty()
                && !inputTyreModel.getText().isEmpty()
                && !inputRim.getItems().isEmpty()
                && !inputBolts.getItems().isEmpty()
                && !inputCaps.getItems().isEmpty()
//                && !inputDamage1.getValue().isEmpty()
//                && !inputDamage2.getValue().isEmpty()
                && !choiceboxUser.getItems().isEmpty()) {
            isTrue = true;
        } else {
            isTrue = false;
        }
        return isTrue;
    }

    public void setDataWheels() {
        name = labelName.getText();
        surname = labelSurname.getText();
        LocalDate dateIn;
        LocalDate dateOut;
        int wheelStored = 0;
        switch (choiceboxInOut.getValue()) {
            case "Eingelagert":
                wheelStored = 1;
                break;
            case "Ausgelagert":
                wheelStored = 0;
                break;
        }

        try {

            database.writeWeheels(
                    cID,
                    vID,
                    inputLocation.getText(),
                    inputWidth.getText(),
                    inputHeight.getText(),
                    inputDiameter.getText(),
                    inputLoadIndex.getText(),
                    inputSpeedIndex.getText().charAt(0),
                    inputSeason.getValue(),
                    inputDot.getText(),
                    Double.parseDouble(inputVL.getText().replaceAll(",", ".")),
                    Double.parseDouble(inputHL.getText().replaceAll(",", ".")),
                    Double.parseDouble(inputVR.getText().replaceAll(",", ".")),
                    Double.parseDouble(inputHR.getText().replaceAll(",", ".")),
                    inputTyreManufacturer.getText(),
                    inputTyreModel.getText(),
                    inputRim.getValue(),
                    inputBolts.getValue(),
                    inputCaps.getValue(),
                    dateIn = LocalDate.now(),
                    dateOut = LocalDate.now(),
                    inputDamage1.getValue(),
                    inputDamage2.getValue(),
                    choiceboxUser.getValue(),
                    wheelStored);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLocation() {
        return inputLocation.getText();
    }

    public String getManufacturer() {
        return labelManufacturer.getText();
    }

    public String getModel() {
        return labelModel.getText();
    }

    public String getPlate() {
        return labelPlate.getText();
    }

    public String getVIN() {
        return labelVIN.getText();
    }

    public String getWidth() {
        return inputWidth.getText();
    }

    public String getHeight() {
        return inputHeight.getText();
    }

    public String getDiameter() {
        return inputDiameter.getText();
    }

    public String getLoadIndex() {
        return inputLoadIndex.getText();
    }

    public String getSpeedIndex() {
        return inputSpeedIndex.getText();
    }

    public String getDot() {
        return inputDot.getText();
    }

    public String getTyreManufacturer() {
        return inputTyreManufacturer.getText();
    }

    public String getTyreModel() {
        return inputTyreModel.getText();
    }

    public String getSeason() {
        return inputSeason.getValue();
    }

    public String getRim() {
        return inputRim.getValue();
    }

    public String getCaps() {
        return inputCaps.getValue();
    }

    public String getBolts() {
        return inputBolts.getValue();
    }

    public String getFl() {
        return inputVL.getText();
    }

    public String getRl() {
        return inputHL.getText();
    }

    public String getFr() {
        return inputVR.getText();
    }

    public String getRr() {
        return inputHR.getText();
    }

    public String getDamage1() {
        return inputDamage1.getValue();
    }

    public String getDamage2() {
        return inputDamage2.getValue();
    }

    public String getMechanic() {
        return choiceboxUser.getValue();
    }

}
