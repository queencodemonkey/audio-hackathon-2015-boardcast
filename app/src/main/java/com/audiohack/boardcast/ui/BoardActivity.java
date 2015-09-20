package com.audiohack.boardcast.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.audiohack.boardcast.audio.PlayerFragment;
import com.audiohack.boardcast.model.Clip;
import com.audiohack.boardcast.model.Collection;
import com.audiohack.boardcast.ui.view.SpaceItemDirection;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity for displaying a BoardCast collection as a board.
 */
public class BoardActivity extends AppCompatActivity implements PlayerFragment.PlayerListener {
    //
    // Constants
    //

    /**
     * Fragment tag for player.
     */
    private static final String FRAGMENT_TAG_PLAYER = "player";

    /**
     *
     */
    private static final String FRAGMENT_TAG_CLIP_FORMAT = "fragment%d";

    /**
     * Collection displayed on board.
     */
    private static final String STATE_COLLECTION = "collection";

    /**
     * Number of columns in the board.
     */
    private static final int COLUMN_COUNT = 2;

    //
    // Fields
    //

    @Bind(R.id.mix_background)
    View mixBackground;

    @Bind(R.id.control_layout)
    View controlLayout;

    @Bind(R.id.app_bar)
    Toolbar appBar;

    @Bind(R.id.board_grid)
    RecyclerView boardView;

    @Bind(R.id.play_button)
    FloatingActionButton playButton;

    @Bind(R.id.fragment_container)
    ViewGroup fragmentContainer;

    private Collection mCollection;
    private BoardAdapter mAdapter;

    private PlayerFragment mPlayerFragment;

    private int mColorPrimary;

    //
    // Activity callbacks
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ButterKnife.bind(this);

        final TypedArray typedArray = getTheme().obtainStyledAttributes(
                new int[]{R.attr.colorPrimary}
        );
        mColorPrimary = typedArray.getColor(0, Color.BLACK);

        // Grab collection either from Intent or from saved state.
        mCollection = (Collection) (savedInstanceState != null
                ? savedInstanceState.getParcelable(STATE_COLLECTION)
                : getIntent().getParcelableExtra(BoardCastIntents.EXTRA_COLLECTION)
        );

        if (mCollection == null) {
            throw new IllegalStateException("Collection is null.");
        }

        if (mCollection.clips == null || mCollection.clips.isEmpty()) {
            throw new IllegalArgumentException("Collection has no clips.");
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
        final int spacing = getResources().getDimensionPixelSize(R.dimen.board_spacing);
        boardView.addItemDecoration(new SpaceItemDirection(spacing));

        // Put clips inside board.
        mAdapter.setClips(mCollection.clips);

        // Set up player.
        if (savedInstanceState == null) {
            mPlayerFragment = PlayerFragment.newInstance(mCollection.clips);
            getSupportFragmentManager().beginTransaction()
                    .add(mPlayerFragment, FRAGMENT_TAG_PLAYER)
                    .commit();
        } else {
            mPlayerFragment = (PlayerFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG_PLAYER);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        hideClipOverlay();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(STATE_COLLECTION, mCollection);
    }

    //
    // Clip overlay
    //

    private void showClipOverlay(Clip clip) {
        if (isClipOverlayShowing(clip)) {
            return;
        }

        mixBackground.setBackgroundColor(mColorPrimary);
        controlLayout.setBackgroundColor(mColorPrimary);
        fragmentContainer.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ClipFragment.newInstance(clip), getFragmentTag(clip))
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .commit();
    }

    private void hideClipOverlay() {
        mixBackground.setBackgroundColor(Color.TRANSPARENT);
        controlLayout.setBackgroundColor(Color.TRANSPARENT);
        fragmentContainer.setVisibility(View.GONE);
    }

    private boolean isClipOverlayShowing(Clip clip) {
        final Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(getFragmentTag(clip));
        return fragment != null;
    }

    private int getClipIndex(Clip clip) {
        return mCollection.clips.indexOf(clip);
    }

    private String getFragmentTag(Clip clip) {
        return String.format(FRAGMENT_TAG_CLIP_FORMAT, getClipIndex(clip));
    }

    //
    // Listeners
    //

    @OnClick(R.id.play_button)
    void onPlayClick() {
        playButton.setEnabled(false);
        if (mPlayerFragment.isPlaying()) {
            mPlayerFragment.pausePlaying();
        } else {
            mPlayerFragment.startPlaying();
        }
    }

    //
    // PlayerFragment.PlayerListener implementation
    //

    @Override
    public void onPlayerStart(Clip clip) {
        showClipOverlay(clip);
        playButton.setEnabled(true);
        playButton.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void onPlayerPause() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onPlayerStop() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
        hideClipOverlay();
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
            return new ClipViewHolder(mInflater.inflate(R.layout.recycler_item_clip, parent, false));
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
                    .load(clip.posterURL)
                    .crossFade()
                    .centerCrop()
                    .into(posterView);
            final int clipColor = Color.parseColor(mClip.color);
            itemView.setBackgroundColor(clipColor);
            ClipColorUtils.setTextColorForClip(textView, mClip);

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
