package com.example.use.drivers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Empowerment extends AppCompatActivity {

    private TextView tv;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empowerment);
        setTitle("Доверенность");
        //setTitleColor();

        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radio_1);
        radioButton1.setOnClickListener(radioButtonClickListener);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radio_2);
        radioButton2.setOnClickListener(radioButtonClickListener);

        tv = (TextView)findViewById(R.id.tv5);
        tv.setVisibility(View.INVISIBLE);
        et = (EditText)findViewById(R.id.et1);
        et.setVisibility(View.INVISIBLE);

    }
    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton)view;
            switch (rb.getId()){
                case R.id.radio_2:
                    tv = (TextView)findViewById(R.id.tv5);
                    tv.setVisibility(View.VISIBLE);
                    et = (EditText)findViewById(R.id.et1);
                    et.setVisibility(View.VISIBLE);
                    et.setText("");
                    break;
                case R.id.radio_1:
                    tv = (TextView)findViewById(R.id.tv5);
                    tv.setVisibility(View.INVISIBLE);
                    et = (EditText)findViewById(R.id.et1);
                    et.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
}
