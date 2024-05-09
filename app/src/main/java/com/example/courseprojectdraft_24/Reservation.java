package com.example.courseprojectdraft_24;

public class Reservation {

    private int id;
    private int carId;
    private String userId;
    private String reservationDate;
    private String reservationTime;
    private double price;
    public Reservation(){
    }
    public Reservation(int id, int carId, String userId, String reservationDate, String reservationTime, double price) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    public String getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
