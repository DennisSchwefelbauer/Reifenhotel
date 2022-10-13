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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerWheelsEdit implements Initializable {

    private static ControllerWheelsEdit instance;
    public ControllerWheelsEdit() {
        super();
        synchronized (ControllerWheelsEdit.class) {
            instance = this;
        }
    }

    public static ControllerWheelsEdit getInstance() {
        return instance;
    }

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonPrint;

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
    ArrayList<Customer> cList = database.getcList();
    ArrayList<Vehicle> vList = database.getvList();
    ArrayList<Wheels> wList = database.getwList();

    int cID = database.getcID();
    int vID = database.getvID();
    int vIDTA = database.getvIDTA();
    int wID = database.getwID();
    int wIDTA = database.getwIDTA();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelName.setText(cList.get(cID - 1).getName());
        labelSurname.setText(cList.get(cID - 1).getSurname());
        labelManufacturer.setText(vList.get(vIDTA).getManufacturer());
        labelModel.setText(vList.get(vIDTA).getModel());
        labelPlate.setText(vList.get(vIDTA).getPlate());
        labelVIN.setText(vList.get(vIDTA).getVin());

        inputSeason.getItems().addAll("Sommer", "Winter", "Allwetter");
        inputRim.getItems().addAll("Stahl", "Alu Original", "Alu Zubehör", "lose Reifen");
        inputCaps.getItems().addAll("Ja", "Ja - in Fzg", "Nein");
        inputBolts.getItems().addAll("Ja", "Ja - in Fzg", "Nein");
        choiceboxInOut.getItems().addAll("Eingelagert", "Ausgelagert");
        inputDamage1.getItems().add("");
        inputDamage2.getItems().add("");

        buttonPrint.setDisable(true);

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

        inputLocation.setText(wList.get(wIDTA).getLocation());
        inputWidth.setText(String.valueOf(wList.get(wIDTA).getWidth()));
        inputHeight.setText(String.valueOf(wList.get(wIDTA).getHeight()));
        inputDiameter.setText(String.valueOf(wList.get(wIDTA).getDiameter()));
        inputLoadIndex.setText(String.valueOf(wList.get(wIDTA).getLoadIndex()));
        inputSpeedIndex.setText(wList.get(wIDTA).getSpeedIndex());
        inputSeason.setValue(wList.get(wIDTA).getSeason());
        inputDot.setText(String.valueOf(wList.get(wIDTA).getDot()));
        inputVL.setText(String.valueOf(wList.get(wIDTA).getFl()));
        inputVR.setText(String.valueOf(wList.get(wIDTA).getFr()));
        inputHL.setText(String.valueOf(wList.get(wIDTA).getRl()));
        inputHR.setText(String.valueOf(wList.get(wIDTA).getRr()));
        inputTyreManufacturer.setText(wList.get(wIDTA).getTyreManufacturer());
        inputTyreModel.setText(wList.get(wIDTA).getTyreModel());
        inputRim.setValue(wList.get(wIDTA).getRim());
        inputBolts.setValue(wList.get(wIDTA).getBolts());
        inputCaps.setValue(wList.get(wIDTA).getCaps());
        inputDamage1.setValue(wList.get(wIDTA).getDamage1());
        inputDamage2.setValue(wList.get(wIDTA).getDamage2());
        choiceboxUser.setValue(wList.get(wIDTA).getUser());
        switch (wList.get(wIDTA).getWheelStored()) {
            case 0:
                choiceboxInOut.setValue("Ausgelagert");
                break;
            case 1:
                choiceboxInOut.setValue("Eingelagert");
                break;
        }
    }

    @FXML
    void SaveButton_Clicked(ActionEvent event) {
        if (checkIfFilled()) {
            setDataWheels();
            if (choiceboxInOut.getValue().equals("Eingelagert")) {
                buttonPrint.setDisable(false);
            }

            saveButton.setDisable(true);
        } else {
            PopupWindowError.display("Alle Felder müssen ausgefüllt sein!");
        }
    }

    @FXML
    void buttonBack_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();
        ControllerMain main = ControllerMain.getInstance();
        main.wheelsMethod(vID);
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
                && !inputDamage1.getItems().isEmpty()
                && !inputDamage2.getItems().isEmpty()
                && !choiceboxUser.getItems().isEmpty()
                && !choiceboxInOut.getItems().isEmpty()) {
            isTrue = true;
        } else {
            isTrue = false;
        }
        return isTrue;
    }

    public void setDataWheels() {
        name = labelName.getText();
        surname = labelSurname.getText();
        String location = inputLocation.getText();
        String width = inputWidth.getText();
        String height = inputHeight.getText();
        String diameter = inputDiameter.getText();
        String loadIndex = inputLoadIndex.getText();
        char speedIndex = inputSpeedIndex.getText().charAt(0);

        String season = inputSeason.getValue();
        String dot = inputDot.getText();
        double fl = Double.parseDouble(inputVL.getText().replaceAll(",", "."));
        double rl = Double.parseDouble(inputHL.getText().replaceAll(",", "."));
        double fr = Double.parseDouble(inputVR.getText().replaceAll(",", "."));
        double rr = Double.parseDouble(inputHR.getText().replaceAll(",", "."));
        String tyreManufacturer = inputTyreManufacturer.getText();
        String tyreModel = inputTyreModel.getText();
        String rim = inputRim.getValue();
        String bolts = inputBolts.getValue();
        String caps = inputCaps.getValue();
        LocalDate dateIn;
        if (choiceboxInOut.getValue().equals("Eingelagert")) {
            dateIn = LocalDate.now();
        } else {
            dateIn = wList.get(wIDTA).getDateIn();
        }
        LocalDate dateOut;
        if (choiceboxInOut.getValue().equals("Ausgelagert")) {
            dateOut = LocalDate.now();
        } else {
            dateOut = wList.get(wIDTA).getDateOut();
        }
        String damage1 = inputDamage1.getValue();
        String damage2 = inputDamage2.getValue();
        String user = choiceboxUser.getValue();
        int wheelStored = 0;
        switch (choiceboxInOut.getValue()) {
            case "Eingelagert":
                wheelStored = 1;
                break;
            case "Ausgelagert":
                wheelStored = 0;
                break;
        }

        if (location.equals(wList.get(wIDTA).getLocation())
                && width == wList.get(wIDTA).getWidth()
                && height == wList.get(wIDTA).getHeight()
                && diameter == wList.get(wIDTA).getDiameter()
                && loadIndex == wList.get(wIDTA).getLoadIndex()
//                && speedIndex == wList.get(wIDTA).getLoadIndex()
                && season == wList.get(wIDTA).getSeason()
                && dot == wList.get(wIDTA).getDot()
                && fl == wList.get(wIDTA).getFl()
                && fr == wList.get(wIDTA).getFr()
                && rl == wList.get(wIDTA).getRl()
                && rr == wList.get(wIDTA).getRr()
                && tyreManufacturer.equals(wList.get(wIDTA).getTyreManufacturer())
                && tyreModel.equals(wList.get(wIDTA).getTyreModel())
                && rim == wList.get(wIDTA).getRim()
                && bolts == wList.get(wIDTA).getBolts()
                && caps == wList.get(wIDTA).getCaps()
                && damage1 == wList.get(wIDTA).getDamage1()
                && damage2 == wList.get(wIDTA).getDamage2()
                && choiceboxUser.getValue().equals(wList.get(wIDTA).getUser())
                && choiceboxInOut.getValue().equals(wList.get(wIDTA).getWheelStored())
        ) {
            //nix machen
            //TODO warum will die Speedindex abfrage ned
        } else {
            try {
                database.editWheels(wID, location, width, height, diameter, loadIndex, speedIndex, season, dot, fl, rl, fr, rr, tyreManufacturer, tyreModel, rim, bolts, caps, dateIn, dateOut, damage1, damage2, user, wheelStored);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        wList.clear();
    }

    @FXML
    void buttonPrint_Clicked(ActionEvent event) throws IOException {
        Stage stageProtocol = new Stage();
        FXMLLoader fxmlLoaderProtocol = new FXMLLoader();
        fxmlLoaderProtocol.setLocation(getClass().getResource("/de/reifenhotel/print/printProtocol.fxml"));
        Scene sceneProtocol = new Scene(fxmlLoaderProtocol.load());
        stageProtocol.setScene(sceneProtocol);

        PrintProtocol printProtocol = PrintProtocol.getInstance();
        printProtocol.createProtocolWheeldEdit();
        stageProtocol.showAndWait();
        printProtocol.printOut();

        Stage stageContainer = new Stage();
        FXMLLoader fxmlLoaderContainer = new FXMLLoader();
        fxmlLoaderContainer.setLocation(getClass().getResource("/de/reifenhotel/print/printContainer.fxml"));
        Scene sceneContainer = new Scene(fxmlLoaderContainer.load());
        stageContainer.setScene(sceneContainer);

        PrintContainer printContainer = PrintContainer.getInstance();
        printContainer.createContainerWheelEdit();
        printContainer.printOut();

//        stageContainer.showAndWait();

        buttonPrint.setDisable(true);
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
