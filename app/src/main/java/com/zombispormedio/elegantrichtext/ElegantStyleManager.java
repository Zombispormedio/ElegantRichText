package com.zombispormedio.elegantrichtext;

import android.text.SpannableString;

import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xavierserrano on 24/11/16.
 */
public class ElegantStyleManager implements StyleContext {

    public static class Style {

        public static final String BOLD = "bold";

        public static final String FOREGROUND_COLOR = "foreground_color";

        public static String foregroundColor(String hex) {
            return FOREGROUND_COLOR + ":" + hex;
        }

        public static final String BACKGROUND_COLOR = "background_color";

        public static String backgroundColor(String hex) {
            return BACKGROUND_COLOR + ":" + hex;
        }

        public static final String TEXT_CENTER="text_center";

    }


    private static ElegantStyleManager ourInstance = new ElegantStyleManager();

    public static ElegantStyleManager getInstance() {
        return ourInstance;
    }

    private LinkedHashMap<String, ElegantUtils.AbstractFilter> defaultFilters;

    private LinkedHashMap<String, ElegantUtils.AbstractFilter> customFilters;

    private boolean preferenceToDefault;

    private ElegantStyleManager() {
        defaultFilters = new LinkedHashMap<>();
        customFilters = new LinkedHashMap<>();
        preferenceToDefault=false;
        configureDefaultFilters();
    }

    private void configureDefaultFilters() {
        addDefaultFilter(Style.BOLD, ElegantUtils::bold);
        addDefaultFilter(Style.FOREGROUND_COLOR, ElegantUtils::foregroundColor);
        addDefaultFilter(Style.BACKGROUND_COLOR, ElegantUtils::backgroundColor);
        addDefaultFilter(Style.TEXT_CENTER, ElegantUtils::centerText);
    }

    private void addDefaultFilter(String key, Function<SpannableString, SpannableString> function) {
        defaultFilters.put(key, new ElegantUtils.Filter(function));
    }

    private void addDefaultFilter(String key, BiFunction<SpannableString, List<String>, SpannableString> function) {
        defaultFilters.put(key, new ElegantUtils.ArgsFilter(function));
    }

    public ElegantStyleManager filter(String key, Function<SpannableString, SpannableString> function){
        customFilters.put(key, new ElegantUtils.Filter(function));
        return this;
    }

    public ElegantStyleManager filter(String key, BiFunction<SpannableString, List<String>, SpannableString> function) {
        defaultFilters.put(key, new ElegantUtils.ArgsFilter(function));
        return this;
    }

    public void setPreferenceToDefault(boolean preferenceToDefault) {
        this.preferenceToDefault = preferenceToDefault;
    }

    public void removeFilter(String key){
        customFilters.remove(key);
    }

    public ElegantUtils.AbstractFilter getFilter(String key){
        boolean withDefault=false;
        if(preferenceToDefault){

            if(defaultFilters.containsKey(key)){
                withDefault=true;
            }

        }else{
            if(!customFilters.containsKey(key)){
                withDefault=true;
            }
        }

        return withDefault?defaultFilters.get(key):customFilters.get(key);
    }

    public boolean haveFilter(String key){
        return defaultFilters.containsKey(key) || customFilters.containsKey(key);
    }


    @Override
    public SpannableString applyFilter(CharSequence value, String key) {
        String realKey = key;
        SpannableString result = new SpannableString(value);
        ArrayList<String> args = new ArrayList<>();
        if (key.contains(":")) {
            String[] elems = key.split(":");
            realKey = elems[0];
            Collections.addAll(args, elems[1].split(","));
        }

        if (haveFilter(realKey)) {
            ElegantUtils.AbstractFilter filter = getFilter(realKey);

            if (args.size() > 0 && filter instanceof ElegantUtils.ArgsFilter) {

                result = ((ElegantUtils.ArgsFilter) filter)
                        .getFunction()
                        .apply(result, args);
            } else {
                if (filter instanceof ElegantUtils.Filter) {
                    result = ((ElegantUtils.Filter) filter).getFunction().apply(result);
                }
            }
        }
        return result;
    }
}
