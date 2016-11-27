package com.zombispormedio.elegantrichtext;

import android.text.SpannableStringBuilder;

import com.annimon.stream.Stream;
import com.annimon.stream.function.BiFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xavierserrano on 24/11/16.
 */

public class ElegantFormatter {

    private static final String defaultStartPoint = "{";

    private static final String defaultEndPoint = "}";

    private String startPoint = defaultStartPoint;

    private String endPoint = defaultEndPoint;

    private HashMap<String, ArrayList<int[]>> positions;

    private HashMap<String, CharSequence> values;

    private String raw;


    public ElegantFormatter(String raw, String startPoint, String endPoint) {
        this.raw = raw;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.positions = new HashMap<>();
        this.values = new HashMap<>();
        resolve();
    }

    public ElegantFormatter(String raw) {
        this.raw = raw;
        this.positions = new LinkedHashMap<>();
        this.values = new HashMap<>();
        resolve();
    }

    public ElegantFormatter setPoints(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        return this;
    }

    public ElegantFormatter setRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public ElegantFormatter resolve() {
        positions.clear();

        StringBuilder maker = new StringBuilder(raw);

        int startPosition = maker.indexOf(startPoint);

        int offset = 0;

        while (startPosition > -1) {
            int endPosition = maker.indexOf(endPoint);

            String key = maker.substring(startPosition + 1, endPosition).trim();

            int realStartPosition = startPosition + offset;
            int realEndPosition = endPosition + offset;

            int[] loc = new int[]{realStartPosition, realEndPosition};
            if (positions.containsKey(key)) {
                positions.get(key).add(loc);

            } else {
                ArrayList<int[]> keyPositions = new ArrayList<>();
                keyPositions.add(loc);
                positions.put(key, keyPositions);
            }

            maker = new StringBuilder(maker.substring(endPosition + 1));

            offset += endPosition + 1;

            startPosition = maker.indexOf(startPoint);

        }


        return this;

    }

    public ElegantFormatter put(String key, CharSequence value) {
        values.put(key, value);
        return this;
    }

    public HashMap<String, ArrayList<int[]>> getPositions() {
        return positions;
    }

    public SpannableStringBuilder apply() {
        SpannableStringBuilder builder = new SpannableStringBuilder(raw);

        return Stream.of(positions.keySet())
                .reduce(builder, (memo, key) -> {
                    CharSequence v = values.get(key);

                    return Stream.of(positions.get(key))
                            .reduce(memo, bindFunction(v));
                });

    }


    private BiFunction<SpannableStringBuilder, int[], SpannableStringBuilder> bindFunction(CharSequence value) {
        return (b, l) -> b.replace(l[0], l[1] + 1, value);
    }


    private abstract class Value<V> {
        private V value;

        public Value(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }

    private class CharSequenceValue extends Value<CharSequence> {

        public CharSequenceValue(CharSequence value) {
            super(value);
        }
    }

    private class ListCharSequenceValue extends Value<ArrayList<CharSequence>> {

        public ListCharSequenceValue(ArrayList<CharSequence> value) {
            super(value);
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
                    .append(elems.get(0));
        }

        private SpannableStringBuilder joinMoreThanTwo(List<CharSequence> elems) {
            SpannableStringBuilder builder = new SpannableStringBuilder();

            int last=elems.size()-1;
            for(int i=0; i<last; i++){
                builder=builder.append(elems.get(i))
                        .append(betweenElements);
            }

            return builder
                    .append(moreThanTwoElements)
                    .append(elems.get(last));
        }
    }
}
