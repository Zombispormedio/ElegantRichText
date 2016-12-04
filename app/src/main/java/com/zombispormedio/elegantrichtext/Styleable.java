package com.zombispormedio.elegantrichtext;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by xavierserrano on 4/12/16.
 */

public interface Styleable<T> extends ElegantUtils.WithStyle<Styleable>{

    T applyStyles();

    void addStyle(String... s);

    void addStyle(HashSet<String> s);
}
