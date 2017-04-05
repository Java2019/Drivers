package com.example.use.drivers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by use on 24.03.17.
 */
public class Waybill implements Parcelable{
    private String data1; // ЮрАдрес
    private String data2; // ЮрЛицо
    private String data3; // НомерДокумента
    private String data4; // ВидДокумента
    private String data5; // Факт
    private ArrayList<GoodTable> goodTables = new ArrayList<>();

    public Waybill(String data1, String data2, String data3, String data4, ArrayList<GoodTable> goodTables) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        this.goodTables = goodTables;
    }

    public Waybill(Parcel parcel){
        data1 = parcel.readString();
        data2 = parcel.readString();
        data3 = parcel.readString();
        data4 = parcel.readString();
        int count = parcel.readInt();
        for (int i = 0; i < count; i++){
            String name = parcel.readString();
            String quantity = parcel.readString();
            goodTables.add(new GoodTable(name, quantity));
        }
    }

    public String getData() {
        return data1;
    }

    public String getNumber() {
        return data2;
    }

    public String getCar() {
        return data3;
    }

    public String getDriver() {
        return data4;
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
        parcel.writeString(data1);
        parcel.writeString(data2);
        parcel.writeString(data3);
        parcel.writeString(data4);
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
