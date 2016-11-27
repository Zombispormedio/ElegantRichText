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
            "por lacinia urna commodo blandit. {hello} ac nibh augue. Sed ornare erat hendrerit posuere porta.";


    @Test
    public void resolveBindingPositions(){

        final ElegantFormatter formatter=new ElegantFormatter(firstString);

        assertEquals(2, formatter.getPositions().size());

        HashMap<String, ArrayList<int[]>> expectedPositions=mockExpectedPositions();

        HashMap<String, ArrayList<int[]>> testPositions=formatter.getPositions();

        for (String k:expectedPositions.keySet()) {

            assertTrue(testPositions.containsKey(k));

            ArrayList<int[]> expected=expectedPositions.get(k);
            ArrayList<int[]> test=testPositions.get(k);

            assertEquals(expected.size(), test.size());

            for(int i=0; i< test.size();i++){
                int [] p=test.get(i);
                assertEquals(k, firstString.substring(p[0]+1, p[1]));
            }
        }

    }

    private HashMap<String, ArrayList<int[]>> mockExpectedPositions(){
        HashMap<String, ArrayList<int[]>> expectedPositions=new HashMap<>();
        ArrayList<int[]> namePositions=new ArrayList<>();
        namePositions.add(new int[]{6, 11});
        namePositions.add(new int[]{});
        expectedPositions.put("name", namePositions);

        ArrayList<int[]> helloPositions=new ArrayList<>();
        helloPositions.add(new int[]{});
        expectedPositions.put("hello", helloPositions);
        return expectedPositions;
    }

    @Test
    public void testApply(){
        final ElegantFormatter formatter=new ElegantFormatter(firstString);

        String result=formatter.put("name", "Xavier")
                .put("hello", "Bienvenido")
                .apply().toString();

        assertEquals("Hola, Xavier, Lorem ipsum dolor sit amet, consectetur adipiscin" +
                "g elit. Integer eget tortor velit. Xavier tincidunt consectetur pharetra. Ut tem" +
                "por lacinia urna commodo blandit. Bienvenido ac nibh augue. Sed ornare erat hendrerit posuere porta.", result);

    }

    @Test
    public void testJoiner(){
        ElegantFormatter.Joiner joiner=new ElegantFormatter.Joiner(", ", " y ", "y ");

        assertEquals("el, ella, y yo", joiner.join("el", "ella", "yo").toString());
    }

}
