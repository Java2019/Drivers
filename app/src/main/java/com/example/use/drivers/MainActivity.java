package com.example.use.drivers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
    public ArrayList<Waybill> titleList = new ArrayList<Waybill>();
    private MainActivityListAdapter adapter;
    private ListView lv;
    private ListView mDrawerListView;
    private String[] mDrawerItem;
    private DrawerLayout drawlayout;
    public String pref_ip;
    public SharedPreferences prefs;
    private String login = "";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        pref_ip = prefs.getString("pref_ip", "");
        login = prefs.getString("login", "");

        drawlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView)findViewById(R.id.left_drawer);
        mDrawerItem = getResources().getStringArray(R.array.drawer_items);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                mDrawerItem));
        mDrawerListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                drawlayout.closeDrawers();
                switch (position){
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), PrefActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("login", "");
                        editor.commit();
                        intent = new Intent(getApplicationContext(), Splash.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });

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
            pref_ip = "91.144.158.194:34789";
            try {
                URL url = new URL("http://" + pref_ip + "/MainActivity/hs/MainActivity/list?login="+login);

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
                    /*for (int k = 0; k < goods.length(); k++) {
                        JSONObject good = goods.getJSONObject(k);
                        goodTables.add(new GoodTable(good.getString("Наименование"), good.getString("Количество")));
                    }*/
                    titleList.add(new Waybill(document.getString("ЮрАдрес"), document.getString("ЮрЛицо"),
                            document.getString("НомерДокумента"), document.getString("ВидДокумента"), goodTables));
                }
                lv.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
