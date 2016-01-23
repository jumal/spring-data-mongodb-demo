package org.jumaland;

import java.util.List;

public class Address {

    private final String street;
    private final String zipcode;
    private final String building;
    private final List<Double> coord;

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getBuilding() {
        return building;
    }

    public List<Double> getCoord() {
        return coord;
    }

    public Address(String street, String zipcode, String building, List<Double> coord) {
        this.street = street;
        this.zipcode = zipcode;
        this.building = building;
        this.coord = coord;
    }
}
