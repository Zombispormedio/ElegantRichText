package com.zombispormedio.eleganttextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xavierserrano on 23/11/16.
 */

public class ElegantTextView extends TextView{

    private String template;

    public ElegantTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {

        TypedArray styledAttributes=context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ElegantTextView,
                0,0);
        template=styledAttributes.getString(R.styleable.ElegantTextView_template);
    }

    public void setTemplate(String template) {
        this.template = template;
    }



}
