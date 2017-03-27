package com.example.use.drivers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by use on 26.03.17.
 */
public class GoodTable implements Parcelable {

    private String name;
    private String quantity;

    public GoodTable(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    protected GoodTable(Parcel in) {
        this.name = in.readString();
        this.quantity = in.readString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public static final Creator<GoodTable> CREATOR = new Creator<GoodTable>() {
        @Override
        public GoodTable createFromParcel(Parcel in) {
            return new GoodTable(in);
        }

        @Override
        public GoodTable[] newArray(int size) {
            return new GoodTable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(quantity);
    }
}
