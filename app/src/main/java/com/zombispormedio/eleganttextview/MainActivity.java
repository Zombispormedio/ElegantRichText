package com.zombispormedio.eleganttextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ElegantTextView textView;

    private String[] myStyle=new String[]{ElegantUtils.Style.foregroundColor("#8E24AA")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (ElegantTextView) findViewById(R.id.text);

        textView.bind("name", "Xavier", myStyle)
                .bind("specie", "Elefante", ElegantUtils.Style.backgroundColor("#8E24AA"), ElegantUtils.Style.foregroundColor("#ECEFF1"))
                .addGlobal(ElegantUtils.Style.BOLD, ElegantUtils.Style.TEXT_CENTER)
                .apply();

        


    }


}
