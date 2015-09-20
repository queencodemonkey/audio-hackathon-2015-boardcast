package com.audiohack.boardcast.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audiohack.boardcast.BoardCastIntents;
import com.audiohack.boardcast.R;
import com.audiohack.boardcast.model.Clip;
import com.audiohack.boardcast.model.Collection;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity for displaying a BoardCast collection as a board.
 */
public class BoardActivity extends AppCompatActivity {
    //
    // Constants
    //

    /**
     * Collection displayed on board.
     */
    private static final String STATE_COLLECTION = "collection";

    private static final int COLUMN_COUNT = 3;

    //
    // Fields
    //

    @Bind(R.id.app_bar)
    Toolbar appBar;

    @Bind(R.id.board_grid)
    RecyclerView boardView;

    private Collection mCollection;
    private BoardAdapter mAdapter;

    //
    // Activity callbacks
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ButterKnife.bind(this);

        // Grab collection either from Intent or from saved state.
        mCollection = (Collection) (savedInstanceState != null
                ? savedInstanceState.getParcelable(STATE_COLLECTION)
                : getIntent().getParcelableExtra(BoardCastIntents.EXTRA_COLLECTION)
        );

        if (mCollection == null) {
            throw new IllegalStateException("Collection is null.");
        }

        // Set up app bar.
        appBar.setTitle(mCollection.title != null && !mCollection.title.isEmpty()
                ? mCollection.title
                : getString(R.string.title_activity_board)
        );
        setSupportActionBar(appBar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up board view.
        boardView.setLayoutManager(new GridLayoutManager(this, COLUMN_COUNT));
        mAdapter = new BoardAdapter(this);
        boardView.setAdapter(mAdapter);

        // Put clips inside board.
        if (mCollection.clips != null) {
            mAdapter.setClips(mCollection.clips);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(STATE_COLLECTION, mCollection);
    }

    //
    // Inner classes
    //

    static class BoardAdapter extends RecyclerView.Adapter<ClipViewHolder> {

        private final LayoutInflater mInflater;
        private List<Clip> mClips;

        public BoardAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mClips = Collections.emptyList();
        }

        public void setClips(List<Clip> clips) {
            mClips = clips;
            notifyDataSetChanged();
        }

        @Override
        public ClipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ClipViewHolder(mInflater.inflate(R.layout.grid_item_clip, parent, false));
        }

        @Override
        public void onBindViewHolder(ClipViewHolder holder, int position) {
            holder.setClip(mClips.get(position));
        }

        @Override
        public int getItemCount() {
            return mClips.size();
        }
    }

    static class ClipViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.poster)
        ImageView posterView;

        @Bind(R.id.text)
        TextView textView;

        private Clip mClip;

        public ClipViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void setClip(Clip clip) {
            mClip = clip;

            Glide.with(itemView.getContext())
                    .load(clip.posterUrl)
                    .crossFade()
                    .centerCrop()
                    .into(posterView);

            if (mClip.transcript != null) {
                textView.setText(mClip.transcript);
            } else {
                textView.setText(mClip.showTitle);
            }
        }

        @Override
        public void onClick(View v) {
            final Context context = itemView.getContext();
            context.startActivity(BoardCastIntents.newClipIntent(context, mClip));
        }
    }
}
