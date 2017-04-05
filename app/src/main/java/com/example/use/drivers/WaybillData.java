package com.example.use.drivers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import fragments.Empowerment;
import fragments.Realization;
import fragments.Refund;

public class WaybillData extends Activity {

    private CustomAdapter mAdapter;
    private ListView lv;
    private Intent intent;
    private Waybill waybill;
    public static final String EXTRA_WAYBILL = "extra_waybill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill_data);
        waybill = (Waybill)getIntent().getParcelableExtra(EXTRA_WAYBILL);
        lv = (ListView)findViewById(R.id.listView1);
        mAdapter = new CustomAdapter(this);
        mAdapter.addSectionHeaderItem("Позвонить");
        mAdapter.addItem("Торговому представителю");
        mAdapter.addItem("Торговой точке");
        mAdapter.addItem("Диспетчеру");
        mAdapter.addSectionHeaderItem("Заполнить");
        mAdapter.addItem("Реализация");
        mAdapter.addItem("Доверенность");
        mAdapter.addItem("Возврат");
        mAdapter.addSectionHeaderItem("Дополнительная информация");
        mAdapter.addItem("Заправка");
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0: break; // title
                    case 1:
                        intent = new Intent();
                        intent.setData(Uri.parse("tel:" + "123456789"));
                        intent.setAction(Intent.ACTION_CALL);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent();
                        intent.setData(Uri.parse("tel:" + "123456789"));
                        intent.setAction(Intent.ACTION_CALL);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent();
                        intent.setData(Uri.parse("tel:" + "123456789"));
                        intent.setAction(Intent.ACTION_CALL);
                        startActivity(intent);
                        break;
                    case 4:break; // title
                    case 5:
                        intent = new Intent(getApplicationContext(), Realization.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(), Empowerment.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(), Refund.class);
                        intent.putExtra(Refund.EXTRA_GOOD, waybill);
                        startActivity(intent);
                        break;
                    case 8:break; // title
                    case 9:
                }
            }
        });
    }

}
