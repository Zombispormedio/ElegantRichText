package com.mufumbo.android.recipe.search.views.components;

import com.mufumbo.android.recipe.search.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class BulletTextView extends TextView {

    public static int DEFAULT_BULLET_SIZE = 15;

    private int bulletSize;

    public BulletTextView(Context context, String text) {
        super(context);
        this.bulletSize = DEFAULT_BULLET_SIZE;
        setText(text);
        render();
    }

    public BulletTextView(Context context, int bulletSize, String text) {
        super(context);
        this.bulletSize = bulletSize;
        setText(text);
        render();
    }

    public BulletTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(attrs);
        render();
    }

    public BulletTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(attrs);
        render();
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BulletTextView, 0, 0);

        try {
            bulletSize = attributes.getInteger(R.styleable.ListTextView_bulletSize, DEFAULT_BULLET_SIZE);

        } finally {
            attributes.recycle();
        }
    }

    private void render() {
        CharSequence text = getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        SpannableString spannable = new SpannableString(text);
        spannable.setSpan(new BulletSpan(15), 0, text.length(), 0);
        setText(spannable);

    }
}
