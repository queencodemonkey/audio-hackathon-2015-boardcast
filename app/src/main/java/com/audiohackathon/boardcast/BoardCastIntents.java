package com.audiohackathon.boardcast;

import android.content.Context;
import android.content.Intent;

import com.audiohackathon.boardcast.model.Clip;
import com.audiohackathon.boardcast.model.Collection;
import com.audiohackathon.boardcast.ui.BoardActivity;
import com.audiohackathon.boardcast.ui.ClipActivity;
import com.audiohackathon.boardcast.ui.CollectionListActivity;

/**
 * @since 2015.09.19
 */
public class BoardCastIntents {
    public static final String EXTRA_COLLECTION = "com.audiohackathon.broadcast.intent.COLLECTION";
    public static final String EXTRA_CLIP = "com.audiohackathon.broadcast.intent.CLIP";

    public static Intent newCollectionListIntent(Context context) {
        return new Intent(context, CollectionListActivity.class);
    }

    public static Intent newBoardIntent(Context context, Collection collection) {
        final Intent intent =  new Intent(context, BoardActivity.class);
        intent.putExtra(EXTRA_COLLECTION, collection);
        return intent;
    }

    public static Intent newClipIntent(Context context, Clip clip) {
        final Intent intent = new Intent(context, ClipActivity.class);
        intent.putExtra(EXTRA_CLIP, clip);
        return intent;
    }
}
