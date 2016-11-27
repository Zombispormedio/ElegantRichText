package com.zombispormedio.elegantrichtext;

import android.text.SpannableString;

import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Function;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xavierserrano on 24/11/16.
 */
public class ElegantStyleManager {
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
        addDefaultFilter(ElegantUtils.Style.BOLD, ElegantUtils::bold);
        addDefaultFilter(ElegantUtils.Style.FOREGROUND_COLOR, ElegantUtils::foregroundColor);
        addDefaultFilter(ElegantUtils.Style.BACKGROUND_COLOR, ElegantUtils::backgroundColor);
        addDefaultFilter(ElegantUtils.Style.TEXT_CENTER, ElegantUtils::centerText);
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
}
