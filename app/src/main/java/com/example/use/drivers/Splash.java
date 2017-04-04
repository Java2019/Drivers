package com.example.use.drivers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Splash extends Activity {

    private SharedPreferences sPref;
    final String SAVED_TEXT = "appCode";
    public String login = "";
    private String resultJson = "";
    public String pref_ip;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        login = prefs.getString("login", "");
        if (!login.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        pref_ip = prefs.getString("pref_ip", "");
        if (pref_ip.isEmpty()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("pref_ip", "91.144.158.194:34789");
            editor.commit();
        }

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.editText);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                login = editText.getText().toString();
                if (login.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "введите код", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    ParseTask parseTask = new ParseTask();
                    parseTask.execute();
                }
            }
        });
    }

    public Boolean getCode(){
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        //etText.setText(savedText);
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
        return true;
    };

    public void setCode(){
        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, findViewById(R.id.editText).toString());
        ed.commit();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String pref_ip = prefs.getString("pref_ip", "");
            try {
                URL url = new URL("http://" + pref_ip + "/MainActivity/hs/MainActivity?login=" + login);

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
                if (dataJsonObj.getString("ОтветАвторизации").equals("авторизация пройдена")){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("login", login);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "неверный код", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
