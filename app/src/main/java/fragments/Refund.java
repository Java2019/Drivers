package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.use.drivers.GoodTable;
import com.example.use.drivers.R;
import com.example.use.drivers.Waybill;

import java.util.ArrayList;

public class Refund extends Fragment {

    public static final String EXTRA_GOOD = "extra_good";
    private ArrayList<GoodTable> goodTable = new ArrayList<>();
    private Waybill waybill;
    private ListView lv;

    public Refund() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_realization, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*waybill = (Waybill)getIntent().getParcelableExtra(EXTRA_GOOD);
        goodTable = waybill.getGoodTables();
        lv = (ListView)findViewById(R.id.listView3);
        MyAdapter adapter = new MyAdapter(this, R.layout.item_good);
        lv.setAdapter(adapter);

        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radio_5);
        radioButton1.setOnClickListener(radioButtonClickListener);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radio_6);
        radioButton2.setOnClickListener(radioButtonClickListener);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton)view;
            switch (rb.getId()){
                case R.id.radio_6:
                    break;
            }
        }*/
    };

    private class MyAdapter extends BaseAdapter{

        private LayoutInflater inflater;
        private int layout;

        public MyAdapter(Context context, int resource) {
            this.layout = resource;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return goodTable.size();
        }

        @Override
        public Object getItem(int i) {
            return goodTable.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(this.layout, parent, false);

            TextView name = (TextView) view.findViewById(R.id.tv6);
            EditText quantity = (EditText) view.findViewById(R.id.et2);

            GoodTable good = goodTable.get(position);

            name.setText(good.getName());
            quantity.setText(good.getQuantity());

            return view;
        }
    }
}
