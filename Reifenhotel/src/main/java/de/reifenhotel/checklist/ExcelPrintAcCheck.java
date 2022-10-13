package de.reifenhotel.checklist;

import java.awt.*;
import java.io.File;

public class ExcelPrintAcCheck {

    public void createChecklist(String vin) throws Exception{

    }

    private void printChecklist() throws Exception {
        File file = new File("Klimacheck.xlsx");
        Desktop.getDesktop().print(file);
    }
}