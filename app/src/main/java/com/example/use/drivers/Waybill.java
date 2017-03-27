package com.example.use.drivers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by use on 24.03.17.
 */
public class Waybill implements Parcelable{
    private String data;
    private String number;
    private String car;
    private String driver;
    private ArrayList<GoodTable> goodTables = new ArrayList<>();

    public Waybill(String data, String number, String car, String driver, ArrayList<GoodTable> goodTables) {
        this.data = data;
        this.number = number;
        this.car = car;
        this.driver = driver;
        this.goodTables = goodTables;
    }

    public Waybill(Parcel parcel){
        data = parcel.readString();
        number = parcel.readString();
        car = parcel.readString();
        driver = parcel.readString();
        int count = parcel.readInt();
        for (int i = 0; i < count; i++){
            String name = parcel.readString();
            String quantity = parcel.readString();
            goodTables.add(new GoodTable(name, quantity));
        }
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

    public ArrayList<GoodTable> getGoodTables() {
        return this.goodTables;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(data);
        parcel.writeString(number);
        parcel.writeString(car);
        parcel.writeString(driver);
        parcel.writeInt(goodTables.size());
        //parcel.writeList(goodTables);
        for (GoodTable element : goodTables){
            String name = element.getName();
            String quantity = element.getQuantity();
            parcel.writeString(name);
            parcel.writeString(quantity);
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Waybill createFromParcel(Parcel parcel) {
            return new Waybill(parcel);
        }

        @Override
        public Waybill[] newArray(int i) {
            return new Waybill[i];
        }
    };

}
