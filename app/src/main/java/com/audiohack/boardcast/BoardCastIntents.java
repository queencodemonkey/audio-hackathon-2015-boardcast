package com.audiohack.boardcast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.audiohack.boardcast.model.Clip;
import com.audiohack.boardcast.model.Collection;
import com.audiohack.boardcast.ui.BoardActivity;
import com.audiohack.boardcast.ui.ClipActivity;
import com.audiohack.boardcast.ui.CollectionListActivity;

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
        final Intent intent = new Intent(context, BoardActivity.class);
        intent.putExtra(EXTRA_COLLECTION, collection);
        return intent;
    }

    public static Intent newClipIntent(Context context, Clip clip) {
        final Intent intent = new Intent(context, ClipActivity.class);
        intent.putExtra(EXTRA_CLIP, clip);
        return intent;
    }

    public static Intent newViewURLIntent(Context context, String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }
}
