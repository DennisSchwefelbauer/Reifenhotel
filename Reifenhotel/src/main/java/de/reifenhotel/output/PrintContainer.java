package de.reifenhotel.output;

import de.reifenhotel.wheels.ControllerNewWheels;
import de.reifenhotel.wheels.ControllerWheelsEdit;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrintContainer {

    ControllerWheelsEdit cwe = ControllerWheelsEdit.getInstance();
    ControllerNewWheels cnw = ControllerNewWheels.getInstance();

    private static PrintContainer instance;

    public PrintContainer() {
        super();
        synchronized (PrintContainer.class) {
            instance = this;
        }
    }

    public static PrintContainer getInstance() {
        return instance;
    }

    @FXML
    private Label labelBolts;

    @FXML
    private Label labelCaps;

    @FXML
    private Label labelName;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelDiameter;

    @FXML
    private Label labelDot;

    @FXML
    private Label labelFl;

    @FXML
    private Label labelFr;

    @FXML
    private Label labelHeight;

    @FXML
    private Label labelLoadIndex;

    @FXML
    private Label labelLocation;

    @FXML
    private Label labelManufacturer;

    @FXML
    private Label labelMechanic;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelPlate;

    @FXML
    private Label labelRim;

    @FXML
    private Label labelRl;

    @FXML
    private Label labelRr;

    @FXML
    private Label labelSpeedIndex;

    @FXML
    private Label labelTyreManufacturer;

    @FXML
    private Label labelTyreModel;

    @FXML
    private Label labelVin;

    @FXML
    private Label labelWidth;

    @FXML
    private AnchorPane pane;

    public void createContainerNewWheels() {
        labelName.setText(cnw.getName() + " " + cnw.getSurname());
        labelLocation.setText(cnw.getLocation());
        labelManufacturer.setText(cnw.getManufacturer());
        labelModel.setText(cnw.getModel());
        labelPlate.setText(cnw.getPlate());
        labelVin.setText(cnw.getVIN());
        labelWidth.setText(cnw.getWidth());
        labelHeight.setText(cnw.getHeight());
        labelDiameter.setText(cnw.getDiameter());
        labelLoadIndex.setText(cnw.getLoadIndex());
        labelSpeedIndex.setText(cnw.getSpeedIndex());
        labelTyreModel.setText(cnw.getTyreModel());
        labelTyreManufacturer.setText(cnw.getTyreManufacturer());
        labelDot.setText(cnw.getDot());
        labelRim.setText(cnw.getRim());
        labelCaps.setText(cnw.getCaps());
        labelBolts.setText(cnw.getBolts());
        labelFl.setText(cnw.getFl());
        labelRl.setText(cnw.getRl());
        labelFr.setText(cnw.getFr());
        labelRr.setText(cnw.getRr());
        LocalDate date = LocalDate.now();
        labelDate.setText((String.valueOf(date)));
        labelMechanic.setText(cnw.getMechanic());
    }

    public void createContainerWheelEdit() {
        labelName.setText(cwe.getName() + " " + cwe.getSurname());
        labelLocation.setText(cwe.getLocation());
        labelManufacturer.setText(cwe.getManufacturer());
        labelModel.setText(cwe.getModel());
        labelPlate.setText(cwe.getPlate());
        labelVin.setText(cwe.getVIN());
        labelWidth.setText(cwe.getWidth());
        labelHeight.setText(cwe.getHeight());
        labelDiameter.setText(cwe.getDiameter());
        labelLoadIndex.setText(cwe.getLoadIndex());
        labelSpeedIndex.setText(cwe.getSpeedIndex());
        labelTyreModel.setText(cwe.getTyreModel());
        labelTyreManufacturer.setText(cwe.getTyreManufacturer());
        labelDot.setText(cwe.getDot());
        labelRim.setText(cwe.getRim());
        labelCaps.setText(cwe.getCaps());
        labelBolts.setText(cwe.getBolts());
        labelFl.setText(cwe.getFl());
        labelRl.setText(cwe.getRl());
        labelFr.setText(cwe.getFr());
        labelRr.setText(cwe.getRr());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        LocalDate date = LocalDate.now();
        labelDate.setText(dtf.format(date));
        labelMechanic.setText(cwe.getMechanic());
    }


    public void printOut() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            boolean success = job.printPage(pageLayout, pane);
            if (success) {
                job.endJob();
            }
        }
    }

}
