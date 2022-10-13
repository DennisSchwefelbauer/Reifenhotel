package de.reifenhotel.output;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PrintWheels {

    private static final PrintWheels instance = new PrintWheels();

    private PrintWheels() {
        System.out.println("Instance PrintWheels gebildet...");
    }

    public static PrintWheels getInstance() {
        return instance;
    }

    public void createProtocol(String name, String surname, String location, String manufacturer, String plate, String model, String vin, int width, int height, int diameter, int loadIndex, String speedIndex, String tyreManufacturer, String tyreModel, String season, int dot, String rim, String caps, String bolts, double fl, double rl, double fr, double rr, String damage1, String damage2, String date) throws Exception {
        Workbook workbook = new XSSFWorkbook(new FileInputStream("Protocol.xlsx"));
        Sheet sheetBeleg = workbook.getSheetAt(0);

        Row rowName = sheetBeleg.getRow(11);
        Cell cellName = rowName.getCell(0);
        cellName.setCellValue(name + " " + surname);

        Row rowLocation = sheetBeleg.getRow(11);
        Cell cellLocation = rowLocation.getCell(10);
        cellLocation.setCellValue(location);

        Row rowManufacturer = sheetBeleg.getRow(15);
        Cell cellManufacturer = rowManufacturer.getCell(2);
        cellManufacturer.setCellValue(manufacturer);

        Row rowPlate = sheetBeleg.getRow(15);
        Cell cellPlate = rowPlate.getCell(10);
        cellPlate.setCellValue(plate);

        Row rowModel = sheetBeleg.getRow(17);
        Cell cellModel = rowModel.getCell(2);
        cellModel.setCellValue(model);

        Row rowVin = sheetBeleg.getRow(17);
        Cell cellVin = rowVin.getCell(10);
        cellVin.setCellValue(vin);

        Row rowWidth = sheetBeleg.getRow(22);
        Cell cellWidth = rowWidth.getCell(1);
        cellWidth.setCellValue(width);

        Row rowHeight = sheetBeleg.getRow(22);
        Cell cellHeight = rowHeight.getCell(3);
        cellHeight.setCellValue(height);

        Row rowDiameter = sheetBeleg.getRow(22);
        Cell cellDiameter = rowDiameter.getCell(5);
        cellDiameter.setCellValue(diameter);

        Row rowLoadIndex = sheetBeleg.getRow(22);
        Cell cellLoadIndex = rowLoadIndex.getCell(6);
        cellLoadIndex.setCellValue(loadIndex);

        Row rowSpeedIndex = sheetBeleg.getRow(22);
        Cell cellSpeedIndex = rowSpeedIndex.getCell(7);
        cellSpeedIndex.setCellValue(speedIndex);

        Row rowTyreModel = sheetBeleg.getRow(22);
        Cell cellTyreModel = rowTyreModel.getCell(10);
        cellTyreModel.setCellValue(tyreModel);

        Row rowTyreManufacturer = sheetBeleg.getRow(24);
        Cell cellTyreManufacturer = rowTyreManufacturer.getCell(1);
        cellTyreManufacturer.setCellValue(tyreManufacturer);

        Row rowSeason = sheetBeleg.getRow(24);
        Cell cellSeason = rowSeason.getCell(10);
        cellSeason.setCellValue(season);

        Row rowDot = sheetBeleg.getRow(26);
        Cell cellDot = rowDot.getCell(1);
        cellDot.setCellValue(dot);

        Row rowRim = sheetBeleg.getRow(31);
        Cell cellRim = rowRim.getCell(3);
        cellRim.setCellValue(rim);

        Row rowCaps = sheetBeleg.getRow(33);
        Cell cellCaps = rowCaps.getCell(5);
        cellCaps.setCellValue(caps);

        Row rowBolts = sheetBeleg.getRow(35);
        Cell cellBolts = rowBolts.getCell(5);
        cellBolts.setCellValue(bolts);

        Row rowFl = sheetBeleg.getRow(29);
        Cell cellFl = rowFl.getCell(9);
        cellFl.setCellValue(fl);

        Row rowRl = sheetBeleg.getRow(35);
        Cell cellRl = rowRl.getCell(9);
        cellRl.setCellValue(rl);

        Row rowFr = sheetBeleg.getRow(29);
        Cell cellFr = rowFr.getCell(12);
        cellFr.setCellValue(fr);

        Row rowRr = sheetBeleg.getRow(35);
        Cell cellRr = rowRr.getCell(12);
        cellRr.setCellValue(rr);

        if (damage1.equals("")) {
            Row rowDamage1 = sheetBeleg.getRow(39);
            Cell cellDamage1 = rowDamage1.getCell(0);
            cellDamage1.setCellValue("");
        } else {
            Row rowDamage1 = sheetBeleg.getRow(39);
            Cell cellDamage1 = rowDamage1.getCell(0);
            cellDamage1.setCellValue(damage1);
        }

        if (damage2.equals("")) {
            Row rowDamage2 = sheetBeleg.getRow(41);
            Cell cellDamage2 = rowDamage2.getCell(0);
            cellDamage2.setCellValue("");
        } else {
            Row rowDamage2 = sheetBeleg.getRow(41);
            Cell cellDamage2 = rowDamage2.getCell(0);
            cellDamage2.setCellValue(damage2);
        }

        Row rowDate = sheetBeleg.getRow(45);
        Cell cellDate = rowDate.getCell(0);
        cellDate.setCellValue(date);


        FileOutputStream fileOut = new FileOutputStream("Protocol.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public void createContainer(String name, String surname, String location, String manufacturer, String plate, String model, String vin, int width, int height, int diameter, int loadIndex, String speedIndex, String tyreManufacturer, int dot, String rim, String caps, String bolts, double fl, double rl, double fr, double rr) throws Exception {
        Workbook workbook = new XSSFWorkbook(new FileInputStream("Container.xlsx"));
        Sheet sheetBeleg = workbook.getSheetAt(0);

        Row rowName = sheetBeleg.getRow(0);
        Cell cellName = rowName.getCell(1);
        cellName.setCellValue(name + " " + surname);

        Row rowLocation = sheetBeleg.getRow(2);
        Cell cellLocation = rowLocation.getCell(1);
        cellLocation.setCellValue(location);

        Row rowManufacturer = sheetBeleg.getRow(4);
        Cell cellManufacturer = rowManufacturer.getCell(1);
        cellManufacturer.setCellValue(manufacturer);

        Row rowPlate = sheetBeleg.getRow(2);
        Cell cellPlate = rowPlate.getCell(8);
        cellPlate.setCellValue(plate);

        Row rowModel = sheetBeleg.getRow(4);
        Cell cellModel = rowModel.getCell(8);
        cellModel.setCellValue(model);

        Row rowVin = sheetBeleg.getRow(6);
        Cell cellVin = rowVin.getCell(1);
        cellVin.setCellValue(vin);

        Row rowWidth = sheetBeleg.getRow(10);
        Cell cellWidth = rowWidth.getCell(1);
        cellWidth.setCellValue(width);

        Row rowHeight = sheetBeleg.getRow(10);
        Cell cellHeight = rowHeight.getCell(3);
        cellHeight.setCellValue(height);

        Row rowDiameter = sheetBeleg.getRow(10);
        Cell cellDiameter = rowDiameter.getCell(5);
        cellDiameter.setCellValue(diameter);

        Row rowLoadIndex = sheetBeleg.getRow(10);
        Cell cellLoadIndex = rowLoadIndex.getCell(6);
        cellLoadIndex.setCellValue(loadIndex);

        Row rowSpeedIndex = sheetBeleg.getRow(10);
        Cell cellSpeedIndex = rowSpeedIndex.getCell(7);
        cellSpeedIndex.setCellValue(speedIndex);

        Row rowTyreManufacturer = sheetBeleg.getRow(8);
        Cell cellTyreManufacturer = rowTyreManufacturer.getCell(2);
        cellTyreManufacturer.setCellValue(tyreManufacturer);

        Row rowDot = sheetBeleg.getRow(12);
        Cell cellDot = rowDot.getCell(8);
        cellDot.setCellValue(dot);

        Row rowRim = sheetBeleg.getRow(12);
        Cell cellRim = rowRim.getCell(1);
        cellRim.setCellValue(rim);

        Row rowCaps = sheetBeleg.getRow(14);
        Cell cellCaps = rowCaps.getCell(1);
        cellCaps.setCellValue(caps);

        Row rowBolts = sheetBeleg.getRow(14);
        Cell cellBolts = rowBolts.getCell(8);
        cellBolts.setCellValue(bolts);

        Row rowFl = sheetBeleg.getRow(16);
        Cell cellFl = rowFl.getCell(2);
        cellFl.setCellValue(fl);

        Row rowRl = sheetBeleg.getRow(17);
        Cell cellRl = rowRl.getCell(2);
        cellRl.setCellValue(rl);

        Row rowFr = sheetBeleg.getRow(16);
        Cell cellFr = rowFr.getCell(6);
        cellFr.setCellValue(fr);

        Row rowRr = sheetBeleg.getRow(17);
        Cell cellRr = rowRr.getCell(6);
        cellRr.setCellValue(rr);

        FileOutputStream fileOut = new FileOutputStream("Container.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
