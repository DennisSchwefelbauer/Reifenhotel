package de.reifenhotel.checklist;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelPrintCheck {

    public void createChecklist(String checklist, String vin) throws Exception{
        Workbook workbook = new XSSFWorkbook(new FileInputStream("Checkliste.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);

        Row rowChecklist = sheet.getRow(5);
        Cell cellChecklist = rowChecklist.getCell(0);

        cellChecklist.setCellValue(checklist);

        Row rowVin = sheet.getRow(7);
        Cell cellVin = rowVin.getCell(3);

        if (!vin.isEmpty()){
            cellVin.setCellValue(vin);
        }

        FileOutputStream fileOut = new FileOutputStream("Checkliste.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

//        printChecklist();
    }

    private void printChecklist() throws Exception {
        File file = new File("Checkliste.xlsx");
        Desktop.getDesktop().print(file);
    }

}
