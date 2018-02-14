package com.example.kelly.parsing;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final EditText editText = (EditText) findViewById(R.id.edit);
       final TextView textView = (TextView) findViewById(R.id.text);
       final Button get = (Button) findViewById(R.id.get);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place;

                if(editText.getText().toString().length()>0){
                    place = editText.getText().toString();
                }
                else{
                    place ="";
                }

                new GetWeather(textView, place).execute();

            }
        });

    }
}
