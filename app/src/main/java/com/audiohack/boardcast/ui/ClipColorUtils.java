package com.audiohack.boardcast.ui;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * @since 2015.09.20
 */
public class ClipColorUtils {
    public static boolean shouldUseDarkText(@ColorInt int backgroundColor) {
        // Counting the perceptive luminance - human eye favors green color...
        final double value = 1 - ( 0.299 * Color.red(backgroundColor)
                + 0.587 * Color.green(backgroundColor)
                + 0.114 * Color.blue(backgroundColor))
                /255;
        return value < 0.5;
    }

    //
    // Constructors
    //

    /**
     * Private constructor to prevent instantiation.
     */
    private ClipColorUtils() {
    }
}
