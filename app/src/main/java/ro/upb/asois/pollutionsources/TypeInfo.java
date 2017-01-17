package ro.upb.asois.pollutionsources;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TypeInfo extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_info);

        txt = (TextView) findViewById(R.id.textView);
        txt.setText(savedInstanceState.get("info").toString());

    }
}
