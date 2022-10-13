package de.reifenhotel.database;

import de.reifenhotel.customer.Customer;
import de.reifenhotel.damages.Damages;
import de.reifenhotel.exceptionHandling.PopupWindowBlank;
import de.reifenhotel.mechanics.Mechanics;
import de.reifenhotel.vehicle.Vehicle;
import de.reifenhotel.wheels.Wheels;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {

    public static final String DB_NAME = "Datenbank.db";
    public static final String DB_PATH = "";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_PATH + DB_NAME;

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    private int cID;
    private int vID;
    private int vIDTA;  //TableArray ID
    private int wID;
    private int wIDTA;  //TableArray ID
    ArrayList<Customer> cList = new ArrayList<>();
    ArrayList<Vehicle> vList = new ArrayList<>();
    ArrayList<Wheels> wList = new ArrayList<>();
    ArrayList<Mechanics> mList = new ArrayList<>();
    ArrayList<Damages> dList = new ArrayList<>();

    // TABLES
    //------------------------------------------------------------------------------------------------------------------
    private static final String TABLE_CUSTOMER = "customer";
    private static final String COLUMN_CUSTOMERID = "customerId";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    //------------------------------------------------------------------------------------------------------------------
    private static final String TABLE_VEHIClE = "vehicle";
    private static final String COLUMN_VEHICLEID = "vehicleId";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_PLATE = "plate";
    private static final String COLUMN_VIN = "vin";
    //------------------------------------------------------------------------------------------------------------------
    private static final String TABLE_WHEELS = "wheels";
    private static final String COLUMN_WHEELID = "wheelId";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_WIDTH = "width";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_DIAMETER = "diameter";
    private static final String COLUMN_LOADINDEX = "loadIndex";
    private static final String COLUMN_SPEEDINDEX = "speedIndex";
    private static final String COLUMN_SEASON = "season";
    private static final String COLUMN_DOT = "dot";
    private static final String COLUMN_FL = "fl";
    private static final String COLUMN_RL = "rl";
    private static final String COLUMN_FR = "fr";
    private static final String COLUMN_RR = "rr";
    private static final String COLUMN_TYREMANUFACTURER = "tyreManufacturer";
    private static final String COLUMN_TYREMODEL = "tyreModel";
    private static final String COLUMN_RIM = "rim";
    private static final String COLUMN_BOLTS = "bolts";
    private static final String COLUMN_CAPS = "caps";
    private static final String COLUMN_DATEIN = "dateIn";
    private static final String COLUMN_DATEOUT = "dateOut";
    private static final String COLUMN_DAMAGE1 = "damage1";
    private static final String COLUMN_DAMAGE2 = "damage2";
    private static final String COLUMN_WHODIDIT = "whoDidIt";
    private static final String COLUMN_WHEELSTORED = "wheelStored";
    //------------------------------------------------------------------------------------------------------------------
    private static final String TABLE_MECHANICS = "mechanics";
    private static final String COLUMN_MECHANIC = "mechanic";
    //------------------------------------------------------------------------------------------------------------------
    private static final String TABLE_DAMAGES = "damages";
    private static final String COLUMN_DAMAGE = "damage";


    private static final Database instance = new Database();

    private Database() {
    }

    public static Database getInstance() {
        return instance;
    }

    public void createDatabase() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER + "( " +
                    COLUMN_CUSTOMERID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_SURNAME + " TEXT NOT NULL )");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_VEHIClE + "( " +
                    COLUMN_CUSTOMERID + " INTEGER NOT NULL, " +
                    COLUMN_VEHICLEID + " INTEGER PRIMARY KEY, " +
                    COLUMN_MANUFACTURER + " TEXT NOT NULL, " +
                    COLUMN_MODEL + " TEXT NOT NULL, " +
                    COLUMN_PLATE + " TEXT NOT NULL, " +
                    COLUMN_VIN + " TEXT NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_WHEELS + "( " +
                    COLUMN_CUSTOMERID + " INTEGER NOT NULL, " +
                    COLUMN_VEHICLEID + " INTEGER NOT NULL, " +
                    COLUMN_WHEELID + " INTEGER PRIMARY KEY, " +
                    COLUMN_LOCATION + " TEXT NOT NULL, " +
                    COLUMN_WIDTH + " TEXT NOT NULL, " +
                    COLUMN_HEIGHT + " TEXT NOT NULL, " +
                    COLUMN_DIAMETER + " TEXT NOT NULL, " +
                    COLUMN_LOADINDEX + " TEXT NOT NULL, " +
                    COLUMN_SPEEDINDEX + " TEXT NOT NULL, " +
                    COLUMN_SEASON + " TEXT NOT NULL, " +
                    COLUMN_DOT + " TEXT NOT NULL, " +
                    COLUMN_FL + " REAL NOT NULL, " +
                    COLUMN_RL + " REAL NOT NULL, " +
                    COLUMN_FR + " REAL NOT NULL, " +
                    COLUMN_RR + " REAL NOT NULL, " +
                    COLUMN_TYREMANUFACTURER + " TEXT NOT NULL, " +
                    COLUMN_TYREMODEL + " TEXT NOT NULL, " +
                    COLUMN_RIM + " TEXT NOT NULL, " +
                    COLUMN_BOLTS + " TEXT NOT NULL, " +
                    COLUMN_CAPS + " TEXT NOT NULL, " +
                    COLUMN_DATEIN + " TEXT NOT NULL, " +
                    COLUMN_DATEOUT + " TEXT NOT NULL, " +
                    COLUMN_DAMAGE1 + " TEXT, " +
                    COLUMN_DAMAGE2 + " TEXT, " +
                    COLUMN_WHODIDIT + " TEXT NOT NULL, " +
                    COLUMN_WHEELSTORED + " INTEGER NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MECHANICS + "( " +
                    COLUMN_MECHANIC + " TEXT NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_DAMAGES + "( " +
                    COLUMN_DAMAGE + " TEXT NOT NULL)");

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMechanics() throws SQLException {
        String query = "SELECT * FROM " + TABLE_MECHANICS;
        ResultSet mechanicsRS;
        mechanicsRS = statement.executeQuery(query);
        while (mechanicsRS.next()) {
            String mechanic = mechanicsRS.getString(1);
            Mechanics mechanics = new Mechanics(mechanic);
            mList.add(mechanics);
        }
        statement.close();
        mechanicsRS.close();
    }

    public void getDamages() throws SQLException {
        String query = "SELECT * FROM " + TABLE_DAMAGES;
        ResultSet damagesRS;
        damagesRS = statement.executeQuery(query);
        while (damagesRS.next()) {
            String damage = damagesRS.getString(1);
            Damages damages = new Damages(damage);
            dList.add(damages);
        }
        statement.close();
        damagesRS.close();
    }

    public void getCustomer() throws SQLException {
        String query = "SELECT * FROM " + TABLE_CUSTOMER;
        ResultSet customerRS;
        customerRS = statement.executeQuery(query);
        while (customerRS.next()) {
            int customerId = customerRS.getInt(1);
            String customerName = customerRS.getString(2);
            String customerSurname = customerRS.getString(3);

            Customer kunde = new Customer(customerId, customerName, customerSurname);
            cList.add(kunde);
        }
        statement.close();
        customerRS.close();
    }

    public void getCustomerFiltered(int inputCID) throws SQLException {
        String query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMERID + " = " + inputCID;
        ResultSet customerRS = statement.executeQuery(query);
        while (customerRS.next()) {
            int customerId = customerRS.getInt(1);
            String customerName = customerRS.getString(2);
            String customerSurname = customerRS.getString(3);

            Customer kunde = new Customer(customerId, customerName, customerSurname);
            cList.add(kunde);
        }
        statement.close();
        customerRS.close();
    }

    public void getVehicle(int customerId) throws SQLException {
        String query = "SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_CUSTOMERID + " = " + customerId;
        ResultSet vehicleRS;
        vehicleRS = statement.executeQuery(query);
        while (vehicleRS.next()) {
            int customer_Id = vehicleRS.getInt(1);
            int vehicle_Id = vehicleRS.getInt(2);
            String vehicleManufacturer = vehicleRS.getString(3);
            String vehicleModel = vehicleRS.getString(4);
            String vehiclePlate = vehicleRS.getString(5);
            String vehicleVin = vehicleRS.getString(6);

            Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
            vList.add(vehicle);
        }
        statement.close();
        vehicleRS.close();
    }

    public void getVehicleFitlered(int inputVID) throws SQLException {
        String query = "SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VEHICLEID + " = " + inputVID;
        ResultSet vehicleRS = statement.executeQuery(query);
        while (vehicleRS.next()) {
            int customer_Id = vehicleRS.getInt(1);
            int vehicle_Id = vehicleRS.getInt(2);
            String vehicleManufacturer = vehicleRS.getString(3);
            String vehicleModel = vehicleRS.getString(4);
            String vehiclePlate = vehicleRS.getString(5);
            String vehicleVin = vehicleRS.getString(6);

            Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
            vList.add(vehicle);
        }
        statement.close();
        vehicleRS.close();
    }

    public void getWheels(int vehicleID) throws SQLException {
        String query = "SELECT * FROM " + TABLE_WHEELS + " WHERE " + COLUMN_VEHICLEID + " = " + vehicleID;
        ResultSet wheelsRS;
        wheelsRS = statement.executeQuery(query);
        while (wheelsRS.next()) {
            int customerId = wheelsRS.getInt(1);
            int vehicleId = wheelsRS.getInt(2);
            int wheelsId = wheelsRS.getInt(3);
            String location = wheelsRS.getString(4);
            String width = wheelsRS.getString(5);
            String height = wheelsRS.getString(6);
            String diameter = wheelsRS.getString(7);
            String loadIndex = wheelsRS.getString(8);
            String speedIndex = wheelsRS.getString(9);
            String season = wheelsRS.getString(10);
            String dot = wheelsRS.getString(11);
            double fl = wheelsRS.getDouble(12);
            double rl = wheelsRS.getDouble(13);
            double fr = wheelsRS.getDouble(14);
            double rr = wheelsRS.getDouble(15);
            String tyreManufacturer = wheelsRS.getString(16);
            String tyreModel = wheelsRS.getString(17);
            String rim = wheelsRS.getString(18);
            String bolts = wheelsRS.getString(19);
            String caps = wheelsRS.getString(20);
            LocalDate dateIn = LocalDate.parse(wheelsRS.getString(21));
            LocalDate dateOut = LocalDate.parse(wheelsRS.getString(22));
            String damage1 = wheelsRS.getString(23);
            String damage2 = wheelsRS.getString(24);
            String user = wheelsRS.getString(25);
            int wheelStored = wheelsRS.getInt(26);

            Wheels wheels = new Wheels(customerId, vehicleId, wheelsId, location, width, height, diameter, loadIndex, speedIndex, season, dot, fl, rl, fr, rr, tyreManufacturer, tyreModel, rim, bolts, caps, dateIn, dateOut, damage1, damage2, user, wheelStored);
            wList.add(wheels);
        }

        wheelsRS.close();
        statement.close();
    }

    public void getWheelsFiltered(double depth, String seasonFilter) throws SQLException {
        String query = "SELECT * FROM " + TABLE_WHEELS + " WHERE " + COLUMN_SEASON + " ='" + seasonFilter + "' AND (" + COLUMN_FL + " <= " + depth + " OR " + COLUMN_RL + " <= " + depth + " OR " + COLUMN_FR + " <= " + depth + " OR " + COLUMN_RR + " <= " + depth + ")";
        ResultSet wheelsRS;
        wheelsRS = statement.executeQuery(query);
        while (wheelsRS.next()) {
            int customerId = wheelsRS.getInt(1);
            int vehicleId = wheelsRS.getInt(2);
            int wheelsId = wheelsRS.getInt(3);
            String location = wheelsRS.getString(4);
            String width = wheelsRS.getString(5);
            String height = wheelsRS.getString(6);
            String diameter = wheelsRS.getString(7);
            String loadIndex = wheelsRS.getString(8);
            String speedIndex = wheelsRS.getString(9);
            String season = wheelsRS.getString(10);
            String dot = wheelsRS.getString(11);
            double fl = wheelsRS.getDouble(12);
            double rl = wheelsRS.getDouble(13);
            double fr = wheelsRS.getDouble(14);
            double rr = wheelsRS.getDouble(15);
            String tyreManufacturer = wheelsRS.getString(16);
            String tyreModel = wheelsRS.getString(17);
            String rim = wheelsRS.getString(18);
            String bolts = wheelsRS.getString(19);
            String caps = wheelsRS.getString(20);
            LocalDate dateIn = LocalDate.parse(wheelsRS.getString(21));
            LocalDate dateOut = LocalDate.parse(wheelsRS.getString(22));
            String damage1 = wheelsRS.getString(23);
            String damage2 = wheelsRS.getString(24);
            String user = wheelsRS.getString(25);
            int wheelStored = wheelsRS.getInt(26);

            Wheels wheels = new Wheels(customerId, vehicleId, wheelsId, location, width, height, diameter, loadIndex, speedIndex, season, dot, fl, rl, fr, rr, tyreManufacturer, tyreModel, rim, bolts, caps, dateIn, dateOut, damage1, damage2, user, wheelStored);
            wList.add(wheels);
        }
    }

    public void search(String searchBox, String inputSearch) throws SQLException {
        String queryExist;
        String query;
        ResultSet ifExists;
        ResultSet searchRS;

        switch (searchBox) {
            case "Nachname" -> {
                queryExist = "SELECT EXISTS(SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_SURNAME + " ='" + inputSearch + "')";
                ifExists = statement.executeQuery(queryExist);
                if (ifExists.getBoolean(1)) {
                    query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_SURNAME + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, inputSearch);
                    searchRS = preparedStatement.executeQuery();
                    this.cID = searchRS.getInt(1);

                    while (searchRS.next()) {
                        int customerId = searchRS.getInt(1);
                        String customerName = searchRS.getString(2);
                        String customerSurname = searchRS.getString(3);

                        Customer kunde = new Customer(customerId, customerName, customerSurname);
                        cList.add(kunde);
                    }

                    preparedStatement.close();
                    searchRS.close();

                    query = "SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_CUSTOMERID + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, cID);
                    searchRS = preparedStatement.executeQuery();

                    while (searchRS.next()) {
                        int customer_Id = searchRS.getInt(1);
                        int vehicle_Id = searchRS.getInt(2);
                        String vehicleManufacturer = searchRS.getString(3);
                        String vehicleModel = searchRS.getString(4);
                        String vehiclePlate = searchRS.getString(5);
                        String vehicleVin = searchRS.getString(6);

                        Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
                        vList.add(vehicle);
                    }
                    searchRS.close();
                    preparedStatement.close();
                } else {
                    PopupWindowBlank.display("Kunde nicht gefunden");
                }
                statement.close();
                ifExists.close();
            }
            case "Kennzeichen" -> {
                queryExist = "SELECT EXISTS(SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_PLATE + " ='" + inputSearch + "')";
                ifExists = statement.executeQuery(queryExist);
                if (ifExists.getBoolean(1)) {
                    query = "SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_PLATE + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, inputSearch);
                    searchRS = preparedStatement.executeQuery();
                    int cID = searchRS.getInt(1);

                    while (searchRS.next()) {
                        int customer_Id = searchRS.getInt(1);
                        int vehicle_Id = searchRS.getInt(2);
                        String vehicleManufacturer = searchRS.getString(3);
                        String vehicleModel = searchRS.getString(4);
                        String vehiclePlate = searchRS.getString(5);
                        String vehicleVin = searchRS.getString(6);

                        Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
                        vList.add(vehicle);
                    }
                    searchRS.close();
                    preparedStatement.close();

                    query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMERID + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, cID);
                    searchRS = preparedStatement.executeQuery();

                    while (searchRS.next()) {
                        int customerId = searchRS.getInt(1);
                        String customerName = searchRS.getString(2);
                        String customerSurname = searchRS.getString(3);

                        Customer kunde = new Customer(customerId, customerName, customerSurname);
                        cList.add(kunde);
                    }
                    searchRS.close();
                    preparedStatement.close();

                } else {
                    PopupWindowBlank.display("Kennzeichen nicht gefunden");
                }
            }
            case "VIN" -> {
                queryExist = "SELECT EXISTS(SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VIN + " ='" + inputSearch + "')";
                ifExists = statement.executeQuery(queryExist);
                if (ifExists.getBoolean(1)) {
                    query = "SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VIN + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, inputSearch);
                    searchRS = preparedStatement.executeQuery();
                    int cID = searchRS.getInt(1);

                    while (searchRS.next()) {
                        int customer_Id = searchRS.getInt(1);
                        int vehicle_Id = searchRS.getInt(2);
                        String vehicleManufacturer = searchRS.getString(3);
                        String vehicleModel = searchRS.getString(4);
                        String vehiclePlate = searchRS.getString(5);
                        String vehicleVin = searchRS.getString(6);

                        Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
                        vList.add(vehicle);
                    }
                    searchRS.close();
                    preparedStatement.close();

                    query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMERID + " = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, cID);
                    searchRS = preparedStatement.executeQuery();

                    while (searchRS.next()) {
                        int customerId = searchRS.getInt(1);
                        String customerName = searchRS.getString(2);
                        String customerSurname = searchRS.getString(3);

                        Customer kunde = new Customer(customerId, customerName, customerSurname);
                        cList.add(kunde);
                    }
                    searchRS.close();
                    preparedStatement.close();

                } else {
                    PopupWindowBlank.display("Fahrgestellnummer nicht gefunden");
                }
            }
        }
    }

    public void newCustomer(String name, String surname) throws SQLException {
        boolean isCustomernew;
        String queryIfOld = "SELECT EXISTS(SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_NAME + " = '" + name + "' AND " + COLUMN_SURNAME + " ='" + surname + "')";

        ResultSet resultSet;
        resultSet = statement.executeQuery(queryIfOld);
        if (resultSet.getBoolean(1)) {
            isCustomernew = false;
            resultSet.close();
        } else {
            isCustomernew = true;
            resultSet.close();
        }
        if (isCustomernew) {
            String query = "INSERT INTO " + TABLE_CUSTOMER + " VALUES (null, ?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            String queryCID = "SELECT " + COLUMN_CUSTOMERID + " FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_NAME + " = ? AND " + COLUMN_SURNAME + " = ?";
            ResultSet getCustomerRS;
            preparedStatement = connection.prepareStatement(queryCID);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            getCustomerRS = preparedStatement.executeQuery();
            this.cID = getCustomerRS.getInt(1);

            preparedStatement.close();
            getCustomerRS.close();
        } else {
            this.cID = 0;
        }
    }

    public void newVehicle(int customerID, String manufacturer, String model, String plate, String vin) throws SQLException {
        boolean isVehicleNew;
        String queryIfOld = "SELECT EXISTS(SELECT * FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VIN + " ='" + vin + "')";

        ResultSet resultSet;
        resultSet = statement.executeQuery(queryIfOld);
        if (resultSet.getBoolean(1)) {
            isVehicleNew = false;
            resultSet.close();
        } else {
            isVehicleNew = true;
            resultSet.close();
        }
        if (isVehicleNew) {
            String query = "INSERT INTO " + TABLE_VEHIClE + " VALUES (?,null,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, plate);
            preparedStatement.setString(5, vin);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            String queryVID = "SELECT " + COLUMN_VEHICLEID + " FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VIN + " = ?";
            ResultSet getVehicleRS;
            preparedStatement = connection.prepareStatement(queryVID);
            preparedStatement.setString(1, vin);
            getVehicleRS = preparedStatement.executeQuery();
            this.vID = getVehicleRS.getInt(1);

        } else {
            this.vID = 0;
        }




    }

    public void newMechanic(String name) throws SQLException {
        String query = "INSERT INTO " + TABLE_MECHANICS + " VALUES (?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void newDamage(String damage) throws SQLException {
        String query = "INSERT INTO " + TABLE_DAMAGES + " VALUES (?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, damage);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void writeWeheels(int cId, int vId, String location, String width, String height, String diameter, String loadIndex,
                             char speedIndex, String season, String dot, double fl, double rl, double fr, double rr, String
                                     tyreManufacturer, String tyreModel, String rim, String bolts, String caps, LocalDate dateIn, LocalDate
                                     dateOut, String damage1, String damage2, String user, int wheelStored) throws SQLException {
        String writeQuery = "INSERT INTO " + TABLE_WHEELS + " VALUES (?,?,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        preparedStatement = connection.prepareStatement(writeQuery);
        preparedStatement.setInt(1, cId);
        preparedStatement.setInt(2, vId);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, width);
        preparedStatement.setString(5, height);
        preparedStatement.setString(6, diameter);
        preparedStatement.setString(7, loadIndex);
        preparedStatement.setString(8, String.valueOf(speedIndex));
        preparedStatement.setString(9, season);
        preparedStatement.setString(10, dot);
        preparedStatement.setDouble(11, fl);
        preparedStatement.setDouble(12, rl);
        preparedStatement.setDouble(13, fr);
        preparedStatement.setDouble(14, rr);
        preparedStatement.setString(15, tyreManufacturer);
        preparedStatement.setString(16, tyreModel);
        preparedStatement.setString(17, rim);
        preparedStatement.setString(18, bolts);
        preparedStatement.setString(19, caps);
        preparedStatement.setString(20, String.valueOf(dateIn));
        preparedStatement.setString(21, String.valueOf(dateOut));
        preparedStatement.setString(22, damage1);
        preparedStatement.setString(23, damage2);
        preparedStatement.setString(24, user);
        preparedStatement.setInt(25, wheelStored);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void editCustomer(int customerID, String name, String surname) throws SQLException {
        String query = "UPDATE " + TABLE_CUSTOMER + " SET " + COLUMN_NAME + " = ?, " + COLUMN_SURNAME + " = ? WHERE " + COLUMN_CUSTOMERID + " = " + customerID;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void editVehicle(int vehicleID, String manufacturer, String model, String plate, String vin) throws
            SQLException {
        String query = "UPDATE " + TABLE_VEHIClE + " SET " + COLUMN_MANUFACTURER + " = ?, " + COLUMN_MODEL + " = ?, " + COLUMN_PLATE + " = ?, " + COLUMN_VIN + " = ? WHERE " + COLUMN_VEHICLEID + " = " + vehicleID;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, manufacturer);
        preparedStatement.setString(2, model);
        preparedStatement.setString(3, plate);
        preparedStatement.setString(4, vin);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void editWheels(int wId, String location, String width, String height, String diameter, String loadIndex,
                           char speedIndex, String season, String dot, double fl, double rl, double fr, double rr, String
                                   tyreManufacturer, String tyreModel, String rim, String bolts, String caps, LocalDate dateIn, LocalDate
                                   dateOut, String damage1, String damage2, String user, int wheelStored) throws SQLException {
        String query = "UPDATE " + TABLE_WHEELS + " SET "
                + COLUMN_LOCATION + " = ?, "
                + COLUMN_WIDTH + " = ?, "
                + COLUMN_HEIGHT + " = ?, "
                + COLUMN_DIAMETER + " = ?, "
                + COLUMN_LOADINDEX + " = ?, "
                + COLUMN_SPEEDINDEX + " = ?, "
                + COLUMN_SEASON + " = ?, "
                + COLUMN_DOT + " = ?, "
                + COLUMN_FL + " = ?, "
                + COLUMN_FR + " = ?, "
                + COLUMN_RL + " = ?, "
                + COLUMN_RR + " = ?, "
                + COLUMN_TYREMANUFACTURER + " = ?, "
                + COLUMN_TYREMODEL + " = ?, "
                + COLUMN_RIM + " = ?, "
                + COLUMN_BOLTS + " = ?, "
                + COLUMN_CAPS + " = ?, "
                + COLUMN_DATEIN + " = ?, "
                + COLUMN_DATEOUT + " = ?, "
                + COLUMN_DAMAGE1 + " = ?, "
                + COLUMN_DAMAGE2 + " = ?, "
                + COLUMN_WHODIDIT + " = ?, "
                + COLUMN_WHEELSTORED + " = ? " + " WHERE " + COLUMN_WHEELID + " = " + wId;

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, location);
        preparedStatement.setString(2, width);
        preparedStatement.setString(3, height);
        preparedStatement.setString(4, diameter);
        preparedStatement.setString(5, loadIndex);
        preparedStatement.setString(6, String.valueOf(speedIndex));
        preparedStatement.setString(7, season);
        preparedStatement.setString(8, dot);
        preparedStatement.setDouble(9, fl);
        preparedStatement.setDouble(10, fr);
        preparedStatement.setDouble(11, rl);
        preparedStatement.setDouble(12, rr);
        preparedStatement.setString(13, tyreManufacturer);
        preparedStatement.setString(14, tyreModel);
        preparedStatement.setString(15, rim);
        preparedStatement.setString(16, bolts);
        preparedStatement.setString(17, caps);
        preparedStatement.setString(18, String.valueOf(dateIn));
        preparedStatement.setString(19, String.valueOf(dateOut));
        preparedStatement.setString(20, damage1);
        preparedStatement.setString(21, damage2);
        preparedStatement.setString(22, user);
        preparedStatement.setInt(23, wheelStored);
        preparedStatement.executeUpdate();

        preparedStatement.close();

    }

    public void deleteCustomer(int cId) throws SQLException {
        String query = "DELETE FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMERID + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cId);
        preparedStatement.executeUpdate();

        String queryVehicle = "DELETE FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_CUSTOMERID + " = ?";
        preparedStatement = connection.prepareStatement(queryVehicle);
        preparedStatement.setInt(1, cId);
        preparedStatement.executeUpdate();

        String queryWheels = "DELETE FROM " + TABLE_WHEELS + " WHERE " + COLUMN_CUSTOMERID + " = ?";
        preparedStatement = connection.prepareStatement(queryWheels);
        preparedStatement.setInt(1, cId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void deleteVehicle(int vId) throws SQLException {
        String query = "DELETE FROM " + TABLE_VEHIClE + " WHERE " + COLUMN_VEHICLEID + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, vId);
        preparedStatement.executeUpdate();

        String queryVehicle = "DELETE FROM " + TABLE_WHEELS + " WHERE " + COLUMN_VEHICLEID + " = ?";
        preparedStatement = connection.prepareStatement(queryVehicle);
        preparedStatement.setInt(1, vId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void deleteWheels(int wId) throws SQLException {
        String query = "DELETE FROM " + TABLE_WHEELS + " WHERE " + COLUMN_WHEELID + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, wId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void deleteMechanic(String name) throws SQLException {
        String query = "DELETE FROM " + TABLE_MECHANICS + " WHERE " + COLUMN_MECHANIC + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void deleteDamages(String damage) throws SQLException {
        String query = "DELETE FROM " + TABLE_DAMAGES + " WHERE " + COLUMN_DAMAGE + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, damage);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void export() throws SQLException {
        String queryCustomer = "SELECT * FROM " + TABLE_CUSTOMER;
        String queryVehicle = "SELECT * FROM " + TABLE_VEHIClE;
        String queryWheels = " SELECT * FROM " + TABLE_WHEELS + " WHERE " + COLUMN_WHEELSTORED + " = 1";

        ResultSet customerRS;
        ResultSet vehicleRS;
        ResultSet wheelsRS;

        customerRS = statement.executeQuery(queryCustomer);
        while (customerRS.next()) {
            int customerId = customerRS.getInt(1);
            String customerName = customerRS.getString(2);
            String customerSurname = customerRS.getString(3);

            Customer kunde = new Customer(customerId, customerName, customerSurname);
            cList.add(kunde);
        }
        statement.close();
        customerRS.close();

        vehicleRS = statement.executeQuery(queryVehicle);
        while (vehicleRS.next()) {
            int customer_Id = vehicleRS.getInt(1);
            int vehicle_Id = vehicleRS.getInt(2);
            String vehicleManufacturer = vehicleRS.getString(3);
            String vehicleModel = vehicleRS.getString(4);
            String vehiclePlate = vehicleRS.getString(5);
            String vehicleVin = vehicleRS.getString(6);

            Vehicle vehicle = new Vehicle(customer_Id, vehicle_Id, vehicleManufacturer, vehicleModel, vehiclePlate, vehicleVin);
            vList.add(vehicle);
        }
        statement.close();
        vehicleRS.close();

        wheelsRS = statement.executeQuery(queryWheels);
        while (wheelsRS.next()) {
            int customerId = wheelsRS.getInt(1);
            int vehicleId = wheelsRS.getInt(2);
            int wheelsId = wheelsRS.getInt(3);
            String location = wheelsRS.getString(4);
            String width = wheelsRS.getString(5);
            String height = wheelsRS.getString(6);
            String diameter = wheelsRS.getString(7);
            String loadIndex = wheelsRS.getString(8);
            String speedIndex = wheelsRS.getString(9);
            String season = wheelsRS.getString(10);
            String dot = wheelsRS.getString(11);
            double fl = wheelsRS.getDouble(12);
            double rl = wheelsRS.getDouble(13);
            double fr = wheelsRS.getDouble(14);
            double rr = wheelsRS.getDouble(15);
            String tyreManufacturer = wheelsRS.getString(16);
            String tyreModel = wheelsRS.getString(17);
            String rim = wheelsRS.getString(18);
            String bolts = wheelsRS.getString(19);
            String caps = wheelsRS.getString(20);
            LocalDate dateIn = LocalDate.parse(wheelsRS.getString(21));
            LocalDate dateOut = LocalDate.parse(wheelsRS.getString(22));
            String damage1 = wheelsRS.getString(23);
            String damage2 = wheelsRS.getString(24);
            String user = wheelsRS.getString(25);
            int wheelStored = wheelsRS.getInt(26);

            Wheels wheels = new Wheels(customerId, vehicleId, wheelsId, location, width, height, diameter, loadIndex, speedIndex, season, dot, fl, rl, fr, rr, tyreManufacturer, tyreModel, rim, bolts, caps, dateIn, dateOut, damage1, damage2, user, wheelStored);
            wList.add(wheels);
        }
        wheelsRS.close();
        statement.close();
    }


    public ArrayList<Customer> getcList() {
        return cList;
    }

    public ArrayList<Vehicle> getvList() {
        return vList;
    }

    public ArrayList<Wheels> getwList() {
        return wList;
    }

    public ArrayList<Mechanics> getmList() {
        return mList;
    }

    public ArrayList<Damages> getdList() {
        return dList;
    }

    public int getcID() {
        return cID;
    }

    public int getvID() {
        return vID;
    }

    public int getvIDTA() {
        return vIDTA;
    }

    public int getwID() {
        return wID;
    }

    public int getwIDTA() {
        return wIDTA;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public void setvID(int vID) {
        this.vID = vID;
    }

    public void setvIDTA(int vIDTA) {
        this.vIDTA = vIDTA;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public void setwIDTA(int wIDTA) {
        this.wIDTA = wIDTA;
    }
}
