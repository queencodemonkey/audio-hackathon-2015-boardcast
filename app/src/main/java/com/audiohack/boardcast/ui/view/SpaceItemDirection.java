package com.audiohack.boardcast.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @since 2015.09.20
 */
public class SpaceItemDirection extends RecyclerView.ItemDecoration {
    private int spacing;

    public SpaceItemDirection(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.top = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
    }
}
