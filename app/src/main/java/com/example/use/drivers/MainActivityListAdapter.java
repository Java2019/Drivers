package com.example.use.drivers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by use on 24.03.17.
 */
public class MainActivityListAdapter extends ArrayAdapter<Waybill> {

    private LayoutInflater inflater;
    private int layout;
    private List<Waybill> waybills;
    private Context context;

    public MainActivityListAdapter(Context context, int resource, List<Waybill> waybills) {
        super(context, resource, waybills);
        this.waybills = waybills;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView number = (TextView) view.findViewById(R.id.tv1);
        TextView data = (TextView) view.findViewById(R.id.tv3);
        TextView car = (TextView) view.findViewById(R.id.tv4);
        //TextView driver = (TextView) view.findViewById(R.id.tv4);
        //driver.setVisibility(View.INVISIBLE);
        Waybill waybill = waybills.get(position);

        number.setText(waybill.getData());
        data.setText(waybill.getNumber());
        car.setText(waybill.getCar());
        if (waybill.getDriver().equals("Реализация")){
            view.setBackgroundResource(R.drawable.rounded_view_grey);
        }else if (waybill.getDriver().equals("Возврат")) {
            view.setBackgroundResource(R.drawable.rounded_view_orange);
        }else if (waybill.getDriver().equals("Деньги")) {
            view.setBackgroundResource(R.drawable.rounded_view_green);
        }
        return view;
    }
}
