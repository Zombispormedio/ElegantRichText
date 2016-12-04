package com.zombispormedio.elegantrichtext;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.annimon.stream.function.BiFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xavierserrano on 24/11/16.
 */

public class Sentence {

    private static final String defaultStartPoint = "{";

    private static final String defaultEndPoint = "}";

    private String startPoint = defaultStartPoint;

    private String endPoint = defaultEndPoint;

    private HashMap<String, Value> values;

    public static Joiner defaultJoiner=new Joiner(", ", " and ", ", and");

    public Joiner globalJoiner;

    private String template;


    public Sentence(String template, String startPoint, String endPoint) {
        this.template = template;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        setup();
    }

    public Sentence() {
        setup();
    }

    public Sentence(String template) {
        this.template = template;
        setup();
    }

    private void setup() {
        this.values = new HashMap<>();
        this.globalJoiner=defaultJoiner;
    }

    public Sentence setPoints(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        return this;
    }

    public Sentence setTemplate(String template) {
        this.template = template;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public boolean hasTemplate() {
        if (template == null) return false;
        if (template.isEmpty()) return false;
        return true;
    }

    public SpannableStringBuilder apply() {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        StringBuilder maker = new StringBuilder(template);

        int startPosition = maker.indexOf(startPoint);

        while (startPosition > -1) {
            int endPosition = maker.indexOf(endPoint);

            String key = maker.substring(startPosition + 1, endPosition).trim();

            String rest = maker.substring(0, startPosition);

            builder.append(rest);

            CharSequence bindingValue = values.get(key).getValue();

            builder.append(bindingValue);


            maker = new StringBuilder(maker.substring(endPosition + 1));

            startPosition = maker.indexOf(startPoint);

        }

        builder.append(maker);

        return builder;

    }


    public Sentence put(String key, CharSequence value) {
        values.put(key, new CharSequenceValue(value));
        return this;
    }

    public Sentence put(String key, ArrayList<CharSequence> value) {
        values.put(key, new ListCharSequenceValue(value, globalJoiner));
        return this;
    }

    public Sentence put(String key, ArrayList<CharSequence> value, Joiner joiner) {
        values.put(key, new ListCharSequenceValue(value, joiner));
        return this;
    }

    private BiFunction<SpannableStringBuilder, int[], SpannableStringBuilder> bindFunction(CharSequence value) {
        return (b, l) -> b.replace(l[0], l[1] + 1, value);
    }


    public Sentence setGlobalJoiner(Joiner globalJoiner) {
        this.globalJoiner = globalJoiner;
        return this;
    }

    public Sentence setGlobalJoiner(String between, String two, String moreThanTwo) {
        this.globalJoiner = new Joiner(between, two, moreThanTwo);
        return this;
    }


    public static Sentence from(Context context, int id) {
        return new Sentence(context.getString(id));
    }

    public void into(TextView textView){
        textView.setText(apply());
    }

    private abstract class Value<V extends CharSequence> {
        private V value;

        public Value(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private class CharSequenceValue extends Value<CharSequence> {

        public CharSequenceValue(CharSequence value) {
            super(value);
        }

    }

    private class ListCharSequenceValue extends Value<CharSequence> {


        private ArrayList<CharSequence> values;
        private Joiner joiner;

        public ListCharSequenceValue(ArrayList<CharSequence> values, Joiner joiner) {
            super(joiner.join(values));
            this.values = values;
            this.joiner = joiner;
        }

        public void setValues(ArrayList<CharSequence> values) {
            this.values = values;
            setValue(joiner.join(values));
        }


        public void setJoiner(Joiner joiner) {
            this.joiner = joiner;
            setValue(joiner.join(values));
        }
    }

    public static class Joiner {

        private String betweenElements;

        private String twoElements;

        private String moreThanTwoElements;

        public Joiner(String betweenElements, String twoElements, String moreThanTwoElements) {
            this.betweenElements = betweenElements;
            this.twoElements = twoElements;
            this.moreThanTwoElements = moreThanTwoElements;
        }

        public SpannableStringBuilder join(CharSequence... elems) {
            return join(Arrays.asList(elems));
        }

        public SpannableStringBuilder join(List<CharSequence> elems) {
            if (elems.size() > 2) {
                return joinMoreThanTwo(elems);
            } else if (elems.size() == 2) {
                return joinTwo(elems);

            } else if (elems.size() == 1) {
                return new SpannableStringBuilder(elems.get(0));
            } else {
                return new SpannableStringBuilder();
            }
        }

        private SpannableStringBuilder joinTwo(List<CharSequence> elems) {
            return new SpannableStringBuilder()
                    .append(elems.get(0))
                    .append(twoElements)
                    .append(elems.get(1));
        }

        private SpannableStringBuilder joinMoreThanTwo(List<CharSequence> elems) {
            SpannableStringBuilder builder = new SpannableStringBuilder();

            int len = elems.size();
            int last = len - 1;
            int penultimate = len - 2;

            for (int i = 0; i < penultimate; i++) {
                builder = builder.append(elems.get(i))
                        .append(betweenElements);
            }

            return builder
                    .append(elems.get(penultimate))
                    .append(moreThanTwoElements)
                    .append(elems.get(last));
        }
    }
}
