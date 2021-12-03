package com.yuki.experiment.common.utils;

import java.util.Collection;

public class EmptyUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0 || "".equals(string.trim());
    }
}
