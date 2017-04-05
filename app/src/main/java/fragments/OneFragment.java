package fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.use.drivers.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by use on 05.04.17.
 */
public class OneFragment extends Fragment{

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
    private View myFragmentView;

    public OneFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_one, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref_ip = prefs.getString("pref_ip", "");
        login = prefs.getString("login", "");

        new ParseTask().execute();
        // определение данных
        lv = (ListView) myFragmentView.findViewById(R.id.listView);
        adapter = new MainActivityListAdapter(getContext(), R.layout.item, titleList);
        //lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), MainTab.class);
                //intent.putExtra(WaybillData.EXTRA_WAYBILL, titleList.get(i));
                startActivity(intent);
            }
        });
        return myFragmentView;
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
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
