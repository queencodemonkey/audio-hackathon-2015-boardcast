package com.audiohack.boardcast.ui;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.audiohack.boardcast.model.Clip;

import butterknife.ButterKnife;

/**
 * @since 2015.09.20
 */
public class ClipColorUtils {
    public static final ButterKnife.Setter<TextView, Clip> CLIP_TEXT_COLOR = new ButterKnife.Setter<TextView, Clip>() {
        @Override public void set(TextView textView, Clip clip, int index) {
            setTextColorForClip(textView, clip);
        }
    };

    public static int getClipColorValue(Clip clip) {
        return Color.parseColor(clip.color);
    }

    public static void setTextColorForClip(TextView textView, Clip clip) {
        if (shouldUseDarkText(getClipColorValue(clip))) {
            textView.setTextColor(Color.BLACK);
        } else {
            textView.setTextColor(Color.WHITE);
        }
    }

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
