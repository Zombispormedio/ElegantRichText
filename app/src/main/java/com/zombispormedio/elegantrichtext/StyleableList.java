package com.zombispormedio.elegantrichtext;

import android.text.SpannableString;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



/**
 * Created by xavierserrano on 4/12/16.
 */

public class StyleableList implements Styleable<ArrayList<CharSequence>> {

    private ArrayList<StyleableValue> values;

    private Sentence.Joiner joiner;


    public StyleableList(ArrayList<StyleableValue> values) {
        this.values = values;
        this.joiner=Sentence.defaultJoiner;
    }

    public StyleableList() {
        this.values=new ArrayList<>();
        this.joiner=Sentence.defaultJoiner;
    }

    public StyleableList add(StyleableValue... value){
        Collections.addAll(values, value);
        return this;
    }

    public StyleableList add(List<StyleableValue> value){
        values.addAll(value);
        return this;
    }

    public StyleableList put(CharSequence v, String... styles){
        StyleableValue value=new StyleableValue(v);
        value.addStyle(styles);
        values.add(value);
        return this;
    }

    public Sentence.Joiner getJoiner() {
        return joiner;
    }

    public StyleableList setJoiner(Sentence.Joiner joiner) {
        this.joiner = joiner;
        return this;
    }

    public StyleableList setJoiner(String betweenElements, String twoElements, String moreThanTwoElements){
        this.joiner=new Sentence.Joiner(betweenElements, twoElements,moreThanTwoElements);
        return this;
    }

    @Override
    public ArrayList<CharSequence> applyStyles() {
        return Stream.of(values).map(StyleableValue::applyStyles)
                .collect(Collectors.toCollection(ArrayList<CharSequence>::new));
    }

    @Override
    public void addStyle(String... s) {
        this.values=Stream.of(values).map(styleableValue -> {
            styleableValue.addStyle(s);
            return styleableValue;
        }).collect(Collectors.toCollection(ArrayList<StyleableValue>::new));
    }

    @Override
    public void addStyle(HashSet<String> s) {
        this.values=Stream.of(values).map(styleableValue -> {
            styleableValue.addStyle(s);
            return styleableValue;
        }).collect(Collectors.toCollection(ArrayList<StyleableValue>::new));
    }

    public StyleableList bold(){
        addStyle(ElegantStyleManager.Style.BOLD);
        return this;
    }

    public StyleableList foregroundColor(String hex){
        addStyle(ElegantStyleManager.Style.foregroundColor(hex));
        return this;
    }

    public StyleableList backgroundColor(String hex){
        addStyle(ElegantStyleManager.Style.backgroundColor(hex));
        return this;
    }

    public StyleableList textCenter(){
        addStyle(ElegantStyleManager.Style.TEXT_CENTER);
        return this;
    }
}
