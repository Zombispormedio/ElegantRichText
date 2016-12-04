package com.zombispormedio.elegantrichtext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import static com.zombispormedio.elegantrichtext.ElegantStyleManager.Style;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] myStyle=new String[]{Style.foregroundColor("#8E24AA")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ElegantUtils.StyleCompound compound=new ElegantUtils.StyleCompound()
                .foregroundColor("#8E24AA")
                .textCenterGlobal();

        ElegantTextView textView = (ElegantTextView) findViewById(R.id.text);

        StyleableList list1=new StyleableList();
        StyleableValue styleableValue=new StyleableValue("Xavier")
                .backgroundColor("#64B5F6");
        StyleableValue styleableValue2=new StyleableValue("Estrella")
                .foregroundColor("#3F51B5");
        list1.add(styleableValue, styleableValue2);
        list1.setJoiner(", ", " y ", " y ");

        textView.bind("name", list1)
                .compose(compound)
                .bind("specie", "Elefante", Style.backgroundColor("#8E24AA"), Style.foregroundColor("#ECEFF1"))
                .bold()
                .apply();

        ArrayList<CharSequence> list=new ArrayList<>();
        list.add("1");
        list.add("2");

        ((ElegantTextView) findViewById(R.id.text2))
                .compose(compound)
                .bindingPoints("[", "]")
                .bind("name", list)
                .bold()
                .apply();
        


    }


}
