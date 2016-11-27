package com.zombispormedio.elegantrichtext;

import android.text.SpannableString;

/**
 * Created by xavierserrano on 23/11/16.
 */

public interface StyleContext {

    SpannableString applyFilter(CharSequence value, String key);
}
