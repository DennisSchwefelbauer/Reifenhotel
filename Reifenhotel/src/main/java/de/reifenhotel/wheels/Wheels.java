package de.reifenhotel.wheels;

import java.time.LocalDate;
import java.util.Date;

public class Wheels {

    private int customerId;
    private int vehicleId;
    private int wheelsId;
    private String location;
    private String width;
    private String height;
    private String diameter;
    private String loadIndex;
    private String speedIndex;
    private String season;
    private String dot;
    private double fl;
    private double rl;
    private double fr;
    private double rr;
    private String tyreManufacturer;
    private String tyreModel;
    private String rim;
    private String bolts;
    private String caps;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private String damage1;
    private String damage2;
    private String user;
    private int wheelStored;

    public Wheels(int customerId, int vehicleId, int wheelsId, String location, String width, String height, String diameter, String loadIndex, String speedIndex, String season, String dot, double fl, double rl, double fr, double rr, String tyreManufacturer, String tyreModel, String rim, String bolts, String caps, LocalDate dateIn, LocalDate dateOut, String damage1, String damage2, String user, int wheelStored) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.wheelsId = wheelsId;
        this.location = location;
        this.width = width;
        this.height = height;
        this.diameter = diameter;
        this.loadIndex = loadIndex;
        this.speedIndex = speedIndex;
        this.season = season;
        this.dot = dot;
        this.fl = fl;
        this.rl = rl;
        this.fr = fr;
        this.rr = rr;
        this.tyreManufacturer = tyreManufacturer;
        this.tyreModel = tyreModel;
        this.rim = rim;
        this.bolts = bolts;
        this.caps = caps;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.damage1 = damage1;
        this.damage2 = damage2;
        this.user = user;
        this.wheelStored = wheelStored;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getWheelsId() {
        return wheelsId;
    }

    public String getLocation() {
        return location;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getLoadIndex() {
        return loadIndex;
    }

    public String getSpeedIndex() {
        return speedIndex;
    }

    public String getSeason() {
        return season;
    }

    public String getDot() {
        return dot;
    }

    public double getFl() {
        return fl;
    }

    public double getRl() {
        return rl;
    }

    public double getFr() {
        return fr;
    }

    public double getRr() {
        return rr;
    }

    public String getTyreManufacturer() {
        return tyreManufacturer;
    }

    public String getTyreModel() {
        return tyreModel;
    }

    public String getRim() {
        return rim;
    }

    public String getBolts() {
        return bolts;
    }

    public String getCaps() {
        return caps;
    }

        public LocalDate getDateIn() {
        return dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public String getDamage1() {
        return damage1;
    }

    public String getDamage2() {
        return damage2;
    }

    public String getUser() {
        return user;
    }

    public int getWheelStored() {
        return wheelStored;
    }
}
