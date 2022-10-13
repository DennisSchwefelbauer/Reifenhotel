package de.reifenhotel.vehicle;

public class Vehicle {

    private int customerId;
    private int vehicleId;
    private String manufacturer;
    private String model;
    private String plate;
    private String vin;

    public Vehicle(int customerId, int vehicleId, String manufacturer, String model, String plate, String vin) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.plate = plate;
        this.vin = vin;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    public String getVin() {
        return vin;
    }
}
