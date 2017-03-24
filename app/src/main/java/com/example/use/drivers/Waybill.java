package com.example.use.drivers;

/**
 * Created by use on 24.03.17.
 */
public class Waybill {
    String data;
    String number;
    String car;
    String driver;

    public Waybill(String data, String number, String car, String driver) {
        this.data = data;
        this.number = number;
        this.car = car;
        this.driver = driver;
    }

    public String getData() {
        return data.substring(0,10);
    }

    public String getNumber() {
        return number;
    }

    public String getCar() {
        return car;
    }

    public String getDriver() {
        return driver;
    }
}
