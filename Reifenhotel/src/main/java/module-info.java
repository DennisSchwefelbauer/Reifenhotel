module de.reifenhotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens de.reifenhotel.main to java.base, javafx.fxml;
    opens de.reifenhotel.database to java.base, javafx.fxml;
    opens de.reifenhotel.customer to java.base, javafx.fxml;
    opens de.reifenhotel.vehicle to java.base, javafx.fxml;
    opens de.reifenhotel.checklist to java.base, javafx.fxml;
    opens de.reifenhotel.wheels to java.base, javafx.fxml;
    opens de.reifenhotel.exceptionHandling to java.base, javafx.fxml;
    opens de.reifenhotel.output to java.base, javafx.fxml;
    opens de.reifenhotel.damages to java.base, javafx.fxml;
    opens de.reifenhotel.mechanics to java.base, javafx.fxml;
    opens de.reifenhotel.about to java.base, javafx.fxml;
    opens de.reifenhotel.export to java.base, javafx.fxml;
    opens de.reifenhotel.filterTire to java.base, javafx.fxml;

    exports de.reifenhotel.main;
    exports de.reifenhotel.database;
    exports de.reifenhotel.customer;
    exports de.reifenhotel.vehicle;
    exports de.reifenhotel.wheels;
    exports de.reifenhotel.checklist;
    exports de.reifenhotel.output;
    exports de.reifenhotel.exceptionHandling;
    exports de.reifenhotel.damages;
    exports de.reifenhotel.mechanics;
    exports de.reifenhotel.export;
    exports de.reifenhotel.filterTire;
    exports de.reifenhotel.about;

}