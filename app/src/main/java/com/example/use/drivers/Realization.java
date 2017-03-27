package com.example.use.drivers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class Realization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realization);
        setTitle("Реализация");

        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radio_3);
        radioButton1.setOnClickListener(radioButtonClickListener);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radio_4);
        radioButton2.setOnClickListener(radioButtonClickListener);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
