package com.zombispormedio.eleganttextview;

import android.text.SpannableString;

import java.util.function.Function;

/**
 * Created by xavierserrano on 23/11/16.
 */

public interface StyleContext {

    SpannableString applyFilter(CharSequence value, String key);
}
