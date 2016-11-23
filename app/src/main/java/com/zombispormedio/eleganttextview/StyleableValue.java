package com.zombispormedio.eleganttextview;

import android.text.SpannableString;

import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xavierserrano on 23/11/16.
 */

public class StyleableValue {

    private SpannableString value;

    private Set<String> styles;

    public StyleableValue() {
        this.styles=new HashSet<>();
        this.value=new SpannableString("");
    }

    public StyleableValue(CharSequence value) {
        this.value = new SpannableString(value);
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
        this.value = new SpannableString(value);
    }

    public SpannableString applyFilters(StyleContext ctx){
        return Stream.of(styles).reduce(value, ctx::applyFilter);
    }
}