package com.example.use.drivers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity{

    public static String LOG_TAG = "my_log";
    // то в чем будем хранить данные пока не передадим адаптеру
    public ArrayList<Waybill> titleList = new ArrayList<Waybill>();
    // Listview Adapter для вывода данных
    private MainActivityListAdapter adapter;
    // List view
    private ListView lv;
    // drawer layout
    private ListView mDrawerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerListView = (ListView)findViewById(R.id.left_drawer);

        //mDrawerListView.

        new ParseTask().execute();
        // определение данных
        lv = (ListView) findViewById(R.id.listView);
        adapter = new MainActivityListAdapter(this, R.layout.item, titleList);
        //lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), WaybillData.class);
                intent.putExtra(WaybillData.EXTRA_WAYBILL, titleList.get(i));
                startActivity(intent);
            }
        });
    }



    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            String ip = "192.168.199.7";
            ip = "109.173.90.94";
            try {
                URL url = new URL("http://" +ip + "/MainActivity/hs/MainActivity/list");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            JSONObject dataJsonObj = null;
            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray documents = dataJsonObj.getJSONArray("Документы");
                // перебираем и выводим
                for (int i = 0; i < documents.length(); i++) {
                    JSONObject document = documents.getJSONObject(i);
                    ArrayList<GoodTable> goodTables = new ArrayList<>();
                    JSONArray goods = document.getJSONArray("ТаблицаТоваров");
                    for (int k = 0; k < goods.length(); k++) {
                        JSONObject good = goods.getJSONObject(k);
                        goodTables.add(new GoodTable(good.getString("Наименование"), good.getString("Количество")));
                    }
                    titleList.add(new Waybill(document.getString("Дата"), document.getString("Номер"),
                            document.getString("Транспорт"), document.getString("Водитель"), goodTables));
                }
                lv.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
