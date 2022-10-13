package de.reifenhotel.output;

import de.reifenhotel.exceptionHandling.PopupWindowError;
import de.reifenhotel.wheels.ControllerNewWheels;
import de.reifenhotel.wheels.ControllerWheelsEdit;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrintProtocol {

ControllerWheelsEdit cwe = ControllerWheelsEdit.getInstance();
ControllerNewWheels cnw = ControllerNewWheels.getInstance();

private static PrintProtocol instance;
    public PrintProtocol() {
        super();
        synchronized (PrintProtocol.class) {
            instance = this;
        }
    }

    public static PrintProtocol getInstance() {
        return instance;
    }

    @FXML
    private Label labelBolts;

    @FXML
    private Label labelCaps;

    @FXML
    private Label labelDamage1;

    @FXML
    private Label labelDamage2;

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
    private Label labelModel;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPlate;

    @FXML
    private Label labelRim;

    @FXML
    private Label labelRl;

    @FXML
    private Label labelRr;

    @FXML
    private Label labelSeason;

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

    public void createProtocolNewWheels() {
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
        labelSeason.setText(cnw.getSeason());
        labelDot.setText(cnw.getDot());
        labelRim.setText(cnw.getRim());
        labelCaps.setText(cnw.getCaps());
        labelBolts.setText(cnw.getBolts());
        labelFl.setText(cnw.getFl());
        labelRl.setText(cnw.getRl());
        labelFr.setText(cnw.getFr());
        labelRr.setText(cnw.getRr());
        if (cnw.getDamage1().isBlank()) {
            labelDamage1.setText("");
        }
        if (cnw.getDamage2().isBlank()) {
            labelDamage2.setText("");
        }
        labelDamage1.setText(cnw.getDamage1());
        labelDamage2.setText(cnw.getDamage2());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        LocalDate date = LocalDate.now();
        labelDate.setText(dtf.format(date));
    }

    public void createProtocolWheeldEdit() {
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
        labelSeason.setText(cwe.getSeason());
        labelDot.setText(cwe.getDot());
        labelRim.setText(cwe.getRim());
        labelCaps.setText(cwe.getCaps());
        labelBolts.setText(cwe.getBolts());
        labelFl.setText(cwe.getFl());
        labelRl.setText(cwe.getRl());
        labelFr.setText(cwe.getFr());
        labelRr.setText(cwe.getRr());
        if (cwe.getDamage1().isBlank()) {
            labelDamage1.setText("");
        }
        if (cwe.getDamage2().isBlank()) {
            labelDamage2.setText("");
        }
        labelDamage1.setText(cwe.getDamage1());
        labelDamage2.setText(cwe.getDamage2());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        LocalDate date = LocalDate.now();
        labelDate.setText(dtf.format(date));
        System.out.println(dtf.format(date));
    }

    public void printOut() {
        try {
            Printer printer = Printer.getDefaultPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                    PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            PrinterJob job = PrinterJob.createPrinterJob();

            if (labelDamage1.getText().equals("") && labelDamage2.getText().equals("")) {
                job.getJobSettings().setCopies(2);
            } else {
                job.getJobSettings().setCopies(3);
            }

            if (job != null && job.showPrintDialog(null)) {
                boolean success = job.printPage(pageLayout, pane);
                if (success) {
                    job.endJob();
                }
            }
        }catch (Exception e) {
            PopupWindowError.display(String.valueOf(e));

        }
    }
}
