package com.example.courseprojectdraft_24;

import java.io.Serializable;

public class Car  implements Serializable {
    private int id;
    private String type;
    private String model;
    private double price;
    private double offers;
    private int year;
    private double distance;

    public Car(){

    }
    public Car(int id, String type, String model, double price, double offers, int year, double distance) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.price = price;
        this.offers = offers;
        this.year = year;
        this.distance = distance;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOffers() {
        return offers;
    }

    public void setOffers(double offers) {
        this.offers = offers;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", offers=" + offers +
                ", year=" + year +
                ", distance=" + distance +
                '}';
    }
}