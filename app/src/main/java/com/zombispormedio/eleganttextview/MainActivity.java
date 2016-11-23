package com.zombispormedio.eleganttextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ElegantTextView textView;

    private String[] myStyle=new String[]{ElegantUtils.Style.BOLD, ElegantUtils.Style.color("#F44336")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (ElegantTextView) findViewById(R.id.text);

        textView.template("Hello world, {name}")
                .bind("name", "Xavier", myStyle)
                .apply();

        


    }


}
