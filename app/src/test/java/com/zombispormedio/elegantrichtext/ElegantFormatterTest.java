package com.zombispormedio.elegantrichtext;

/**
 * Created by xavierserrano on 26/11/16.
 */



import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ElegantFormatterTest {

    public final String firstString="Hola, {name}, Lorem ipsum dolor sit amet, consectetur adipiscin" +
            "g elit. Integer eget tortor velit. {name} tincidunt consectetur pharetra. Ut tem" +
            "por lacinia urna commodo blandit. {hello} ac nibh augue. Sed {list} erat hendrerit posuere porta.";




    @Test
    public void testApply(){
        final ElegantFormatter formatter=new ElegantFormatter(firstString);
        ArrayList<CharSequence> list=new ArrayList<>();
        list.add("hola1");
        list.add("hola2");
        list.add("hola3");

        String result=formatter.put("name", "Xavier")
                .put("hello", "Bienvenido")
                .put("list", list, new ElegantFormatter.Joiner(", ", " y ", " y "))
                .apply().toString();

        assertEquals("Hola, Xavier, Lorem ipsum dolor sit amet, consectetur adipiscin" +
                "g elit. Integer eget tortor velit. Xavier tincidunt consectetur pharetra. Ut tem" +
                "por lacinia urna commodo blandit. Bienvenido ac nibh augue. Sed hola1, hola2 y hola3 erat hendrerit posuere porta.", result);

    }

    @Test
    public void testJoiner(){
        ElegantFormatter.Joiner joiner=new ElegantFormatter.Joiner(", ", " y ", " y ");

        assertEquals("el, ella y yo", joiner.join("el", "ella", "yo").toString());
    }

}
