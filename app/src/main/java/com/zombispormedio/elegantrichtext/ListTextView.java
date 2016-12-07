package com.mufumbo.android.recipe.search.views.components;

import com.mufumbo.android.recipe.search.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xavierserrano on 7/12/16.
 */

public class ListTextView extends LinearLayout {

    private static final String curlyPattern = "\\{([^}]*)\\}";

    private int bulletSize;

    private int bulletStyle;

    private int marginInnerElementsBottom;

    private String text;

    private int textColor;

    private int textSize;

    public ListTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(attrs);
        render();
    }

    public ListTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(attrs);
        render();
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ListTextView, 0, 0);

        try {

            bulletSize = attributes.getInteger(R.styleable.ListTextView_bulletSize, BulletTextView.DEFAULT_BULLET_SIZE);

            marginInnerElementsBottom = attributes
                    .getDimensionPixelSize(R.styleable.ListTextView_marginBetweenElementsBottom,
                            (int) getResources().getDimension(R.dimen.spacing_large));

            text = attributes.getString(R.styleable.ListTextView_text);

            bulletStyle=attributes.getInt(R.styleable.ListTextView_bulletStyle, 0);

            textColor=attributes.getColor(R.styleable.ListTextView_textColor, ContextCompat.getColor(getContext(), R.color.black));

            textSize=attributes.getDimensionPixelOffset(R.styleable.ListTextView_textSize, getResources().getDimensionPixelSize(R.dimen.text_size_small));

        } finally {
            attributes.recycle();
        }
    }

    private void render() {
        ArrayList<String> elems = getElemsList();
        int size = elems.size();
        for (int i = 0; i < size; i++) {
            TextView view = createTextView(elems.get(i));

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i < size - 1) {
                params.setMargins(0, 0, 0, marginInnerElementsBottom);
            }
            view.setLayoutParams(params);
            addView(view);
        }
    }

    private TextView createTextView(String elem){
        TextView textView;

        switch (bulletStyle){
            case 1: textView=new TextView(getContext());
                textView.setText(elem);
              break;
            case 0:
            default:
                textView=new BulletTextView(getContext(), elem);
                break;
        }

        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        return  textView;
    }

    private ArrayList<String> getElemsList() {
        ArrayList<String> elems = new ArrayList<>();
        Pattern pattern = Pattern.compile(curlyPattern);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String elem = matcher.group(1);
            elems.add(elem);
        }

        return elems;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getBulletSize() {
        return bulletSize;
    }

    public void setBulletSize(int bulletSize) {
        this.bulletSize = bulletSize;
    }
}
