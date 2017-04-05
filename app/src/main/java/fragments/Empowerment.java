package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.use.drivers.R;

public class Empowerment extends Fragment {

    private TextView tv;
    private EditText et;
    private View myFragmentView;

    public Empowerment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_empowerment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*RadioButton radioButton1 = (RadioButton) myFragmentView.findViewById(R.id.radio_1);
        radioButton1.setOnClickListener(radioButtonClickListener);
        RadioButton radioButton2 = (RadioButton) myFragmentView.findViewById(R.id.radio_2);
        radioButton2.setOnClickListener(radioButtonClickListener);

        tv = (TextView)myFragmentView.findViewById(R.id.tv5);
        tv.setVisibility(View.INVISIBLE);
        et = (EditText)myFragmentView.findViewById(R.id.et1);
        et.setVisibility(View.INVISIBLE);

    }
    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton)view;
            switch (rb.getId()){
                case R.id.radio_2:
                    tv = (TextView)myFragmentView.findViewById(R.id.tv5);
                    tv.setVisibility(View.VISIBLE);
                    et = (EditText)myFragmentView.findViewById(R.id.et1);
                    et.setVisibility(View.VISIBLE);
                    et.setText("");
                    break;
                case R.id.radio_1:
                    tv = (TextView)myFragmentView.findViewById(R.id.tv5);
                    tv.setVisibility(View.INVISIBLE);
                    et = (EditText)myFragmentView.findViewById(R.id.et1);
                    et.setVisibility(View.INVISIBLE);
                    break;
            }
        }*/
    };
}
