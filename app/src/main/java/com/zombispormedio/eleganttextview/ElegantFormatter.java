package com.zombispormedio.eleganttextview;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by xavierserrano on 24/11/16.
 */

public class ElegantFormatter {

    private static final String defaultStartPoint = "{";

    private static final String defaultEndPoint= "}";

    private String startPoint=defaultStartPoint;

    private String endPoint=defaultEndPoint;

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public LinkedHashMap<String, ArrayList<int[]>> resolveBindingLocation(String raw){
        LinkedHashMap<String, ArrayList<int[]>> locations=new LinkedHashMap<>();
        

        return locations;
    }
}
