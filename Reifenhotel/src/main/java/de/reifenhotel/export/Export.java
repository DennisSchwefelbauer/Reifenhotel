package de.reifenhotel.export;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.database.Database;
import de.reifenhotel.vehicle.Vehicle;
import de.reifenhotel.wheels.Wheels;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Export {
    Database database = Database.getInstance();
    ArrayList<Customer> cList;
    ArrayList<Vehicle> vList;
    ArrayList<Wheels> wList;

    public void getData() throws SQLException, IOException {
        cList = database.getcList();
        vList = database.getvList();
        wList = database.getwList();

        cList.clear();
        vList.clear();
        wList.clear();

        database.export();

        cList = database.getcList();
        vList = database.getvList();
        wList = database.getwList();
    }

    public void writeExport() throws IOException, SQLException {
        getData();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reifenhotel");


        for (int i = 0; i < wList.size(); i++) {
            Row row = CellUtil.getRow(i, sheet);
            CellUtil.createCell(row, 0, String.valueOf(wList.get(i).getCustomerId()));


//            CellUtil.createCell(row, 1, vList.get(i).getManufacturer());
//            CellUtil.createCell(row, 2, vList.get(i).getModel());
//            CellUtil.createCell(row, 3, vList.get(i).getVin());
//            CellUtil.createCell(row, 4, vList.get(i).getPlate());

            sheet.autoSizeColumn(i);

        }

        FileOutputStream fileOut = new FileOutputStream("Export_Reifenhotel.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        File file = new File("Export_Reifenhotel.xlsx");
        Desktop.getDesktop().open(file);
    }
}
