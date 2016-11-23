package com.zombispormedio.eleganttextview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Function;

import java.util.List;

/**
 * Created by xavierserrano on 23/11/16.
 */

public class ElegantUtils {

    public static class Style {

        public static final String BOLD = "bold";

        public static final String COLOR = "color";

        public static final String color(String hex) {
            return COLOR + ":" + hex;
        }

    }


    public static CharSequence bold(CharSequence sequence) {
        SpannableString spannableString = new SpannableString(sequence);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, sequence.length(), 0);

        return spannableString;
    }

    public static CharSequence foregroundColor(CharSequence sequence, List<String> color) {
        SpannableString spannableString = new SpannableString(sequence);
        int hex = Color.parseColor(color.get(0));

        spannableString.setSpan(new ForegroundColorSpan(hex), 0, sequence.length(), 0);

        return spannableString;
    }


    public static class AbstractFilter<F> {
        private F fn;

        public AbstractFilter(F fn) {
            this.fn = fn;
        }

        public F getFunction() {
            return fn;
        }
    }

    public static class Filter extends AbstractFilter<Function<CharSequence, CharSequence>> {

        public Filter(Function<CharSequence, CharSequence> fn) {
            super(fn);
        }


    }

    public static class ArgsFilter extends AbstractFilter<BiFunction<CharSequence, List<String>, CharSequence>> {

        public ArgsFilter(BiFunction<CharSequence, List<String>, CharSequence> fn) {
            super(fn);
        }
    }
}
