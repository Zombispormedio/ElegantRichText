package com.zombispormedio.elegantrichtext;

import android.text.SpannableString;

import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xavierserrano on 4/12/16.
 */

public class StyleableValue implements Styleable<SpannableString>{

    private SpannableString value;

    private Set<String> styles;

    public StyleableValue(CharSequence value) {
        this.value=new SpannableString(value);
        this.styles=new HashSet<>();
    }

    public StyleableValue() {
        this.styles=new HashSet<>();
    }

    public void setValue(CharSequence value) {
        this.value=new SpannableString(value);
    }

    public void addStyle(String... s){
        Collections.addAll(styles, s);
    }

    public void addStyle(HashSet<String> s){
        styles.addAll(s);
    }

    public Set<String> getStyles() {
        return styles;
    }

    @Override
    public SpannableString applyStyles() {
        return Stream.of(styles).reduce(value, ElegantStyleManager.getInstance()::applyFilter);
    }

    public StyleableValue bold(){
        addStyle(ElegantStyleManager.Style.BOLD);
        return this;
    }

    public StyleableValue foregroundColor(String hex){
        addStyle(ElegantStyleManager.Style.foregroundColor(hex));
        return this;
    }

    public StyleableValue backgroundColor(String hex){
        addStyle(ElegantStyleManager.Style.backgroundColor(hex));
        return this;
    }

    public StyleableValue textCenter(){
        addStyle(ElegantStyleManager.Style.TEXT_CENTER);
        return this;
    }
}
