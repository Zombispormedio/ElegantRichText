package com.zombispormedio.elegantrichtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import static com.zombispormedio.elegantrichtext.ElegantStyleManager.Style;

/**
 * Created by xavierserrano on 23/11/16.
 */

public class ElegantTextView extends TextView implements ElegantUtils.WithStyle<ElegantTextView>{

    private Sentence sentence;

    private LinkedHashMap<String, Styleable> values;

    private HashSet<String> filtersToCompoundInValues;

    private HashSet<String> globals;

    public ElegantTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sentence = new Sentence();
        obtainAttributes(context, attrs);

        values = new LinkedHashMap<>();

        globals = new HashSet<>();

        filtersToCompoundInValues = new HashSet<>();

    }

    private void obtainAttributes(Context context, AttributeSet attrs) {

        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ElegantTextView,
                0, 0);

        try {
            String temp = styledAttributes.getString(R.styleable.ElegantTextView_template);
            sentence.setTemplate(temp);
        } finally {
            styledAttributes.recycle();
        }

    }

    public ElegantTextView template(String mTemplate) {
        sentence.setTemplate(mTemplate);
        return this;
    }


    public ElegantTextView bind(String key, CharSequence value) {
        if (values.containsKey(key)) {
            Styleable st = values.get(key);
            if (st instanceof StyleableValue) {
                ((StyleableValue) st).setValue(value);
            }
        } else {
            values.put(key, new StyleableValue(value));
        }

        return this;
    }

    public ElegantTextView bind(String key, CharSequence v, String... styles) {

        if (!values.containsKey(key)) {
            StyleableValue value = new StyleableValue(v);
            values.put(key, value);
            value.addStyle(styles);
        } else {
            Styleable value = values.get(key);
            if (value instanceof StyleableValue) {
                ((StyleableValue) value).setValue(v);
                value.addStyle(styles);
            }
        }

        return this;

    }

    public ElegantTextView bindStyle(String key, String... styles) {
        Styleable value;

        if (values.containsKey(key)) {
            value = values.get(key);
        } else {
            value = new StyleableValue();
            values.put(key, value);
        }

        value.addStyle(styles);

        return this;
    }


    public ElegantTextView bind(String key, List<CharSequence> v, String... styles) {
        Styleable styleable;

        if (values.containsKey(key)) {
            styleable = values.get(key);
        } else {
            styleable = new StyleableList();
            values.put(key, styleable);
        }

        if (styleable instanceof StyleableList) {
            Stream.of(v)
                    .forEach(charSequence -> ((StyleableList) styleable).put(charSequence, styles));
        }

        return this;

    }

    public ElegantTextView bind(String key, List<CharSequence> v, Sentence.Joiner joiner, String... styles) {
        Styleable styleable;

        if (values.containsKey(key)) {
            styleable = values.get(key);
        } else {
            styleable = new StyleableList();
            values.put(key, styleable);
        }

        if (styleable instanceof StyleableList) {
            Stream.of(v)
                    .forEach(charSequence -> ((StyleableList) styleable).put(charSequence, styles));
            ((StyleableList) styleable).setJoiner(joiner);
        }

        return this;

    }

    public ElegantTextView bind(String key, List<StyleableValue> v) {
        Styleable styleable;

        if (values.containsKey(key)) {
            styleable = values.get(key);
        } else {
            styleable = new StyleableList();
            values.put(key, styleable);
        }

        if (styleable instanceof StyleableList) {
            ((StyleableList) styleable).add(v);
        }

        return this;

    }


    public ElegantTextView bind(String key, Styleable styleable) {
        values.put(key, styleable);
        return this;
    }


    public ElegantTextView addGlobal(String... keys) {
        Collections.addAll(globals, keys);
        return this;
    }


    public ElegantTextView compose(ElegantUtils.StyleCompound compound) {
        filtersToCompoundInValues.addAll(compound.getStyles());
        globals.addAll(compound.getGlobalStyles());
        return this;
    }

    public ElegantTextView removeBindingValue(String key) {
        values.remove(key);
        return this;
    }

    public ElegantTextView removeGlobal(String key) {
        globals.remove(key);
        return this;
    }

    public ElegantTextView bindingPoints(String startPoint, String endPoint) {
        sentence.setPoints(startPoint, endPoint);
        return this;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public ElegantTextView apply() {
        if (!sentence.hasTemplate()) return this;


        SpannableStringBuilder builder = buildBinding();

        builder = buildGlobal(builder);

        setText(builder, BufferType.SPANNABLE);
        return this;

    }

    private SpannableStringBuilder buildGlobal(SpannableStringBuilder builder) {
        SpannableStringBuilder newBuilder = new SpannableStringBuilder();

        CharSequence result = Stream.of(globals).reduce(builder, ElegantStyleManager.getInstance()::applyFilter);

        newBuilder.append(result);

        return newBuilder;
    }

    private SpannableStringBuilder buildBinding() {

        return Stream.of(values).reduce(sentence, (memo, entry) -> {

            String key = entry.getKey();
            Styleable styleable = entry.getValue();
            styleable.addStyle(filtersToCompoundInValues);

            if (styleable instanceof StyleableValue) {
                return memo.put(key, ((StyleableValue) styleable).applyStyles());
            } else if (styleable instanceof StyleableList) {
                return memo.put(key, ((StyleableList) styleable).applyStyles(), ((StyleableList) styleable).getJoiner());
            }

            return memo;


        }).apply();
    }


    public ElegantTextView bold(){
        addGlobal(Style.BOLD);
        return this;
    }

    public ElegantTextView foregroundColor(String hex){
        addGlobal(Style.foregroundColor(hex));
        return this;
    }

    public ElegantTextView backgroundColor(String hex){
        addGlobal(Style.backgroundColor(hex));
        return this;
    }

    public ElegantTextView textCenter(){
        addGlobal(Style.TEXT_CENTER);
        return this;
    }
}
