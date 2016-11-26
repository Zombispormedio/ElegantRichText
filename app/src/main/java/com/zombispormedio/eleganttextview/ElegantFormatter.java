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

    private LinkedHashMap<String,ArrayList<int[]>> positions;

    private String raw;


    public ElegantFormatter(String raw, String startPoint, String endPoint) {
        this.raw=raw;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.positions=new LinkedHashMap<>();
    }

    public ElegantFormatter(String raw) {
        this.raw=raw;
        this.positions=new LinkedHashMap<>();
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setRaw(String raw) {
        this.raw = raw;
        resolveBindingLocations();
    }

    public void resolveBindingLocations(){
        positions.clear();

        StringBuilder maker=new StringBuilder(raw);

        int startPosition=maker.indexOf(startPoint);

        while(startPosition>-1){

        }

    }

    public LinkedHashMap<String, ArrayList<int[]>> getPositions() {
        return positions;
    }
}
