package com.zombispormedio.eleganttextview;

import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xavierserrano on 23/11/16.
 */

public class StyleableValue {

    private CharSequence value;

    private Set<String> styles;

    public StyleableValue() {
        this.styles=new HashSet<>();
        this.value="";
    }

    public StyleableValue(CharSequence value) {
        this.value = value;
        this.styles=new HashSet<>();
    }

    public void addStyle(String... s){
        Collections.addAll(styles, s);
    }

    public CharSequence getValue() {
        return value;
    }

    public Set<String> getStyles() {
        return styles;
    }

    public void setValue(CharSequence value) {
        this.value = value;
    }

    public CharSequence applyFilters(StyleContext ctx){
        return Stream.of(styles).reduce(value, (memo, keyStyle) -> ctx.applyFilter(keyStyle, memo));
    }
}
