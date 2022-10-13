package de.reifenhotel.customer;

public class Customer {

    private int id;
    private String name;
    private String surname;

    public Customer(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
